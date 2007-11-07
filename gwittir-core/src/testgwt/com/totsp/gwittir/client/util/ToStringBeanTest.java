package com.totsp.gwittir.client.util;

import com.google.gwt.junit.client.GWTTestCase;

import com.totsp.gwittir.client.testmodel.TestModel;


/**
 * 
DOCUMENT ME!
 *
 * @author ccollins
 */
public class ToStringBeanTest extends GWTTestCase {
    public String getModuleName() {
        return "com.totsp.gwittir.GwittirTest";
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testToString() {
        TestModel testModel = new TestModel();
        System.out.println(testModel.toString());
    }
}
