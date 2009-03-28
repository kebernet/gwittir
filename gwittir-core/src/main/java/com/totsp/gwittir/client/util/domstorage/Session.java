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
 *
 * sessionStorage
 * This is a global object (sessionStorage) that maintains a storage area that's
 * available for the duration of the page session. A page session lasts for as
 * long as the browser is open and survives over page reloads and restores.
 * Opening a page in a new tab or window will cause a new session to be initiated.
 *
 * @author kebernet
 */
public class Session extends JavaScriptObject implements Storage {

    protected Session(){
        
    }

    public native final String get(String key)
    /*-{ return this[key]; }-*/;

    public native final int length()
    /*-{ return this.length; }-*/;

    public native final String key(int index)
    /*-{ return this.key(index); }-*/;

    public native final void clear()
    /*-{ this.clear(); return; }-*/;

    public native final void remove(String key)
   /*-{ this.removeItem(key); return; }-*/;

}
