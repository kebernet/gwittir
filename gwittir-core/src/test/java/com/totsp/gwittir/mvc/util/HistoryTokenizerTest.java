/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.util;

import junit.framework.TestCase;

/**
 *
 * @author kebernet
 */
public class HistoryTokenizerTest extends TestCase {
    
    public HistoryTokenizerTest(String testName) {
        super(testName);
    }

    /**
     * Test of tokenize method, of class HistoryTokenizer.
     */
    public void testTokenize() {
        System.out.println("tokenize");
        String token = "test1=someTest&test2=testu!038;2&test3=test3";
        HistoryTokenizer instance = new HistoryTokenizer(token);
        assertEquals(instance.getToken("test1"), "someTest" );
        assertEquals(instance.getToken("test2"), "test&2");

        String result = instance.tokenize();
        System.out.println(result);
        assertEquals(instance, new HistoryTokenizer(result));
    }

    

}
