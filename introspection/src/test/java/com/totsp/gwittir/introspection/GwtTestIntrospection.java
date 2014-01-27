/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.introspection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.totsp.gwittir.introspection.testmodel.TestFileDeclaredIntrospection;

/**
 *
 * @author kebernet
 */
public class GwtTestIntrospection extends GWTTestCase {

    public String getModuleName() {
        return "com.totsp.gwittir.Introspection";
    }

    public void testFileDeclared() throws Exception {
        TestFileDeclaredIntrospection bean = new TestFileDeclaredIntrospection();
        Property p = Introspector.INSTANCE.getDescriptor(bean).getProperty("stringProperty");
        System.out.println( "String property: "+p);
        assertTrue( p.getName().equals("stringProperty") );
        p = Introspector.INSTANCE.getDescriptor(bean).getProperty("intProperty");
        assertTrue( p.getName().equals("intProperty"));
        try{
            p = Introspector.INSTANCE.getDescriptor(bean).getProperty("doubleProperty");
            if(GWT.isScript()){
                fail();
            }
        }
        catch(RuntimeException e) {
            assertTrue( e != null );
        }
    }

    public void testCreate() throws Exception {
        TestFileDeclaredIntrospection bean = Introspector.INSTANCE.createInstance(TestFileDeclaredIntrospection.class);
        bean.setStringProperty("c");
    }

    public void testForName() throws Exception {
        Class clazz = Introspector.INSTANCE.forName("com.totsp.gwittir.introspection.testmodel.TestFileDeclaredIntrospection");
        assertEquals(TestFileDeclaredIntrospection.class, clazz);
    }
}
