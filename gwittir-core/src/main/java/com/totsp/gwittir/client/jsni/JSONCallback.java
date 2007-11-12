/*
 * JSONCallback.java
 *
 * Created on November 9, 2007, 8:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.jsni;

/**
 * A callback Interface for JSON service calls. Note that there is no failure
 * notification.
 * @author rcooper
 */
public interface JSONCallback {
    
    /**
     * Passes in the result of the JSON call wrapped in a JavaScriptObjectDecorator.
     * @param decorator The result of the JSON call.
     * Note you can call .getObject() on the resulting decorator to get the raw 
     * JavaScriptObject. This is useful of you want to pass it into a 
     * <a href="http://code.google.com/p/gwt-jsonizer/">gwt-jsonizer</a> factory.
     */
    void onJSONResult( JavaScriptObjectDecorator decorator );
    
}
