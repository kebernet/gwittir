/*
 * AbstractBoundWidget.java
 *
 * Created on June 14, 2007, 9:55 AM
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
package com.totsp.gwittir.mvc.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;

import com.totsp.gwittir.mvc.action.Action;
import com.totsp.gwittir.mvc.action.BindingAction;
import com.totsp.gwittir.mvc.keyboard.KeyBinding;
import com.totsp.gwittir.mvc.keyboard.KeyBindingException;
import com.totsp.gwittir.mvc.keyboard.KeyBoundWidget;
import com.totsp.gwittir.mvc.keyboard.KeyboardController;
import com.totsp.gwittir.mvc.log.Level;
import com.totsp.gwittir.mvc.log.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Comparator;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet"
 *         Cooper</a>
 */
public abstract class AbstractBoundWidget<T> extends Composite implements BoundWidget<T>, KeyBoundWidget {
    private static final int STATE_SET = 0;
    private static final int STATE_BOUND = 1;
    private static final int STATE_CLEAR = 3;
    
    protected static final Logger LOG = Logger.getLogger("" + AbstractBoundWidget.class);
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private Action<BoundWidget<T>> action;
    private ChangeListenerCollection changeListeners = new ChangeListenerCollection();
    private Comparator comparator;
    private KeyBinding binding;
    private Object model;
    private boolean bindingRegistered = false;
    private int state = STATE_CLEAR;

    /** Creates a new instance of AbstractBoundWidget */
    public AbstractBoundWidget() {
    }

    /*
     * Removes any old associated action including the bindings, and also sets
     * the new action including any bindings.
     */
    public void setAction(Action<BoundWidget<T>> action) {
        if(action == this.action ){
            return;
        }
        if (this.action != null) {
            this.cleanupAction();
        }

        this.action = action;

        // Check to see if action is not null and model has been set
        if ((this.action != null) && (this.model != null)) {
            // If attached do the binding
            if (this.isAttached()) {
                this.setupAction();
                this.activateAction();
            }
        }
    }

    public Action<BoundWidget<T>> getAction() {
        return action;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public void setKeyBinding(final KeyBinding binding) {
        this.binding = binding;

        if ((this.binding != null) && this.isAttached()) {
            try {
                KeyboardController.INSTANCE.register(this.binding, this);
                this.bindingRegistered = true;
            } catch (KeyBindingException kbe) {
                this.bindingRegistered = false;
                AbstractBoundWidget.LOG.log(Level.SPAM, "Exception adding default binding", kbe);
            }
        }
    }

    public KeyBinding getKeyBinding() {
        return this.binding;
    }

    public void setModel(Object model) {
        Object old = this.getModel();

        if (model == old) {
            return;
        }
        if(state == STATE_BOUND){
            cleanupAction();
        }
        this.model = model;

        if (this.isAttached() && (model != null)) {
            setupAction();
            activateAction();
        }

        this.changes.firePropertyChange("model", old, model);
    }

    public Object getModel() {
        return model;
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return changes.getPropertyChangeListeners();
    }

    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyName, l);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeListeners.remove(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        changes.removePropertyChangeListener(propertyName, l);
    }

    protected void fireChange() {
        changeListeners.fireChange(this);
    }

    @Override
    protected void onAttach() {
        this.setupAction();
        super.onAttach();
        this.changes.firePropertyChange("attached", false, true);
    }

    @Override
    protected void onDetach() {
        super.onDetach();

        this.cleanupAction();
        this.changes.firePropertyChange("attached", true, false);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        this.activateAction();
    }

    private void activateAction() {
        if(state == STATE_BOUND){
            return;
        }
        if (this.getAction() instanceof BindingAction) {
            ((BindingAction<BoundWidget<T>>) getAction()).bind(this);
        }

        if (this.binding != null) {
            try {
                KeyboardController.INSTANCE.register(this.binding, this);
                this.bindingRegistered = true;
            } catch (KeyBindingException kbe) {
                this.bindingRegistered = false;
                AbstractBoundWidget.LOG.log(Level.SPAM, "Exception adding default binding", kbe);
            }
        }
        state = STATE_BOUND;
    }

    /*
     * Remove the bindings including the keybindings
     */
    private void cleanupAction() {
        if(state != STATE_BOUND){
            return;
        }
        if (this.getAction() instanceof BindingAction && (this.getModel() != null)) {
            ((BindingAction<BoundWidget<T>>) getAction()).unbind(this);
        }

        if ((this.binding != null) && this.bindingRegistered) {
            KeyboardController.INSTANCE.unregister(this.binding);
        }
        state = STATE_CLEAR;
    }

    /*
     * Calls the associated action with this widget to set the bindings.
     */
    private void setupAction() {
        if(state != STATE_CLEAR){
            //GWT.log("curious setup action call...", new RuntimeException());
        }
        if (this.getAction() instanceof BindingAction) {
            ((BindingAction<BoundWidget<T>>) getAction()).set(this);
        }
        state = STATE_SET;
    }
}
