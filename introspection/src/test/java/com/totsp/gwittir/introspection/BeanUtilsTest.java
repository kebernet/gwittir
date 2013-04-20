/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.introspection;

import com.totsp.gwittir.introspection.util.BeanUtils;
import junit.framework.TestCase;
import test.destination.ABean;
import test.destination.BBean;

/**
 *
 * @author robert.cooper
 */
public class BeanUtilsTest extends TestCase {
    
    public BeanUtilsTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of readDottedProperty method, of class BeanUtils.
     */
    public void testReadDottedProperty() {
        ABean a = new ABean();
        BBean b = new BBean();
        a.setCharProperty('C');
        b.setParent(a);
        Character c = (Character) BeanUtils.readDottedProperty("parent.charProperty", b);
        assertEquals( Character.valueOf('C'), c);
    }

}
