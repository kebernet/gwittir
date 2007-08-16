/*
 * ContactsServiceServlet.java
 *
 * Created on August 2, 2007, 11:02 PM
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
package com.totsp.gwittir.example.remote;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.totsp.gwittir.example.api.ContactsService;
import com.totsp.gwittir.example.client.remote.Contact;
import com.totsp.gwittir.example.client.remote.ContactsRemoteException;
import com.totsp.gwittir.example.client.remote.ContactsRemoteService;
import com.totsp.gwittir.example.web.StartupContextListener;

import com.totsp.gwt.beans.server.BeanMapping;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;


/**
 *
 * @author cooper
 */
public class ContactsServiceServlet extends RemoteServiceServlet
    implements ContactsRemoteService {
    private ContactsService service;
    private Properties mappings;

    /** Creates a new instance of ContactsServiceServlet */
    public ContactsServiceServlet() {
    }

    public List findContacts(int limit, int start, String orderBy,
        boolean ascending) throws ContactsRemoteException {
        try {
            return (List) BeanMapping.convert(getMappings(),
                getService().findContacts(limit, start, orderBy, ascending));
        } catch(Exception e) {
            this.log("Exception in findContacts", e);
            throw new ContactsRemoteException(e.toString());
        }
    }

    public Properties getMappings() {
        return mappings;
    }

    public ContactsService getService() {
        return service;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println( "Initting the service servlet.");
        this.setService((ContactsService) config.getServletContext()
                                                .getAttribute(StartupContextListener.CONTACTS_SERVICE));
        this.setMappings((Properties) config.getServletContext()
                                            .getAttribute(StartupContextListener.MAPPINGS));
    }

    public Contact saveContact(Contact contact) throws ContactsRemoteException {
        try {
            return (Contact) BeanMapping.convert(getMappings(),
                getService()
                    .saveContact((com.totsp.gwittir.example.api.Contact) 
                    BeanMapping
                    .convert(getMappings(), contact)));
        } catch(Exception e) {
            this.log("Exception in findContacts", e);
            throw new ContactsRemoteException(e.toString());
        }
    }

    public void setMappings(Properties mappings) {
        this.mappings = mappings;
    }

    public void setService(ContactsService service) {
        this.service = service;
    }
}
