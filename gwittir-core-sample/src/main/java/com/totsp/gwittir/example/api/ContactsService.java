/*
 * ContactsService.java
 *
 * Created on August 2, 2007, 8:09 PM
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.totsp.gwittir.example.api;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author cooper
 */

public class ContactsService {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    
    /** Creates a new instance of ContactsService */
    public ContactsService() {
    }
    
    public List<Contact> findContacts( int limit, int start, String orderBy, boolean ascending ) 
    throws ContactsServiceException {
        EntityManager em = this.getEntityManagerFactory().createEntityManager();
        try {
             em.getTransaction().begin();
             Query  q = em.createNamedQuery("Contact."+
                     (orderBy == null ? "lastName" : orderBy ) +
                     (ascending ? "Ascending" : "Descending" ) );;
             q.setFirstResult( start );
             q.setMaxResults( limit );
             List<Contact> results =  q.getResultList();
             
             return results;
             
        }catch(RuntimeException e){
            throw new ContactsServiceException( "Unable to query contacts - " + e.getMessage());
        }
        finally {
            if (em != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }
    }
    
    public Contact saveContact( Contact contact ) throws ContactsServiceException{
        EntityManager em = this.getEntityManagerFactory().createEntityManager();
        try {
             em.getTransaction().begin();
             if( contact.getId() == null ){
                 em.persist( contact );
             } else {
                 em.merge( contact );
             }
             return contact;
             
        }catch(RuntimeException e){
            throw new ContactsServiceException( "Unable to query contacts", e);
        }
        finally {
            if (em != null) {
                em.getTransaction().commit();
                em.close();
            }
        }
        
    }
    
    public List<StateLookup> getStateLookups(){
        EntityManager em = this.getEntityManagerFactory().createEntityManager();
        try {
             em.getTransaction().begin();
             Query q = em.createNamedQuery("StateLookup.all");
            return q.getResultList();
             
        } finally {
            if (em != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }        
    }
    
    public List<TypeLookup> getTypeLookups(){
        EntityManager em = this.getEntityManagerFactory().createEntityManager();
        try {
             em.getTransaction().begin();
             Query q = em.createNamedQuery("TypeLookup.all");
            return q.getResultList();
             
        } finally {
            if (em != null) {
                em.getTransaction().rollback();
                em.close();
            }
        }        
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    
    
}
