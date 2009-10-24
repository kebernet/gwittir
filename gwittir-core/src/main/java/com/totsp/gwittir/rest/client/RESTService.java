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

import com.totsp.gwittir.rest.client.Transport.RequestControl;
import com.totsp.gwittir.serial.client.Codec;


/**
 * This is the parent interface for a REST service.
 * @author <a href="mailto:kebernet@gmail.com">Robert Cooper</a>
 */
public interface RESTService<T> {
    /** Sets the base URL for rest calls. This is the prefix that will appear before a key request to the method calls
     *
     * @param baseURL the base URL, such as "http://foo.com/entities/"
     */
    public void setBaseURL(String baseURL);

    /**
     * Sets the Codec the data will serialized/deserialized.
     * @param codec Codec implementation.
     */
    public void setCodec(Codec<T> codec);

    /** Set the Transport implementation the service should use to make calls.
     *
     * @param transport a Transport implementation
     */
    public void setTransport(Transport transport);

    /** Deletes an object on the server
     *
     * @param key final portion of the URL
     * @param callback a callback returning exceptions or a String containing the redirect result, or the data returned from the server.
     * @return the RequestControl object that can be used to terminate the call.
     */
    public RequestControl delete(String key, AsyncCallback<String> callback);

    /** Fetches an object from the server using a GET request
     *
     * @param key the final portion of the URL to fetch.
     * @param callback a callback receiving the object or an exception.
     * @return the RequestControl object that can be used to terminate the call.
     */
    public RequestControl get(String key, AsyncCallback<T> callback);

    /** Updates and entity on the server.
     * @param key the final portion of hte URL
     * @param Object the object to update.
     * @param callback a callback returning exceptions or a String containing the redirect result, or the data returned from the server.
     * @return the RequestControl object that can be used to terminate the call.
     */
    public RequestControl post(String key, T object, AsyncCallback<String> callback);

    /** Puts a new object onto the server
     *
     * @param key final portion of the URL. May be null for collection style REST services
     * @param object object to store
     * @param callback a callback returning exceptions or a String containing the redirect result, or the data returned from the server.
     * @return the RequestControl object that can be used to terminate the call.
     */
    public RequestControl put(String key, T object, AsyncCallback<String> callback);
}
