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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.totsp.gwittir.rest.client.Transport;

import java.util.Arrays;


/**
 *
 * @author kebernet
 */
public abstract class HTTPTransport implements Transport {
    public static final String ACCEPT_HEADER = "Accept";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final int[] GET_RESPONSE_CODES = new int[] { Response.SC_OK };
    public static final int[] PUT_RESPONSE_CODES = new int[] { Response.SC_OK, Response.SC_NO_CONTENT, Response.SC_CREATED };
    public static final int[] POST_RESPONSE_CODES = new int[] {
            Response.SC_OK, Response.SC_NO_CONTENT, Response.SC_RESET_CONTENT
        };
    public static final int[] DELETE_RESPONSE_CODES = new int[] { Response.SC_OK, Response.SC_NO_CONTENT };

    static {
        Arrays.sort(PUT_RESPONSE_CODES);
        Arrays.sort(POST_RESPONSE_CODES);
        Arrays.sort(DELETE_RESPONSE_CODES);
    }

    private PostHook post;
    private PreHook pre;

    /**
     * @param post the post to set
     */
    public void setPost(PostHook post) {
        this.post = post;
    }

    /**
     * @return the post
     */
    public PostHook getPost() {
        return post;
    }

    /**
     * @param pre the pre to set
     */
    public void setPre(PreHook pre) {
        this.pre = pre;
    }

    /**
     * @return the pre
     */
    public PreHook getPre() {
        return pre;
    }

    protected Response doPostHook(Response response) {
        return (this.post == null) ? response
                                   : this.post.afterReceive(response);
    }

    protected RequestBuilder doPreHook(RequestBuilder builder) {
        return (this.pre == null) ? builder
                                  : this.pre.beforeSend(builder);
    }

    protected XHRRequestControl doRequest(RequestBuilder builder, final RequestCallback callback) {
        doPreHook(builder);

        RequestCallback innerCallback = new RequestCallback() {
                public void onResponseReceived(Request request, Response response) {
                    response = doPostHook(response);
                    callback.onResponseReceived(request, response);
                }

                public void onError(Request request, Throwable exception) {
                    callback.onError(request, exception);
                }
            };

        builder.setCallback(innerCallback);

        try {
            return new XHRRequestControl(builder.send());
        } catch (RequestException e) {
            callback.onError(null, e);

            return new XHRRequestControl(null);
        }
    }

    protected static String handleResponse(Response response, int[] acceptableCodes, boolean requireBody)
        throws HTTPTransportException {
        if (Arrays.binarySearch(acceptableCodes, response.getStatusCode()) < 0) {
            throw new HTTPTransportException(
                "Unexpected response code " + response.getStatusCode(), response.getStatusCode(), response.getText());
        }

        if (requireBody) {
            if (response.getText() == null) {
                throw new HTTPTransportException(
                    "Did not receive payload.", response.getStatusCode(), response.getText());
            }

            return response.getText();
        } else {
            String locationHeader = response.getHeader("Location");

            if (locationHeader != null) {
                return locationHeader;
            } else {
                return response.getText();
            }
        }
    }

    public static interface PostHook {
        public Response afterReceive(Response response);
    }

    public static interface PreHook {
        public RequestBuilder beforeSend(RequestBuilder builder);
    }

    protected static class GenericRequestCallback implements RequestCallback {
        AsyncCallback<String> wrapped;
        int[] codes;
        boolean requireBody;

        GenericRequestCallback(int[] codes, boolean requireBody, AsyncCallback<String> wrapped) {
            this.codes = codes;
            this.requireBody = requireBody;
            this.wrapped = wrapped;
        }

        public void onError(Request request, Throwable exception) {
            this.wrapped.onFailure(exception);
        }

        public void onResponseReceived(Request request, Response response) {
            try {
                this.wrapped.onSuccess(handleResponse(response, this.codes, this.requireBody));
            } catch (Exception e) {
                this.wrapped.onFailure(e);
            }
        }
    }

    protected static class XHRRequestControl implements RequestControl {
        private Request request;

        public XHRRequestControl(Request request) {
            this.request = request;
        }

        public void cancel() {
            if (this.request != null) {
                this.request.cancel();
            }
        }
    }
}
