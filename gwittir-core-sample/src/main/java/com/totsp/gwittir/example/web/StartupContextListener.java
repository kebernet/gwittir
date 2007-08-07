/*
 * StartupContextListener.java
 *
 * Created on August 2, 2007, 11:35 PM
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
package com.totsp.gwittir.example.web;

import com.totsp.gwittir.example.api.ContactsService;
import com.totsp.gwittir.example.client.remote.StateLookup;
import com.totsp.gwittir.example.client.remote.TypeLookup;

import com.totsp.gwt.beans.server.BeanMapping;
import com.totsp.gwt.freezedry.server.SimpleSerializer;

import java.net.URLEncoder;

import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *
 * @author cooper
 */
public class StartupContextListener implements ServletContextListener {
    public static final String CONTACTS_SERVICE = "ContactsService";
    public static final String MAPPINGS = "Mappings";
    public static final String TYPES = "TypeLookups";
    public static final String STATES = "StateLookups";
    private static final Properties MAPPING_PROPS = new Properties();
    
    static {
        MAPPING_PROPS.setProperty("com.totsp.gwittir.example.api.*",
                "com.totsp.gwittir.example.client.remote.*");
    }
    
    /** Creates a new instance of StartupContextListener */
    public StartupContextListener() {
    }
    
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println( "Initting the context.");
        EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("gwittir-core-example");
        ContactsService service = new ContactsService();
        service.setEntityManagerFactory( emf );
        servletContextEvent.getServletContext()
        .setAttribute(StartupContextListener.CONTACTS_SERVICE, service);
        servletContextEvent.getServletContext()
        .setAttribute(MAPPINGS, MAPPING_PROPS);
        
        try {
            String typesSer = this.getSerializedTypes( service );
            servletContextEvent.getServletContext().setAttribute(TYPES, typesSer);
            String statesSer = this.getSerializedStates( service );
            servletContextEvent.getServletContext()
            .setAttribute(STATES, statesSer);
        } catch(Exception e) {
            servletContextEvent.getServletContext()
            .log("Exception getting lookup types.", e);
        }
    }
    
    public String getSerializedStates( ContactsService service ) throws Exception {
        List<StateLookup> states = (List<StateLookup>) BeanMapping.convert(MAPPING_PROPS,
                service.getStateLookups());
        String statesSer = URLEncoder.encode(SimpleSerializer
                .serializeCollection(states), "UTF-8");
        return statesSer;
    }
    
    public String getSerializedTypes( ContactsService service ) throws Exception {
        List<TypeLookup> types = (List<TypeLookup>) BeanMapping
                .convert(MAPPING_PROPS, service.getTypeLookups());
        String typesSer = URLEncoder.encode(SimpleSerializer
                .serializeCollection(types), "UTF-8");
        return typesSer;
    }
}
