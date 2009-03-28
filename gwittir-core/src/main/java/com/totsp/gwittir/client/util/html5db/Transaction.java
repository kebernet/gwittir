/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.util.html5db;

import com.google.gwt.core.client.JavaScriptObject;

import com.totsp.gwittir.client.jsni.JavaScriptObjectDecorator;

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
    function(result){
    callback.@com.totsp.gwittir.client.util.html5db.ResultsCallback::onSuccess(Lcom/totsp/gwittir/client/util/html5db/Transaction;Lcom/totsp/gwittir/client/util/html5db/ResultSet;)
    (trans, result);
    },
    function(error){
    callback.@com.totsp.gwittir.client.util.html5db.ResultsCallback::onFailure(Lcom/totsp/gwittir/client/util/html5db/Transaction;Lcom/totsp/gwittir/client/util/html5db/SQLError;)
    (trans, error);
    })
    return;
    }-*/;
}
