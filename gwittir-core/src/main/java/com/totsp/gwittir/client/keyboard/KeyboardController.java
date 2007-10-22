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
        if( binding.getKey() == KeyBinding.F1 ){
            this.clobberHelp();
        }
        bindings.put(binding, widget);
    }

    public void register(final KeyBinding binding, final Action action)
        throws KeyBindingException {
        if(bindings.containsKey(binding)) {
            throw new KeyBindingAlreadyRegisteredException();
        }
        if( binding.getKey() == KeyBinding.F1 ){
            this.clobberHelp();
        }
        bindings.put( binding, action );
    }
    
    public void register(final KeyBinding binding, final Task task )
    throws KeyBindingException {
        if(bindings.containsKey(binding)) {
            throw new KeyBindingAlreadyRegisteredException();
        }
        if( binding.getKey() == KeyBinding.F1 ){
            this.clobberHelp();
        }
        bindings.put( binding, task );
        
    }
    
    public boolean unregister( final KeyBinding binding ){
        return this.bindings.remove( binding ) != null;
    }
    
    boolean handleEvent( char keyCode, boolean ctrl, boolean alt, boolean shift ){
        KeyboardController.LOG.log( Level.SPAM, "key event:"+keyCode+" ctrl:"+ctrl + " alt:"+alt, null);
        KeyBinding check = new KeyBinding( keyCode, ctrl, alt, shift );
        Object execute  = this.bindings.get( check );
        if( execute != null ){
             if( execute instanceof Task ){
                 ((Task) execute).run();
             } else if( execute instanceof Action ){
                 ((Action) execute).execute(null);
             } else if( execute instanceof BoundWidget ){
                 ((BoundWidget) execute).getAction().execute((BoundWidget) execute);
             }
             return false;
        }
        return true;
    }
    
    private native void clobberHelp() /*-{
        $wnd.onhelp = function(){ return false; }
        $doc.onhelp = function(){ return false; }
    }-*/;
           
    
}
