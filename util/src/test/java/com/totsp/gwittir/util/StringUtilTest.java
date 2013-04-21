/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.util;

import junit.framework.TestCase;

/**
 *
 * @author kebernet
 */
public class StringUtilTest extends TestCase {
    
    public StringUtilTest(String testName) {
        super(testName);
    }


    public void testUTF16() throws Exception {
        String s = "Hello world!\u597D";
        System.out.println(s);
        byte[] b = StringUtil.toUtf16ByteArray(s);//"This ia test in english".getBytes();
        String types = "";
        byte[] control = s.getBytes("UTF-16");
        for(int i=0; i < control.length; i++ ){
            assertEquals(control[i], b[i]);
        }
    }

    public void testUTF8() throws Exception {
        String s = "Hello world!";
        System.out.println(s);
        byte[] b = StringUtil.toUtf8ByteArray(s);//"This ia test in english".getBytes();
        String types = "";
        byte[] control = s.getBytes("UTF-8");
        for(int i=0; i < control.length; i++ ){
            assertEquals(control[i], b[i]);
        }
    }
}
