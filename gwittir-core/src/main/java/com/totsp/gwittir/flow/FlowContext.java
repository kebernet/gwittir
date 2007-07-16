/*
 * FlowContext.java
 *
 * Created on May 9, 2007, 6:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.flow;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.Action;
import com.totsp.gwittir.ui.BoundWidget;
import java.util.HashMap;

/**
 *
 * @author cooper
 */
public class FlowContext {
    
    private final HashMap destinations = new HashMap();
    private final HashMap actions = new HashMap();
    /** Creates a new instance of FlowContext */
    public FlowContext() {
        super();
    }
    
    public FlowContext add( String name, BoundWidget widget ){
        destinations.put( name, widget );
        return this;
    }
    
    public FlowContext add( String name, BoundWidgetProvider provider ){
        destinations.put( name, provider );
        return this;
    }
     
     public FlowContext add( String name, BoundWidget widget, Action action ){
        add( name, widget );
        actions.put( name, action );
        return this;
    }
    
    
     public FlowContext add( String name, BoundWidgetProvider provider, Action action ){
        add( name, provider );
        actions.put( name, action );
        return this;
    }
    
    public BoundWidget get( String name ){
        Object value =  destinations.get(name);
        BoundWidget ret = null;
        if( value instanceof BoundWidgetProvider ){
            ret = ((BoundWidgetProvider) value).get();
        } else {
            ret =  (BoundWidget) destinations.get(name);
        }
        if( ret != null && actions.get( name ) != null ){
            ret.setAction( (Action) actions.get(name) );
        }
        return ret;
    }
    
    
}
