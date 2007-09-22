/*
 * ContactsServiceServletTest.java
 * JUnit based test
 *
 * Created on August 5, 2007, 3:37 PM
 */

package com.totsp.gwittir.example.remote;

import java.util.ArrayList;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import com.totsp.gwittir.example.api.ContactsService;
import com.totsp.gwittir.example.client.remote.Address;
import com.totsp.gwittir.example.client.remote.Contact;
import com.totsp.gwittir.example.client.remote.StateLookup;
import com.totsp.gwittir.example.client.remote.TypeLookup;
import com.totsp.gwittir.example.remote.ContactsServiceServlet;

/**
 *
 * @author cooper
 */
public class ContactsServiceServletTest extends TestCase {
    private static final Properties MAPPING_PROPS = new Properties();
    
    static {
        MAPPING_PROPS.setProperty("com.totsp.gwittir.example.api.*",
                "com.totsp.gwittir.example.client.remote.*");
    }
    
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gwittir-core-example-test");
    ContactsService service = new ContactsService();
    
    
    public ContactsServiceServletTest(String testName) {
        super(testName);
        service.setEntityManagerFactory( entityManagerFactory );
    }
    
    /**
     * Test of findContacts method, of class com.totsp.gwittir.example.remote.ContactsServiceServlet.
     */
    public void testFindContacts() throws Exception {
        ContactsServiceServlet servlet = new ContactsServiceServlet();
        servlet.setMappings( MAPPING_PROPS );
        servlet.setService( service );
        servlet.findContacts( 100, 0, "firstName", true );
        
    }
    
    /**
     * Test of saveContact method, of class com.totsp.gwittir.example.remote.ContactsServiceServlet.
     */
    public void testSaveContact() throws Exception {
        Contact c = new Contact();
        c.setFirstName("Charlie");
        c.setLastName("Collins");
        StateLookup state = new StateLookup();
        state.id = Integer.valueOf( 13 );
        state.code = "GA";
        state.name = "Georgia";
        ArrayList addresses = new ArrayList();
        Address a = new Address();
        a.setAddress1( "555 Peachtree St.");
        a.setCity( "Somewhere");
        a.setState( state );
        TypeLookup type = new TypeLookup();
        type.id = Integer.valueOf( 1 );
        type.name = "home";
        a.setType( type );
        addresses.add( a );
        c.setAddresses( addresses );
        
        ContactsServiceServlet servlet = new ContactsServiceServlet();
        servlet.setMappings( MAPPING_PROPS );
        servlet.setService( service );
        c = servlet.saveContact( c );
        
        Address work = new Address();
        work.setAddress1("333 Work St.");
        work.setState( state );
        work.setType( type );
        work.setZip("30308");
        
        c.getAddresses().add(work);
        
        servlet.saveContact( c );
    }
    
    /**
     * Test of init method, of class com.totsp.gwittir.example.remote.ContactsServiceServlet.
     */
    public void testInit() throws Exception {
        // TODO add your test code.
    }
    
}
