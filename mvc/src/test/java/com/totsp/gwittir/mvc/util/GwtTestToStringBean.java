package com.totsp.gwittir.mvc.util;

import com.google.gwt.junit.client.GWTTestCase;

import com.totsp.gwittir.mvc.testmodel.TestModel;


/**
 * 
DOCUMENT ME!
 *
 * @author ccollins
 */
public class GwtTestToStringBean extends GWTTestCase {
    public String getModuleName() {
        return "com.totsp.gwittir.GwittirTest";
    }

    
    public void testToString() {
        TestModel testModel = new TestModel();
        System.out.println(testModel.toString());
    }
}
