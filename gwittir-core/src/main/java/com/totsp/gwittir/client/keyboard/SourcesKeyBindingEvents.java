/*
 * SourcesKeyBindingEvents.java
 *
 * Created on October 22, 2007, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.keyboard;

/**
 *
 * @author rcooper
 */
public interface SourcesKeyBindingEvents {
    
    public void addKeyBindingEventListener( KeyBindingEventListener l );
    
    public boolean removeKeyBindingEventListener( KeyBindingEventListener l );
    
    public KeyBindingEventListener[] getKeyBindingEventListeners();
}
