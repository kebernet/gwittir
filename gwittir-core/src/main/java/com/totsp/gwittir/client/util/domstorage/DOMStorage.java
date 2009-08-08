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

import com.totsp.gwittir.client.util.UnavailableException;


/**
 * A factory class for accessing the DOMStorage API.
 * https://developer.mozilla.org/En/DOM:Storage
 *
 * @author kebernet
 */
public class DOMStorage {
    private DOMStorage() {
    }

    public static Storage getLocal() throws UnavailableException {
        if (!available()) {
            throw new UnavailableException();
        }

        return getNativeLocal();
    }

    public static Storage getSession() throws UnavailableException {
        if (!available()) {
            throw new UnavailableException();
        }

        return getNativeSession();
    }

    private static native Storage getNativeLocal();

    private static native Storage getNativeSession();

    private static native boolean available();
}
