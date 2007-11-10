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
 *
 * @author rcooper
 */
public interface JSONCallback {
    
    void onJSONResult( JavaScriptObjectDecorator decorator );
    
}
