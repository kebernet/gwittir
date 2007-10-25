/*
 * KeyboardController.java
 *
 * Created on Oct 12, 2007, 6:06:29 PM
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
package com.totsp.gwittir.client.keyboard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;

import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.ui.BoundWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.Map.Entry;


/**
 *
 * @author cooper
 */
public class KeyboardController {
    private static final KeyBindingEventListener[] EMPTY_LISTENRS = new KeyBindingEventListener[0];
    static final Logger LOG = Logger.getLogger(
            "com.totsp.gwittir.keyboard.KeyboardController");
    public static final KeyboardController INSTANCE = new KeyboardController();
    private final Map bindings = new HashMap();
    private final EventPreviewListener epl = (EventPreviewListener) GWT.create(
            EventPreviewListener.class);
    private ArrayList bindingListeners = null;

    private KeyboardController() {
        super();
        DOM.addEventPreview(epl);
        KeyboardController.LOG.log(
            Level.SPAM, "Creating keyboard controller.", null);
    }

    public void register(final KeyBinding binding, final BoundWidget widget)
        throws KeyBindingException {
        this.register(binding, (Object) widget);
    }

    public void register(final KeyBinding binding, final Action action)
        throws KeyBindingException {
        this.register(binding, (Object) action);
    }

    public void register(final KeyBinding binding, final Task task)
        throws KeyBindingException {
        this.register(binding, (Object) task);
    }

    private void register(final KeyBinding binding, final Object target)
        throws KeyBindingException {
        if(binding instanceof SuggestedKeyBinding) {
            KeyBinding actual = new KeyBinding(
                    binding.getKey(), binding.isControl(), binding.isAlt(),
                    binding.isShift());

            if(bindings.containsKey(actual) || bindings.containsKey(binding)) {
                KeyboardController.LOG.log( Level.SPAM, "Contains suggested. Failing silently", null);
                return;
            }
        } else {
            SuggestedKeyBinding sb = new SuggestedKeyBinding(
                    binding.getKey(), binding.isControl(), binding.isAlt(),
                    binding.isShift());
            this.unregister(sb);

            if(this.bindings.containsKey(binding)) {
                throw new KeyBindingAlreadyRegisteredException();
            }
        }

        if(binding.getKey() == KeyBinding.F1) {
            this.clobberHelp();
        }

        bindings.put(binding, target);

        for(int i = 0; i < binding.getKeyBindingEventListeners().length; i++) {
            binding.getKeyBindingEventListeners()[i].onBind(binding);
        }

        for(int i = 0; i < this.getKeyBindingEventListeners().length; i++) {
            this.getKeyBindingEventListeners()[i].onBind(binding);
        }
    }

    public boolean unregister(final KeyBinding binding) {
        if(this.bindings.containsKey(binding)) {
            KeyboardController.LOG.log( Level.SPAM, "Unegistering from controller"+binding, null);
            for(Iterator it = this.bindings.entrySet().iterator(); it.hasNext();) {
                Entry e = (Entry) it.next();

                if(e.getValue().equals(binding)) {
                    KeyBinding b = (KeyBinding) e.getValue();
                    KeyboardController.LOG.log( Level.SPAM, "Found identity instance"+binding, null);
                    for(int i = 0; i < b.getKeyBindingEventListeners().length;
                            i++) {
                        b.getKeyBindingEventListeners()[i].onUnbind(b);
                    }

                    for(
                        int i = 0;
                            i < this.getKeyBindingEventListeners().length;
                            i++) {
                        this.getKeyBindingEventListeners()[i].onUnbind(b);
                    }
                }
            }
        }

        return this.bindings.remove(binding) != null;
    }

    boolean handleEvent(char keyCode, boolean ctrl, boolean alt, boolean shift) {
        KeyboardController.LOG.log(
            Level.SPAM, "key event:" + keyCode + " ctrl:" + ctrl + " alt:"
            + alt, null);

        //TODO: Do this without so much object creation.
        KeyBinding check = new KeyBinding(keyCode, ctrl, alt, shift);
        Object execute = this.bindings.get(check);

        if(execute == null) {
            check = new SuggestedKeyBinding(keyCode, ctrl, alt, shift);
            KeyboardController.LOG.log( Level.SPAM, "Bindings registered: "+ this.bindings.size(), null );
            execute = this.bindings.get(check);
            KeyboardController.LOG.log( Level.SPAM, "Found Suggested binding "+ check+ " "+(execute != null), null);
        }

        if(execute != null) {
            if(execute instanceof Task) {
                ((Task) execute).run();
            } else if(execute instanceof Action) {
                ((Action) execute).execute(null);
            } else if(execute instanceof BoundWidget) {
                ((BoundWidget) execute).getAction()
                 .execute((BoundWidget) execute);
            }

            return false;
        }

        return true;
    }

    private native void clobberHelp() /*-{
    $wnd.onhelp = function(){ return false; }
    $doc.onhelp = function(){ return false; }
    }-*/;

    public AutoMappedBinding findSuggestedMapping(final String match) {
        if((match == null) || (match.trim().length() == 0)) {
            return null;
        }

        for(int i = 0; i < match.length(); i++) {
            KeyBinding b = new KeyBinding(
                    match.toUpperCase().charAt(i), false, true, false);
            SuggestedKeyBinding sb = new SuggestedKeyBinding(
                    match.toUpperCase().charAt(i), false, true, false);

            if(!this.bindings.containsKey(b) && !this.bindings.containsKey(sb)) {
                String newHtml = match.substring(0, i);
                newHtml += "<u>";
                newHtml += match.substring(i, i + 1);
                newHtml += "</u>";

                if(i+1 < (match.length() - 1)) {
                    newHtml += match.substring(i+1, match.length());
                }

                AutoMappedBinding ab = new AutoMappedBinding();
                ab.newHtml = newHtml;
                ab.binding = sb;

                return ab;
            }
        }

        return null;
    }

    public void addKeyBindingEventListener(KeyBindingEventListener l) {
        if(this.bindingListeners == null) {
            this.bindingListeners = new ArrayList();
        }

        this.bindingListeners.add(l);
    }

    public boolean removeKeyBindingEventListener(KeyBindingEventListener l) {
        if(this.bindingListeners == null) {
            return false;
        }

        return this.bindingListeners.remove(l);
    }

    public KeyBindingEventListener[] getKeyBindingEventListeners() {
        if(this.bindingListeners == null) {
            return KeyboardController.EMPTY_LISTENRS;
        }

        KeyBindingEventListener[] l = new KeyBindingEventListener[this.bindingListeners
            .size()];
        this.bindingListeners.toArray(l);

        return l;
    }

    public static class AutoMappedBinding {
        public String newHtml;
        public SuggestedKeyBinding binding;
    }
}
