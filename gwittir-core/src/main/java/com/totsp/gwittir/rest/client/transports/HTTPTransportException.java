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

import com.totsp.gwittir.rest.client.TransportException;

/**
 *
 * @author <a href="mailto:kebernet@gmail.com">Robert Cooper</a>
 */
public class HTTPTransportException extends TransportException {

    private int responseCode;
    private String body;

    public HTTPTransportException(String message, int responseCode, String body){
        super(message);
        this.responseCode = responseCode;
        this.body = body;
    }



    public int getResponseCode(){
        return this.responseCode;
    }

    public String getBody(){
        return this.body;
    }

}
