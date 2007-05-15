/*
 * GwailsWidget.java
 *
 * Created on April 12, 2007, 12:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.totsp.gwittir.Action;
import com.totsp.gwittir.BindingAction;
import java.util.Comparator;

/**
 *
 * @author cooper
 */
public interface BoundWidget extends SourcesChangeEvents {
    
    public Object getModel();
    
    public void setModel(Object model);
    
    public Action getAction();
    
    public void setAction(Action action);
    
    public Renderer getRenderer();
    
    public void setRenderer(Renderer renderer);
    
    public Comparator getComparator();
    
    public void setComparator(Comparator comparator);
    
    public Object getValue();
    
    public void setValue(Object value);
    
    
}
