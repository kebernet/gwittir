/*
 * AbstractBeanForm.java
 *
 * Created on April 12, 2007, 3:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;

import com.totsp.gwittir.client.action.Action;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Comparator;


/**
 *
 * @author cooper
 */
public abstract class AbstractBeanForm extends AbstractBoundWidget {
    private Action action;
    private Comparator comparator;
    private Object model;
    private Renderer renderer;
    private PropertyContainer[] properties;
    private boolean inited = false;

    /** Creates a new instance of AbstractBeanForm */
    public AbstractBeanForm() {
    }

    public void addChangeListener(ChangeListener listener) {
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

    protected PropertyContainer[] getProperties() {
        return properties;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    protected void init() {
        this.inited = true;
    }

    protected void onAttach() {
        if(!inited) {
            throw new RuntimeException(
                "You must call .init() before attaching this object");
        }

        super.onAttach();
    }

    public void removeChangeListener(ChangeListener listener) {
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    protected void setProperties(PropertyContainer[] properties) {
        this.properties = properties;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
