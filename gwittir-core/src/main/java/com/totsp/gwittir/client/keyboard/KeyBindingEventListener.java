/*
 * KeyBindingEventListener.java
 *
 * Created on October 22, 2007, 4:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.keyboard;

import com.totsp.gwittir.client.action.KeyBinding;

/**
 *
 * @author rcooper
 */
public interface KeyBindingEventListener {
    void onBind( KeyBinding binding );
    void onUnbind( KeyBinding binding );
}
