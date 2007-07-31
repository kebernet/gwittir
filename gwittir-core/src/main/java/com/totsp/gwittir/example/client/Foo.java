/*
 * Foo.java
 *
 * Created on April 12, 2007, 3:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.beans.Bindable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 *
 * @author cooper
 */
public class Foo implements Bindable {
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private String stringProperty;
    private int intProperty;
    private boolean booleanProperty;
    
    public Foo( String stringProperty, int intProperty ){
        this.stringProperty = stringProperty;
        this.intProperty = intProperty;
    }
    
    /** Creates a new instance of Foo */
    public Foo() {
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyName, l);
    }

    public int getIntProperty() {
        return intProperty;
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return changes.getPropertyChangeListeners();
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        changes.removePropertyChangeListener(propertyName, l);
    }

    public void setIntProperty(int intProperty) {
        int old = this.intProperty;
        this.intProperty = intProperty;
        changes.firePropertyChange("intProperty", old, intProperty);
    }

    public void setStringProperty(String stringProperty) {
        String old = this.stringProperty;
        this.stringProperty = stringProperty;
        changes.firePropertyChange("stringProperty", old, stringProperty);
    }

    public boolean isBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(boolean booleanProperty) {
        Boolean old = this.booleanProperty ? Boolean.TRUE : Boolean.FALSE;
        this.booleanProperty = booleanProperty;
        changes.firePropertyChange( "booleanProperty", old, this.booleanProperty ? Boolean.TRUE : Boolean.FALSE);
    }
}
