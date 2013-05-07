package com.totsp.gwittir.introspection;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.introspection.testmodel.TestFileDeclaredIntrospection;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 5/7/13
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestIntrospection extends TestCase{


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
}
