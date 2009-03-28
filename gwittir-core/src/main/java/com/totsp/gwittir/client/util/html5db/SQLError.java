/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.html5db;

import com.google.gwt.core.client.JavaScriptObject;

/**
 *
 * @author kebernet
 */
public class SQLError extends JavaScriptObject {

    protected SQLError(){
        
    }

    public final native String getMessage()/*-{ return this.message; }-*/;

}
