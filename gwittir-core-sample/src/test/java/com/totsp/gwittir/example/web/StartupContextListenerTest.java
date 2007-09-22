/*
 * StartupContextListenerTest.java
 * JUnit based test
 *
 * Created on August 5, 2007, 3:04 PM
 */

package com.totsp.gwittir.example.web;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import com.totsp.gwittir.example.api.ContactsService;
import com.totsp.gwittir.example.web.StartupContextListener;

/**
 *
 * @author cooper
 */
public class StartupContextListenerTest extends TestCase {
    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gwittir-core-example-test");
    private ContactsService service = new ContactsService();
    
    public StartupContextListenerTest(String testName) {
        super(testName);
        service.setEntityManagerFactory( entityManagerFactory );
    }

    /**
     * Test of contextDestroyed method, of class com.totsp.gwittir.example.web.StartupContextListener.
     */
    public void testContextDestroyed() {
        // TODO add your test code.
    }

    /**
     * Test of contextInitialized method, of class com.totsp.gwittir.example.web.StartupContextListener.
     */
    public void testContextInitialized() {
        // TODO add your test code.
    }

    /**
     * Test of getSerializedStates method, of class com.totsp.gwittir.example.web.StartupContextListener.
     */
    public void testGetSerializedStates() throws Exception {
        StartupContextListener l = new StartupContextListener();
        System.out.println( l.getSerializedStates( service ) );
        
    }

    /**
     * Test of getSerializedTypes method, of class com.totsp.gwittir.example.web.StartupContextListener.
     */
    public void testGetSerializedTypes() throws Exception {
        StartupContextListener l = new StartupContextListener();
        System.out.println( l.getSerializedTypes( service ) );
    }
    
}
