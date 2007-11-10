/*
 * JSONServiceInvoker.java
 *
 * Created on November 9, 2007, 8:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 * @author rcooper
 */
public class JSONServiceInvoker {
    
    public static void invoke( String url, String callbackMethodName, JSONCallback callback) {
        createCallbackMethod( callbackMethodName, callback );
        createScriptTag( url );
    }
    
    native static void createCallbackMethod( String callbackMethodName, JSONCallback callback  )
    /*-{
        $wnd[callbackMethodName] = function( argument ){
            var wrapped = @com.totsp.gwittir.client.jsni.JSONServiceInvoker::wrapObject(Lcom/google/gwt/core/client/JavaScriptObject;)(argument);
            callback.@com.totsp.gwittir.client.jsni.JSONCallback::onJSONResult(Lcom/totsp/gwittir/client/jsni/JavaScriptObjectDecorator;)(wrapped);
        }
    }-*/;
    
    static JavaScriptObjectDecorator wrapObject( JavaScriptObject object ){
        return new JavaScriptObjectDecorator(object);
    }

    static void createScriptTag( String url ){
        Element script = DOM.createElement("script");
        DOM.setElementAttribute( script, "src", url );
        DOM.setElementAttribute(script, "type", "text/javascript");
        DOM.appendChild( RootPanel.get().getElement(), script );
    }
    
}
