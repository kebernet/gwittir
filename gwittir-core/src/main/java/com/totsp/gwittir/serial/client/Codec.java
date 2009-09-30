/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.serial.client;

/**
 *
 * @author kebernet
 */
public interface Codec<T> {

    String getMimeType();

    T deserialize(String data) throws SerializationException;
    String serialize(T object) throws SerializationException;

}
