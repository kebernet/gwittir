/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rest.client.transports;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.totsp.gwittir.rest.client.Transport.RequestControl;

/**
 *
 * @author kebernet
 */
public class XHRTransport extends HTTPTransport {

    public RequestControl get(String mimeType, String url, final AsyncCallback<String> callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET, url);
        b.setHeader( ACCEPT_HEADER, mimeType);
        RequestCallback rc = new RequestCallback(){

            public void onResponseReceived(Request request, Response response) {
                callback.onSuccess( response.getText() );
            }

            public void onError(Request request, Throwable exception) {
                callback.onFailure(exception);
            }

        };
        return super.doRequest(b, rc);
    }


    public RequestControl delete(String mimeType, String url, final AsyncCallback callback) {
        RequestBuilder b = new FullRequestBuilder("DELETE", url);
        b.setHeader( ACCEPT_HEADER, mimeType);
        RequestCallback rc = new RequestCallback(){

            public void onResponseReceived(Request request, Response response) {
                callback.onSuccess( response.getText() );
            }

            public void onError(Request request, Throwable exception) {
                callback.onFailure(exception);
            }

        };
        return super.doRequest(b, rc);
    }

    public RequestControl post(String mimeType, String url, String payload, final AsyncCallback callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET, url);
        b.setHeader( ACCEPT_HEADER, mimeType);
        b.setRequestData(payload);
        RequestCallback rc = new RequestCallback(){

            public void onResponseReceived(Request request, Response response) {
                callback.onSuccess( response.getText() );
            }

            public void onError(Request request, Throwable exception) {
                callback.onFailure(exception);
            }

        };
        return super.doRequest(b, rc);
    }

    public RequestControl put(String mimeType, String url, String payload, final AsyncCallback<String> callback) {
        RequestBuilder b = new FullRequestBuilder("PUT", url);
        b.setHeader( ACCEPT_HEADER, mimeType);
        b.setRequestData(payload);
        RequestCallback rc = new RequestCallback(){

            public void onResponseReceived(Request request, Response response) {
                callback.onSuccess( response.getText() );
            }

            public void onError(Request request, Throwable exception) {
                callback.onFailure(exception);
            }

        };
        return super.doRequest(b, rc);
    }


    private static class FullRequestBuilder extends RequestBuilder {

        public FullRequestBuilder(String method, String url){
            super(method, url);
        }
    }
}
