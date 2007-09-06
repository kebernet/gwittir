/*
 * FlowContext.java
 *
 * Created on May 9, 2007, 6:29 PM
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
package com.totsp.gwittir.client.flow;

import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;


/**
 *
 * @author cooper
 */
public class FlowContext {
    private String fromName = null;
    private final HashMap actions = new HashMap();
    private final HashMap destinations = new HashMap();
    private ArrayList listeners = new ArrayList();
    /** Creates a new instance of FlowContext */
    public FlowContext() {
        super();
    }

    public FlowContext add(String name, BoundWidget widget) {
        destinations.put(name, widget);

        return this;
    }

    public FlowContext add(String name, BoundWidgetProvider provider) {
        destinations.put(name, provider);

        return this;
    }

    public FlowContext add(String name, BoundWidget widget, Action action) {
        add(name, widget);
        actions.put(name, action);

        return this;
    }

    public FlowContext add(String name, BoundWidgetProvider provider,
        Action action) {
        add(name, provider);
        actions.put(name, action);

        return this;
    }

    public BoundWidget get(String name) {
        Object value = destinations.get(name);
        BoundWidget ret;

        if(value instanceof BoundWidgetProvider) {
            ret = ((BoundWidgetProvider) value).get();
        } else {
            ret = (BoundWidget) destinations.get(name);
        }

        if((ret != null) && (actions.get(name) != null)) {
            ret.setAction((Action) actions.get(name));
        }

        return ret;
    }
    
    void fireEvents( String toName, BoundWidget toWidget, BoundWidget fromWidget ){
        FlowEvent e = new FlowEvent( this, fromWidget, fromWidget == null ? null : fromWidget.getModel(),
                this.fromName,
                toWidget, toWidget == null ? null : toWidget.getModel(), toName);
        for(Iterator i = this.listeners.iterator(); i.hasNext(); ){
                ((FlowEventListener) i.next()).onFlowEvent( e );
        }
        this.fromName = toName;
    }
    
    public void addFlowEventListener( FlowEventListener l ){
        this.listeners.add( l );
    }
    
    public void removeFlowEventListener( FlowEventListener l ){
        this.listeners.remove( l );
    }
    
    public FlowEventListener[] getFlowEventListeners(){
        return (FlowEventListener[]) listeners.toArray();
    }
}
