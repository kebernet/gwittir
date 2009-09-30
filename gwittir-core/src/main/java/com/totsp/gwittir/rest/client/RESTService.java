/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.totsp.gwittir.serial.client.Codec;

/**
 *
 * @author kebernet
 */
public interface RESTService<T> {

    public void setBaseURL(String baseURL);
    public void setTransport(Transport transport);
    public void setCodec(Codec<T> codec);

    public void get(String key, AsyncCallback<T> callback );
    public void put(T object, AsyncCallback<String> callback );
    public void delete(String key, AsyncCallback callback );
    public void post(String key, T object, AsyncCallback callback );

}
