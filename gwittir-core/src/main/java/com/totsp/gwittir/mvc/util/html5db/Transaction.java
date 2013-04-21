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

import com.google.gwt.core.client.JavaScriptObject;

import com.totsp.gwittir.util.jsni.JavaScriptObjectDecorator;

import java.util.Date;


/**
 *
 * @author kebernet
 */
public class Transaction extends JavaScriptObject {
    protected Transaction() {
    }

    public final void execute(String sql, Object[] parameters,
        ResultsCallback callback) {
        JavaScriptObject jsParams = convertParameters(parameters);
        this.nativeExecuteSql(sql, jsParams, callback);
    }

    private final static JavaScriptObject convertParameters(Object[] parameters) {
        JavaScriptObjectDecorator d = new JavaScriptObjectDecorator(JavaScriptObjectDecorator.newArray());

        for (int i = 0; (parameters != null) && (i < parameters.length); i++) {
            Object p = parameters[i];

            if (p instanceof String) {
                d.setStringProperty(Integer.toString(i), (String) p);
            } else if (p instanceof Integer) {
                d.setIntegerProperty(Integer.toString(i), (Integer) p);
            } else if (p instanceof Date) {
                d.setJavaScriptObjectProperty(Integer.toString(i),
                    longAsInt(Long.toString(((Date) p).getTime())));
            } else if (p instanceof Float) {
                d.setFloatProperty(Integer.toString(i), (Float) p);
            } else if (p instanceof Double) {
                d.setFloatProperty(Integer.toString(i), (Float) p);
            }
        }

        return d.getObject();
    }

    private final static native JavaScriptObject longAsInt(String longAsString) /*-{
    return parseInt(longAsString) ;
    }-*/;

    private final native void nativeExecuteSql(String sql,
        JavaScriptObject parameters, ResultsCallback callback) /*-{
    var trans = this;
    trans.executeSql(
    sql,
    parameters,
    function(trans, result){
    callback.@com.totsp.gwittir.mvc.util.html5db.ResultsCallback::onSuccess(Lcom/totsp/gwittir/client/util/html5db/Transaction;Lcom/totsp/gwittir/client/util/html5db/ResultSet;)
    (trans, result);
    },
    function(trans, error){
    callback.@com.totsp.gwittir.mvc.util.html5db.ResultsCallback::onFailure(Lcom/totsp/gwittir/client/util/html5db/Transaction;Lcom/totsp/gwittir/client/util/html5db/SQLError;)
    (trans, error);
    })
    return;
    }-*/;
}
