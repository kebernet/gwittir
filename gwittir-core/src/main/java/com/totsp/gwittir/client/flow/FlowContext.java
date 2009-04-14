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

import java.util.ArrayList;
import java.util.HashMap;

import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class FlowContext {
    private String fromName = null;
    private final HashMap<String,Action> actions = new HashMap<String,Action>();
    private final HashMap<String,BoundWidgetProvider<?>> destinations = new HashMap<String,BoundWidgetProvider<?>>();
    private ArrayList<FlowEventListener> listeners = new ArrayList<FlowEventListener>();
    /** Creates a new instance of FlowContext */
    public FlowContext() {
        super();
    }

    public <T> FlowContext add(String name, BoundWidget<T> widget) {
        destinations.put( name, new SimpleProvider<BoundWidget<T>>(widget));

        return this;
    }

    public FlowContext add(String name, BoundWidgetProvider<?> provider) {
        destinations.put(name, provider);

        return this;
    }

    public FlowContext add(String name, BoundWidget<?> widget, Action action) {
        add(name, widget);
        actions.put(name, action);

        return this;
    }

    public FlowContext add(String name, BoundWidgetProvider<?> provider,
        Action action) {
        add(name, provider);
        actions.put(name, action);

        return this;
    }

    public BoundWidget<?> get(String name) {
        Object value = destinations.get(name);
        BoundWidget<?> ret;

        ret = ((BoundWidgetProvider<?>) value).get();
        

        if((ret != null) && (actions.get(name) != null)) {
            ret.setAction((Action) actions.get(name));
        }

        return ret;
    }
    
    void fireEvents( String toName, BoundWidget<?> toWidget, BoundWidget<?> fromWidget ){
        FlowEvent e = new FlowEvent( this, fromWidget, fromWidget == null ? null : fromWidget.getModel(),
                this.fromName,
                toWidget, toWidget == null ? null : toWidget.getModel(), toName);
        for(FlowEventListener listener : this.listeners ){
                listener.onFlowEvent( e );
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
        FlowEventListener[] listeners = new FlowEventListener[this.listeners.size() ];
        this.listeners.toArray((Object[]) listeners);
        return listeners;
    }
    
    private static class SimpleProvider<T extends BoundWidget<?>> implements BoundWidgetProvider<T> {

    	private T widget;
    	public SimpleProvider(T widget){
    		this.widget = widget;
    	}
    	
		public T get() {
			return this.widget;
		}
    	
    }
}
