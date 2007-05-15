/*
 * AbstractBeanForm.java
 *
 * Created on April 12, 2007, 3:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.totsp.gwittir.Action;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Comparator;

/**
 *
 * @author cooper
 */
public abstract class AbstractBeanForm extends Composite implements BoundWidget {
    
    private Object model;
    private Action action;
    private Renderer renderer;
    private Comparator comparator;
    private PropertyContainer[] properties;
    
    private boolean inited = false;
    /** Creates a new instance of AbstractBeanForm */
    public AbstractBeanForm() {
        
    }
    
    protected void init(){
        this.inited = true;
    }
    
    public Object getModel() {
        return model;
    }
    
    public void setModel(Object model) {
        this.model = model;
    }
    
    public Action getAction() {
        return action;
    }
    
    public void setAction(Action action) {
        this.action = action;
    }
    
    public Renderer getRenderer() {
        return renderer;
    }
    
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
    
    public Comparator getComparator() {
        return comparator;
    }
    
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }
    
    protected PropertyContainer[] getProperties() {
        return properties;
    }
    
    protected void setProperties(PropertyContainer[] properties) {
        this.properties = properties;
    }
    
    protected void onAttach() {
        if( !inited ){
            throw new RuntimeException("You must call .init() before attaching this object");
        }
        super.onAttach();
        
    }

    public void removeChangeListener(ChangeListener listener) {
    }

    public void addChangeListener(ChangeListener listener) {
    }
    
}
