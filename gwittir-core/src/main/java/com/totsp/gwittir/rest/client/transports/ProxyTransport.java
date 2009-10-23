/*
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.totsp.gwittir.rest.client.transports;

import com.google.gwt.http.client.RequestBuilder;
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
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.DELETE_RESPONSE_CODES, false, callback));
    }

    @Override
    public RequestControl delete(String mimeType, String url,
        final AsyncCallback callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET,
                this.originProxyPath);
        b.setHeader(X_REST_METHOD_HEADER, "DELETE");
        b.setHeader(X_PROXY_LOCATION_HEADER, url);
        b.setHeader(ACCEPT_HEADER, mimeType);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.GET_RESPONSE_CODES, true, callback));
    }

    @Override
    public RequestControl post(String mimeType, String url, String payload,
        final AsyncCallback callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.POST,
                this.originProxyPath);
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setHeader(X_PROXY_LOCATION_HEADER, url);
        b.setRequestData(payload);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.POST_RESPONSE_CODES, false, callback));
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
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.PUT_RESPONSE_CODES, false, callback));
    }
}
