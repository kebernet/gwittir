/* JSONCodec.java
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

package com.totsp.gwittir.json;

import com.google.gwt.json.client.JSONObject;
import com.totsp.gwittir.serial.Codec;
import com.totsp.gwittir.serial.SerializationException;

/**
 * The Base codec implementation for JSON support.
 *
 * @author kebernet
 */
public interface JSONCodec<T> extends Codec<T> {


    static final String MIME_TYPE = "application/json";

    /**
     *  Serializes an object to JSONObject form.
     * @param source Object to Serialize
     * @return JSONObject with the value
     * @throws SerializationException
     */
    public JSONObject serializeToJSONObject(T source) throws SerializationException;

    /**
     * Creates a new instance of T from a JSONObject.
     * @param source the parsed JSON
     * @return the deserialized object.
     * @throws SerializationException
     */
    public T deserializeFromJSONObject(JSONObject source) throws SerializationException;
}
