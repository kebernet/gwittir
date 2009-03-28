/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.html5db;

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
