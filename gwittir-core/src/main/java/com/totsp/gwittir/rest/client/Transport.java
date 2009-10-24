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
package com.totsp.gwittir.rest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


/** Transports are implementations of the network call that deal directly with the serialized forms of objects in RESTService
 *
 * @see com.totsp.gwittir.rest.client.transports.XHRTransport
 * @author <a href="mailto:kebernet@gmail.com">Robert Cooper</a>
 */
public interface Transport {
    /** Makes a DELETE request to the server
     *
     * @param mimeType mimeType from the codec.
     * @param url URL to make the request to
     * @param callback callback with the redirect URL or the resultant data.
     * @return RequestControl for cancelling the request.
     */
    public RequestControl delete(String mimeType, String url, AsyncCallback<String> callback);

    /** Makes a GET request to the server
     *
     * @param mimeType mimeType from the codec.
     * @param url URL to make the request to
     * @param callback callback with the resultant data.
     * @return RequestControl for cancelling the request.
     */
    public RequestControl get(String mimeType, String url, AsyncCallback<String> callback);

    /** Makes a POST request to the server
     *
     * @param mimeType mimeType from the codec.
     * @param url URL to make the request to
     * @param callback callback with the redirect URL or the resultant data.
     * @return RequestControl for cancelling the request.
     */
    public RequestControl post(String mimeType, String url, String payload, AsyncCallback<String> callback);

    /** Makes a PUT request to the server
     *
     * @param mimeType mimeType from the codec.
     * @param url URL to make the request to
     * @param callback callback with the redirect URL or the resultant data.
     * @return RequestControl for cancelling the request.
     */
    public RequestControl put(String mimeType, String url, String payload, AsyncCallback<String> callback);

    /** An interface to controlling a request once it has been made.
     *
     */
    public static interface RequestControl {
        public void cancel();
    }
}
