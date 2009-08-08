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
package com.totsp.gwittir.client.util.userdata;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;

import com.totsp.gwittir.client.util.UnavailableException;


/**
 *
 * @author kebernet
 */
public class UserData extends JavaScriptObject {
    protected UserData() {
    }

    public static final UserData getInstance(Element e)
        throws UnavailableException {
        if (!isAvailable()) {
            throw new UnavailableException();
        }

        return getNativeInstance(e);
    }

    public static final UserData getInstance() throws UnavailableException {
        return getInstance(createElement());
    }

    public final native String get(String key) /*-{
    return this.getAttribute(key);
    }-*/;

    public final native void load(String name) /*-{
    this.load(name);
    }-*/;

    public final native void save(String name) /*-{
    this.save(name);
    }-*/;

    public final native void set(String key, String value) /*-{
    this.setAttribute(key, value);
    }-*/;

    private static final native boolean isAvailable() /*-{
    return navigator.userAgent.indexOf("MSIE") != -1;
    }-*/;

    private static final native UserData getNativeInstance(Element e);

    private static final native Element createElement();
}
