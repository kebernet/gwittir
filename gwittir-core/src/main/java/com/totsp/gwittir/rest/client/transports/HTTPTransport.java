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


/** This is an abstract based class for all of the XHR oriented transports.
 *
 * @author <a href="mailto:kebernet@gmail.com">Robert Cooper</a>
 */
public abstract class HTTPTransport implements Transport {
    /** "Accept" */
    public static final String ACCEPT_HEADER = "Accept";
    /** "Content-Type" */
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    /** SC_OK */
    public static final int[] GET_RESPONSE_CODES = new int[] { Response.SC_OK };
    /** SC_OK, SC_NO_CONTENT, SC_CREATED */
    public static final int[] PUT_RESPONSE_CODES = new int[] { Response.SC_OK, Response.SC_NO_CONTENT, Response.SC_CREATED };
    /** SC_OK, SC_NO_CONTENT, SC_RESET_CONTENT */
    public static final int[] POST_RESPONSE_CODES = new int[] {
            Response.SC_OK, Response.SC_NO_CONTENT, Response.SC_RESET_CONTENT
        };
    /** SC_OK, SC_NO_CONTENT */
    public static final int[] DELETE_RESPONSE_CODES = new int[] { Response.SC_OK, Response.SC_NO_CONTENT };

    static {
        Arrays.sort(PUT_RESPONSE_CODES);
        Arrays.sort(POST_RESPONSE_CODES);
        Arrays.sort(DELETE_RESPONSE_CODES);
    }

    private PostHook post;
    private PreHook pre;

    /** Sets a PostHook implementation that can effect the response before it is pass out of the transport.
     * You can use post hooks to, for example, get special headers off the return result or in extreme cases,
     * alter the response to fix characte escapings, etc.
     * @param post the post to set
     */
    public void setPost(PostHook post) {
        this.post = post;
    }

    /**  PostHook implementation
     * @return the post
     */
    public PostHook getPost() {
        return post;
    }

    /** Sets a PreHook implementation.
     * You can use PreHooks to pass special auth headers, etc.
     * @param pre the pre to set
     */
    public void setPre(PreHook pre) {
        this.pre = pre;
    }

    /** a PreHook implementation.
     * @return the pre
     */
    public PreHook getPre() {
        return pre;
    }

    /** Runs the post hook on a raw response and returns a response for use by the transport
     *
     * @param the raw response
     * @return response the response to continue operations on
     */
    protected Response doPostHook(Response response) {
        return (this.post == null) ? response
                                   : this.post.afterReceive(response);
    }

    /** Runs the pre hook and returns a request builder for use by the transport
     *
     * @param builder the transports constructed request builder.
     * @return the request builder to continue operations on.
     */
    protected RequestBuilder doPreHook(RequestBuilder builder) {
        return (this.pre == null) ? builder
                                  : this.pre.beforeSend(builder);
    }

    /** Executes a request and handles the pre and post hooks
     *
     * @param builder the initial builder
     * @param callback the request callback to pass to the final builder
     * @return a RequestControl implementation to return back out.
     */
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

    /** Handles a response and returns the appropriate String value, or throws an exception
     *
     * @param response the response object to read
     * @param acceptableCodes the acceptable HTTP status codes for the method that was used to generate the response
     * @param requireBody whether a full String body is required (GET requests)
     * @return The String value that is the result of the call or, where appropriate, the Location header.
     * @throws HTTPTransportException thrown if there is a problem with the call.
     */
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

    /** An interface that can be implemented to alter the response before it is return from the transport (and into a codec)
     *
     */
    public static interface PostHook {
        public Response afterReceive(Response response);
    }

    /** An interface that can be implemented to edit the request builder before a call is made.
     *
     */
    public static interface PreHook {
        public RequestBuilder beforeSend(RequestBuilder builder);
    }

    /** A standard RequestCallback that can be used to invoke #doRequest() with.
     * 
     */
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
