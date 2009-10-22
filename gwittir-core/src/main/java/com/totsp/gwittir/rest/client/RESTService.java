/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.totsp.gwittir.rest.client.Transport.RequestControl;
import com.totsp.gwittir.serial.client.Codec;

/**
 *
 * @author kebernet
 */
public interface RESTService<T> {

    public void setBaseURL(String baseURL);
    public void setTransport(Transport transport);
    public void setCodec(Codec<T> codec);

    public RequestControl get(String key, AsyncCallback<T> callback );
    public RequestControl put(String key, T object, AsyncCallback<String> callback );
    public RequestControl delete(String key, AsyncCallback callback );
    public RequestControl post(String key, T object, AsyncCallback callback );

}
