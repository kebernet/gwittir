/*
 * Copyright 2009 Robert "kebernet" Cooper
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

package com.totsp.gwittir.client.util.impl;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.totsp.gwittir.client.util.WindowContext;
import com.totsp.gwittir.client.util.WindowContextItem;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public abstract class WindowContextPersister {

    public abstract int getByteLimit();

    public abstract Map<String, String> getWindowContextData();

    public abstract void storeWindowContextData(Map<String, WindowContextItem> windowContextData);

    protected String serializeObject(WindowContextItem item) throws SerializationException {
        SerializationStreamFactory factory = WindowContext.SERIALIZERS.getFactory(item);
        SerializationStreamWriter writer = factory.createStreamWriter();
        writer.writeObject(item);
        return writer.toString();
    }

}
