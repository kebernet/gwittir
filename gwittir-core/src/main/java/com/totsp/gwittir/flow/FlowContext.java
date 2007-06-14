/*
 * FlowContext.java
 *
 * Created on May 9, 2007, 6:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.flow;

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
    
    public BoundWidget get( String name ){
        return (BoundWidget) destinations.get(name);
    }
    
    
}
