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
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;

import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.action.BindingAction;

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
