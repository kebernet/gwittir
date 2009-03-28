/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.html5db;

import com.google.gwt.core.client.JavaScriptObject;
import com.totsp.gwittir.client.jsni.JavaScriptObjectDecorator;

/**
 *
 * @author kebernet
 */
public class ResultSet extends JavaScriptObject {

    
    protected ResultSet(){
        
    }

    public final native int getRowCount()/*-{ return this.rows.length }-*/;

    public final JavaScriptObjectDecorator[] getRows(){
        JavaScriptObjectDecorator[] result = new JavaScriptObjectDecorator[this.getRowCount()];
        JavaScriptObjectDecorator instance = new JavaScriptObjectDecorator(this).getJavaScriptObjectProperty("rows");
        for(int i=0; i < result.length; i++ ){
            result[i] = instance.getJavaScriptObjectProperty(Integer.toString(i));
        }
        return result;
    }
}
