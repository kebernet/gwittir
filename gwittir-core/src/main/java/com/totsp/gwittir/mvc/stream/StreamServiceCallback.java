/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.stream;

/**
 *
 * @author kebernet
 */
public interface StreamServiceCallback<T> {


    void onReceive(T object);

    void onError(Throwable thrown);

    void onComplete();
    
}
