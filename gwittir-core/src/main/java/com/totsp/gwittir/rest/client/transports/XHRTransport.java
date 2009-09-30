/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rest.client.transports;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.totsp.gwittir.rest.client.Transport.RequestControl;

/**
 *
 * @author kebernet
 */
public class XHRTransport extends HTTPTransport {

    public RequestControl get(String mimeType, String url, AsyncCallback<String> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public RequestControl delete(String mimeType, String url, AsyncCallback callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RequestControl post(String mimeType, String url, String payload, AsyncCallback callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RequestControl put(String mimeType, String url, String payload, AsyncCallback<String> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
