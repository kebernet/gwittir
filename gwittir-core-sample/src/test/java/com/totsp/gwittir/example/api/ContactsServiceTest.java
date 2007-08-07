/*
 * ContactsServiceTest.java
 * JUnit based test
 *
 * Created on August 5, 2007, 2:19 PM
 */

package com.totsp.gwittir.example.api;

import java.util.ArrayList;
import java.util.List;
import junit.framework.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author cooper
 */
public class ContactsServiceTest extends TestCase {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gwittir-core-example-test");
    public ContactsServiceTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of findContacts method, of class com.totsp.gwittir.example.api.ContactsService.
     */
    public void testFindContacts() throws Exception {
        // TODO add your test code.
    }
    
    /**
     * Test of saveContact method, of class com.totsp.gwittir.example.api.ContactsService.
     */
    public void testSaveContact() throws Exception {
        ContactsService service = new ContactsService();
        service.setEntityManagerFactory( entityManagerFactory );
        List<StateLookup> states = service.getStateLookups();
        List<TypeLookup> types = service.getTypeLookups();
        
        Contact c = new Contact();
        c.setFirstName( "Robert" );
        c.setLastName( "Cooper" );
        c.setNotes( "It's Mee!!!! ");
        
        Address a = new Address();
        a.setAddress1( "555 Peachtree St.");
        a.setCity( "Atlanta");
        a.setZip( "30308" );
        a.setState( states.get(11) );
        a.setType( types.get(1) );
        
        List<Address> addresses = new ArrayList<Address>();
        addresses.add( a );
        c.setAddresses( addresses );
        
        System.out.println( "==================Saving");
        
        service.saveContact( c );
        
        Address work = new Address();
        addresses = new ArrayList( addresses );
        addresses.add( a );
        work.setAddress1( "555 Business St." );
        work.setCity( "Atlanta");
        work.setZip( "30328");
        work.setState( states.get(11) );
        work.setType( types.get(2) );
        addresses.add( work );
        c.setAddresses( addresses );
        
        service.saveContact( c );
        
        
        
    }
    
    /**
     * Test of getStateLookups method, of class com.totsp.gwittir.example.api.ContactsService.
     */
    public void testGetStateLookups() {
        ContactsService service = new ContactsService();
        service.setEntityManagerFactory( entityManagerFactory );
        System.out.println( service.getStateLookups() );
    }
    
    /**
     * Test of getTypeLookups method, of class com.totsp.gwittir.example.api.ContactsService.
     */
    public void testGetTypeLookups() {
        // TODO add your test code.
    }
    
    /**
     * Test of getEntityManagerFactory method, of class com.totsp.gwittir.example.api.ContactsService.
     */
    public void testGetEntityManagerFactory() {
        // TODO add your test code.
    }
    
    /**
     * Test of setEntityManagerFactory method, of class com.totsp.gwittir.example.api.ContactsService.
     */
    public void testSetEntityManagerFactory() {
        // TODO add your test code.
    }
    
}
