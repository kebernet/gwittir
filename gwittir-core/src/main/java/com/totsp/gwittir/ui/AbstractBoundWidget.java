/*
 * AbstractBoundWidget.java
 *
 * Created on June 14, 2007, 9:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;

import com.totsp.gwittir.Action;
import com.totsp.gwittir.BindingAction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Comparator;


/**
 *
 * @author rcooper
 */
public abstract class AbstractBoundWidget extends Composite
    implements BoundWidget {
    private Action action;
    private ChangeListenerCollection changeListeners = new ChangeListenerCollection();
    private Comparator comparator;
    private Object model;
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private Renderer renderer;

    /** Creates a new instance of AbstractBoundWidget */
    public AbstractBoundWidget() {
    }

    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyName, l);
    }

    protected void fireChange() {
        changeListeners.fireChange(this);
    }

    public Action getAction() {
        return action;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public Object getModel() {
        return model;
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return changes.getPropertyChangeListeners();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    protected void onAttach() {
        super.onAttach();

        if(this.getAction() instanceof BindingAction) {
            ((BindingAction) getAction()).bind(this);
        }
    }

    protected void onDetach() {
        super.onDetach();

        if(this.getAction() instanceof BindingAction &&
                (this.getModel() != null)) {
            ((BindingAction) getAction()).unbind(this);
        }
    }

    public void removeChangeListener(ChangeListener listener) {
        changeListeners.remove(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        changes.removePropertyChangeListener(propertyName, l);
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public void setModel(Object model) {
        if(this.getAction() instanceof BindingAction &&
                (this.getModel() != null)) {
            ((BindingAction) getAction()).unbind(this);
        }

        this.model = model;

        if(this.getAction() instanceof BindingAction) {
            ((BindingAction) getAction()).set(this);

            if(this.isAttached() && (this.getModel() != null)) {
                ((BindingAction) getAction()).bind(this);
            }
        }
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
