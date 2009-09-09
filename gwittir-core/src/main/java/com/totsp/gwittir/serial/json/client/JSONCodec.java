/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.serial.json.client;

import com.google.gwt.json.client.JSONObject;
import com.totsp.gwittir.serial.client.Codec;
import com.totsp.gwittir.serial.client.SerializationException;

/**
 *
 * @author kebernet
 */
public interface JSONCodec<T> extends Codec<T> {


    public JSONObject serializeToJSONObject(T source) throws SerializationException;
    public T deserializeFromJSONObject(JSONObject source) throws SerializationException;
}
