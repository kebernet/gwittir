/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util;

import com.google.gwt.user.server.rpc.RPC;
import com.totsp.gwittir.example.client.TestContextItem;
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

    public static interface com_totsp_gwittir_example_client_TestContextItem_RemoteService extends com.google.gwt.user.client.rpc.RemoteService {
  public com.totsp.gwittir.example.client.TestContextItem get(com.totsp.gwittir.example.client.TestContextItem param);
  }

    public void testSer() throws Exception {
        TestContextItem item = new TestContextItem();
        item.setIntPropert(42);
        item.setString("A String");
        String ser = RPC.encodeResponseForSuccess(com_totsp_gwittir_example_client_TestContextItem_RemoteService.class.getMethod("get", TestContextItem.class ), item);
        System.out.println(ser );
        System.out.println("5|0|4|file:///Users/kebernet/Documents/code/gwittir/gwittir/gwittir-core/target/gwittir-core-0.5-SNAPSHOT/com.totsp.gwittir.example.Example/|FC9E3E3D86D542434EE8607058698D78|com.totsp.gwittir.example.client.TestContextItem/4168296061|a string|1|2|3|42|4|");
    }

}
