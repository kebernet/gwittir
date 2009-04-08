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

import java.util.Map;

import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;

/**
 *
 * @author kebernet
 */
public class WindowContextPersisterFirefox extends AbstractWindowContextPersister {

    @Override
    public int getByteLimit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> getWindowContextData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeWindowContextData(Map<String, String> windowContextData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(WindowContextCallback listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
