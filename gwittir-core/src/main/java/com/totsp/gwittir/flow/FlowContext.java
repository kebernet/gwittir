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
import com.totsp.gwittir.ui.BoundWidget;
import java.util.HashMap;

/**
 *
 * @author cooper
 */
public class  FlowContext {
    
    private final HashMap destinations = new HashMap();
    
    /** Creates a new instance of FlowContext */
    public FlowContext() {
        super();
    }
    
    public FlowContext add( String name, BoundWidget widget ){
        destinations.put( name, widget );
        return this;
    }
    
    public FlowContext add( String name, Class boundWidgetClass ){
        destinations.put( name, boundWidgetClass );
        return this;
    }
    
    public BoundWidget get( String name ){
        Object value =  destinations.get(name);
        if( value instanceof Class ){
            return (BoundWidget) GWT.create( (Class) value );
        }
        if( value instanceof BoundWidgetProvider ){
            return ((BoundWidgetProvider) value).get();
        }
        return (BoundWidget) destinations.get(name);
    }
    
    
}
