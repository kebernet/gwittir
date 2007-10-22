/*
 * KeyboardController.java
 *
 * Created on Oct 12, 2007, 6:06:29 PM
 */
package com.totsp.gwittir.client.keyboard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.action.KeyBinding;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.ui.BoundWidget;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author cooper
 */
public class KeyboardController {
    static final Logger LOG = Logger.getLogger("com.totsp.gwittir.keyboard.KeyboardController");
    public static final KeyboardController INSTANCE = new KeyboardController();
    private final Map bindings = new HashMap();
    private final EventPreviewListener epl = (EventPreviewListener) GWT.create(EventPreviewListener.class);
    
    private KeyboardController() {
        super();
        DOM.addEventPreview( epl );
        KeyboardController.LOG.log( Level.SPAM, "Creating keyboard controller.", null);
    }

    public void register(final KeyBinding binding, final BoundWidget widget)
        throws KeyBindingException {
        if(bindings.containsKey(binding)) {
            throw new KeyBindingAlreadyRegisteredException();
        }

        bindings.put(binding, widget);
    }

    public void register(final KeyBinding binding, final Action action)
        throws KeyBindingException {
        if(bindings.containsKey(binding)) {
            throw new KeyBindingAlreadyRegisteredException();
        }
        bindings.put( binding, action );
    }
    
    public void register(final KeyBinding binding, final Task task )
    throws KeyBindingException {
        if(bindings.containsKey(binding)) {
            throw new KeyBindingAlreadyRegisteredException();
        }
        bindings.put( binding, task );
        
    }
    
    boolean handleEvent( char keyCode, boolean ctrl, boolean alt ){
        KeyboardController.LOG.log( Level.SPAM, "key event:"+keyCode+" ctrl:"+ctrl + " alt:"+alt, null);
        return true;
    }
    
    
}
