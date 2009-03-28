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
public class Local extends JavaScriptObject implements Storage {

    protected Local(){
        
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
