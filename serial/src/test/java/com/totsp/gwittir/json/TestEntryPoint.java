/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.json;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.serial.SerializationException;
/**
 *
 * @author kebernet
 */
public class TestEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        try {
            String json = " { string:'a string', integer: 5} ";
            TestBeanCodec codec = GWT.create(TestBeanCodec.class);
            TestBean b = codec.deserialize(json);
        } catch (SerializationException ex) {
            GWT.log("FAIL", ex);
        }
    }

}
