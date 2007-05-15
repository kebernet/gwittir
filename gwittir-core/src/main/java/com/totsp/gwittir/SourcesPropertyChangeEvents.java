/*
 * SourcesPropertyChangeEvents.java
 *
 * Created on April 12, 2007, 7:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir;

import java.beans.PropertyChangeListener;


/**
 *
 * @author cooper
 */
public interface SourcesPropertyChangeEvents {
    public void addPropertyChangeListener(PropertyChangeListener l);

    public void addPropertyChangeListener(String propertyName,
        PropertyChangeListener l);

    public PropertyChangeListener[] getPropertyChangeListeners();

    public void removePropertyChangeListener(PropertyChangeListener l);

    public void removePropertyChangeListener(String propertyName,
        PropertyChangeListener l);
}
