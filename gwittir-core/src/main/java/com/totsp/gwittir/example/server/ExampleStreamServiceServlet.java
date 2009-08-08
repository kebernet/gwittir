/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.server;

import com.totsp.gwittir.client.stream.StreamServiceIterator;
import com.totsp.gwittir.example.client.ExampleStreamService;
import com.totsp.gwittir.example.client.MyClass;
import com.totsp.gwittir.service.StreamServiceServlet;


/**
 *
 * @author kebernet
 */
public class ExampleStreamServiceServlet extends StreamServiceServlet implements ExampleStreamService {
    public StreamServiceIterator<MyClass> getResults(final int count, final String name) {
        System.out.println(count + name);

        return new StreamServiceIterator<MyClass>() {
                int i = 0;

                public boolean hasNext() {
                    return i < count;
                }

                public MyClass next() {
                    i++;

                    MyClass mc = new MyClass();
                    mc.setName(name + i);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        //Ignore.
                    }

                    return mc;
                }

                public void remove() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void close() {
                    System.out.println("finalized.");
                }
            };
    }
}
