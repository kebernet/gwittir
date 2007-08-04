/*
 * OpacitySetterIE6Test.java
 * JUnit based test
 *
 * Created on August 3, 2007, 8:12 PM
 */

package com.totsp.gwittir.client.fx.rebind;

import junit.framework.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.UIObject;

/**
 *
 * @author cooper
 */
public class OpacitySetterIE6Test extends TestCase {
    
    public OpacitySetterIE6Test(String testName) {
        super(testName);
    }

    /**
     * Test of getOpacity method, of class com.totsp.gwittir.client.fx.rebind.OpacitySetterIE6.
     */
    public void testGetOpacity() {
        // TODO add your test code.
    }

    /**
     * Test of setOpacity method, of class com.totsp.gwittir.client.fx.rebind.OpacitySetterIE6.
     */
    public void testSetOpacity() {
        // TODO add your test code.
    }

    /**
     * Test of parseOrReplace method, of class com.totsp.gwittir.client.fx.rebind.OpacitySetterIE6.
     */
        public void testParseOrReplace(){
        String test1 = "alpha(opacity=25)";
        this.assertEquals( "25", OpacitySetterIE6.parseOrReplace( test1, null ) );
        String replaced1 = OpacitySetterIE6.parseOrReplace( test1, "40" );
        this.assertEquals("alpha(opacity=40)", replaced1 );
        String test2 = "foo(bar=20,baz=100) alpha(opacity=25)";
        String replaced2 = OpacitySetterIE6.parseOrReplace( test2, "50");
        this.assertEquals("foo(bar=20,baz=100) alpha(opacity=50)", replaced2 );
        String test3 = "";
        String replaced3 = OpacitySetterIE6.parseOrReplace( test3, "60");
        this.assertEquals("alpha(opacity=60)", replaced3 );
        
        String test4 = "foo(bar=100,baz=200)";
        String replaced4 = OpacitySetterIE6.parseOrReplace( test4, "60");
        this.assertEquals( test4 + replaced3, replaced4 );
        
        String test5 =  "alpha(foo=25)";
        String replaced5 = OpacitySetterIE6.parseOrReplace( test5, "60");
        System.out.println( replaced5 );
        
    }
    
}
