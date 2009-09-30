/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author kebernet
 */
public interface Transport {

    public RequestControl get(String mimeType, String url, AsyncCallback<String> callback );
    public RequestControl put(String mimeType, String url, String payload, AsyncCallback<String> callback );
    public RequestControl delete(String mimeType, String url, AsyncCallback callback );
    public RequestControl post(String mimeType, String url, String payload, AsyncCallback callback );

    public static interface RequestControl {
        public void cancel();
    }

}
