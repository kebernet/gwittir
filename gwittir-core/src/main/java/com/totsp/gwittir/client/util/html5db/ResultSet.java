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

    public final native int getRowCount()/*-{ 
        alert(this);
        alert(this.rows);                               
        return this.rows != undefined ? this.rows.length : 0;
     }-*/;

    public final JavaScriptObjectDecorator[] getRows(){
        JavaScriptObjectDecorator[] result = new JavaScriptObjectDecorator[this.getRowCount()];
        if( this.getRowCount() > 0){
        	for(int i=0; i < result.length; i++ ){
	            result[i] = new JavaScriptObjectDecorator( this.item(i));
	        }
        }
        return result;
    }

    private final native JavaScriptObject item(int i)/*-{ return this.rows.item(i); }-*/;
}
