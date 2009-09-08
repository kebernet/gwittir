/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.serial.client;

/**
 *
 * @author kebernet
 */
public class SerializationException extends Exception {

    public SerializationException(Throwable cause){
        super(cause);
    }

    public SerializationException(String message, Throwable cause){
        super(message, cause);
    }

}
