/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.stream.impl;

import java.util.Iterator;

/**
 *
 * @author kebernet
 */
public interface StreamServiceIterator<T> extends Iterator<T> {


    void finalize();
    
}
