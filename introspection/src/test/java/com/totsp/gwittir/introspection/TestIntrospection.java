package com.totsp.gwittir.introspection;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.introspection.testmodel.TestFileDeclaredIntrospection;
import com.totsp.gwittir.rebind.introspection.JVMIntrospector;
import junit.framework.TestCase;

/**
 *
 */
public class TestIntrospection extends TestCase{


    public void testFileDeclared() throws Exception {
        TestFileDeclaredIntrospection bean = new TestFileDeclaredIntrospection();
        Property p = new JVMIntrospector().getDescriptor(bean).getProperty("stringProperty");
        System.out.println( "String property: "+p);
        assertTrue( p.getName().equals("stringProperty") );
        p = new JVMIntrospector().getDescriptor(bean).getProperty("intProperty");
        assertTrue( p.getName().equals("intProperty"));
        try{
            p = new JVMIntrospector().getDescriptor(bean).getProperty("doubleProperty");
            if(GWT.isScript()){
                fail();
            }
        }
        catch(RuntimeException e) {
            assertTrue( e != null );
        }

    }
}
