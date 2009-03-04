/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.stream.StreamControl;
import com.totsp.gwittir.client.stream.StreamServiceCallback;

/**
 *
 * @author kebernet
 */
public interface ExampleStreamServiceAsync {

    public StreamControl getStrings(int count, String name, StreamServiceCallback callback);

}
