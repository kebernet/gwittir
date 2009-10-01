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
public class ProxyTransport extends XRESTTransport {
    public static final String X_PROXY_LOCATION_HEADER = "X-Proxy-Location";
    private String originProxyPath;

    /**
     * Set the value of originProxyPath
     *
     * @param neworiginProxyPath new value of originProxyPath
     */
    public void setOriginProxyPath(String neworiginProxyPath) {
        this.originProxyPath = neworiginProxyPath;
    }

    @Override
    public RequestControl get(String mimeType, String url,
        final AsyncCallback<String> callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET,
                this.originProxyPath);
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setHeader(X_PROXY_LOCATION_HEADER, url);

        RequestCallback rc = new RequestCallback() {
                public void onResponseReceived(Request request,
                    Response response) {
                    callback.onSuccess(response.getText());
                }

                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }
            };

        return super.doRequest(b, rc);
    }

    @Override
    public RequestControl delete(String mimeType, String url,
        final AsyncCallback callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET,
                this.originProxyPath);
        b.setHeader(X_REST_METHOD_HEADER, "DELETE");
        b.setHeader(X_PROXY_LOCATION_HEADER, url);
        b.setHeader(ACCEPT_HEADER, mimeType);

        RequestCallback rc = new RequestCallback() {
                public void onResponseReceived(Request request,
                    Response response) {
                    callback.onSuccess(response.getText());
                }

                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }
            };

        return super.doRequest(b, rc);
    }

    @Override
    public RequestControl post(String mimeType, String url, String payload,
        final AsyncCallback callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET,
                this.originProxyPath);
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setHeader(X_PROXY_LOCATION_HEADER, url);
        b.setRequestData(payload);

        RequestCallback rc = new RequestCallback() {
                public void onResponseReceived(Request request,
                    Response response) {
                    callback.onSuccess(response.getText());
                }

                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }
            };

        return super.doRequest(b, rc);
    }

    @Override
    public RequestControl put(String mimeType, String url, String payload,
        final AsyncCallback<String> callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.POST,
                this.originProxyPath);
        b.setHeader(X_PROXY_LOCATION_HEADER, url);
        b.setHeader(X_REST_METHOD_HEADER, "PUT");
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setRequestData(payload);

        RequestCallback rc = new RequestCallback() {
                public void onResponseReceived(Request request,
                    Response response) {
                    callback.onSuccess(response.getText());
                }

                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }
            };

        return super.doRequest(b, rc);
    }
}
