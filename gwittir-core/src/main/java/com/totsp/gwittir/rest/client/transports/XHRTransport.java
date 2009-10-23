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
public class XHRTransport extends HTTPTransport {
    public RequestControl delete(String mimeType, String url, final AsyncCallback callback) {
        RequestBuilder b = new FullRequestBuilder("DELETE", url);
        b.setHeader(ACCEPT_HEADER, mimeType);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.DELETE_RESPONSE_CODES, false, callback));
    }

    public RequestControl get(String mimeType, String url, final AsyncCallback<String> callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET, url);
        b.setHeader(ACCEPT_HEADER, mimeType);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.GET_RESPONSE_CODES, true, callback));
    }

    public RequestControl post(String mimeType, String url, String payload, final AsyncCallback<String> callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.POST, url);
        b.setHeader(CONTENT_TYPE_HEADER, mimeType);
        b.setRequestData(payload);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.POST_RESPONSE_CODES, false, callback));
    }

    public RequestControl put(String mimeType, String url, String payload, final AsyncCallback<String> callback) {
        RequestBuilder b = new FullRequestBuilder("PUT", url);
        b.setHeader(CONTENT_TYPE_HEADER, mimeType);
        b.setRequestData(payload);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.PUT_RESPONSE_CODES, false, callback));
    }

    private static class FullRequestBuilder extends RequestBuilder {
        public FullRequestBuilder(String method, String url) {
            super(method, url);
        }
    }

    
}
