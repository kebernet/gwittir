/*
 * UnitsParserTest.java
 * JUnit based test
 *
 * Created on August 3, 2007, 3:44 PM
 */

package com.totsp.gwittir.fx;


import junit.framework.TestCase;

/**
 *
 * @author cooper
 */
public class UnitsParserTest extends TestCase {
    
    public UnitsParserTest(String testName) {
        super(testName);
    }


    /**
     * Test of parse method, of class com.totsp.gwittir.mvc.util.UnitsParser.
     */
    public void testParse() {
        UnitsParser.UnitValue v = UnitsParser.parse( "100px" );
        this.assertEquals( 100, v.value );
        this.assertEquals( "px", v.units );
    }
    
}
