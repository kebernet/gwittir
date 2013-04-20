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

package com.totsp.gwittir.mvc.util.html5db;

/**
 *
 * @author kebernet
 */
public class Databases {

    public static native Database openDatabase(String databaseName, String version, String description, int initialByteSize)
    /*-{
        if( !($wnd.openDatabase) ){
             throw "Unsupported Browser";
        }
        var db = $wnd.openDatabase(databaseName, version, description, initialByteSize);
        if(!db) {
            throw "openDatabse returned null. Are you in Hosted mode? http://lists.apple.com/archives/Webkitsdk-dev/2008/Apr/msg00027.html"
        } else {
            return db;
        }
     }-*/;

}
