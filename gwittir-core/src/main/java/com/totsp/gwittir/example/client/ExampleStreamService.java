/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.stream.StreamingService;
import com.totsp.gwittir.client.stream.StreamServiceIterator;

/**
 *
 * @author kebernet
 */
public interface ExampleStreamService extends StreamingService {

    public StreamServiceIterator<MyClass> getStrings(int count, String name);

}
