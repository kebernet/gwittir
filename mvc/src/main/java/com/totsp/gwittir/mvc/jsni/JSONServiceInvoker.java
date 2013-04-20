/*
 * JSONServiceInvoker.java
 *
 * Created on November 9, 2007, 8:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The JSON service invoker is a utility class for invoking JSON-P style web 
 * services. 
 * 
 * It has a single method to invoke a service with a specified callback method.
 * @author rcooper
 */
public class JSONServiceInvoker {
    
    /**
     * Invokes a JSON-P service. 
     * 
     * <b>Note</b>: If you make multiple calls to a service with a single
     * callback method name, calls made AFTER the first one will effectively be lost,
     * or you will get multiple resonses sent to the later callback listener.
     * 
     * For JSON-P services like the Facebook API, where you can specify different
     * different callback method names for each call, you should avail yourself of this.
     * For services like the Flickr Feed service, you should simply be aware of this.
     * @param url URL to invoke. This will become the "src" attribute of a &lt;script&gt; tag on 
     * the page.
     * 
     * @param callbackMethodName The name of the callback method the service will invoke. This will be added to 
     * the window/$wnd reference.
     * @param callback A callback that will be invoked by the service.
     */
    public static void invoke( String url, String callbackMethodName, JSONCallback callback) {
        createCallbackMethod( callbackMethodName, callback );
        createScriptTag( url );
    }
    
    native static void createCallbackMethod( String callbackMethodName, JSONCallback callback  )
    /*-{
        $wnd[callbackMethodName] = function( argument ){
            var wrapped = @com.totsp.gwittir.mvc.jsni.JSONServiceInvoker::wrapObject(Lcom/google/gwt/core/client/JavaScriptObject;)(argument);
            callback.@com.totsp.gwittir.mvc.jsni.JSONCallback::onJSONResult(Lcom/totsp/gwittir/client/jsni/JavaScriptObjectDecorator;)(wrapped);
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
