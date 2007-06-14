/*
 * FlowController.java
 *
 * Created on May 9, 2007, 6:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.flow;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.ui.BoundWidget;
import java.util.HashMap;

/**
 *
 * @author cooper
 */
public class FlowController {
    
    static final HashMap/*<Widget, FlowContext>*/ contexts = new HashMap();
    
    public static void setFlowContext( Panel container, FlowContext context ){
        contexts.put( container, context );
    }
    
    public static boolean unsetFlowContext( Panel container ){
        return contexts.remove( container ) != null;
    }
    
    public static boolean call( Widget sender, String name, Object model ){
        
        Widget contextRoot = sender;
        FlowContext context = (FlowContext) contexts.get( contextRoot );
        while( context == null && contextRoot != null ){
            contextRoot = contextRoot.getParent();
            context = (FlowContext) contexts.get( contextRoot );
        }
        Panel panel = (Panel) contextRoot;
        BoundWidget widget = context.get(name);
        if( widget == null ){
            throw new RuntimeException( "Unknown destination name for this elements context.");
        }
        widget.setModel( model );
        panel.clear();
        panel.add( (Widget) widget );
        return true;
    }
}
