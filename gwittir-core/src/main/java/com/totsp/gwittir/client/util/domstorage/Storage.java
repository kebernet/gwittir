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
package com.totsp.gwittir.client.util.domstorage;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * This is a global object (globalStorage) that maintains multiple private
 * storage areas that can be used to hold data over a long period of time
 * (e.g. over multiple pages and browser sessions).
 *
 * This is no longer in the WhatWG spec. Replaced by localStorage. Localstorage
 * is not, however supported by Firefox yet. When called from the factory it will
 * be returned as localStorage or globalStorage[window.location.hostName], whichever
 * is available.
 *
 * @author kebernet
 */
public class Storage extends JavaScriptObject {
    protected Storage() {
    }

    public final native void clear();

    public final native String get(String key);

    public final native String key(int index);

    public final native int length();

    public final native void remove(String key);

    public final native void set(String key, String value);
}
