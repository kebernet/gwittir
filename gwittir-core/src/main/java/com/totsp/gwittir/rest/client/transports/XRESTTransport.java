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


/** The XRESTTransport uses GET for DELETE and POST for PUT, while passing an extended header called
 * X-REST-Method with the actual intended method for the call. This is suitable for older browsers that
 * only support GET and POST as methods.
 *
 * You can use this with the XRESTFilter to easily support this operation in your REST services.
 * @see com.totsp.gwittir.rest.server.XRESTFilter
 *
 * @author <a href="mailto:kebernet@gmail.com">Robert Cooper</a>
 */
public class XRESTTransport extends HTTPTransport {
    /** "X-REST-Method" */
    public static final String X_REST_METHOD_HEADER = "X-REST-Method";

    public RequestControl delete(String mimeType, String url, final AsyncCallback callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET, url);
        b.setHeader(X_REST_METHOD_HEADER, "DELETE");
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setHeader(CONTENT_TYPE_HEADER, mimeType);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.DELETE_RESPONSE_CODES, false, callback));
    }

    public RequestControl get(String mimeType, String url, final AsyncCallback<String> callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.GET, url);
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setHeader(CONTENT_TYPE_HEADER, mimeType);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.POST_RESPONSE_CODES, false, callback));
    }

    public RequestControl post(String mimeType, String url, String payload, final AsyncCallback callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.POST, url);
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setHeader(CONTENT_TYPE_HEADER, mimeType);
        b.setRequestData(payload);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.PUT_RESPONSE_CODES, false, callback));
    }

    public RequestControl put(String mimeType, String url, String payload, final AsyncCallback<String> callback) {
        RequestBuilder b = new RequestBuilder(RequestBuilder.POST, url);
        b.setHeader(X_REST_METHOD_HEADER, "PUT");
        b.setHeader(ACCEPT_HEADER, mimeType);
        b.setHeader(CONTENT_TYPE_HEADER, mimeType);
        b.setRequestData(payload);
        return super.doRequest(b, new GenericRequestCallback(HTTPTransport.PUT_RESPONSE_CODES, false, callback));
    }
}
