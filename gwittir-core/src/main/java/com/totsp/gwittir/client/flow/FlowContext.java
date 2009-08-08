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

import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;

import java.util.ArrayList;
import java.util.HashMap;


/** A FlowContext is a registry of BoundWidgets or BoundWidgetProviders and Actions to activity names.
 * Each .add() method returns this, so you can use a builder pattern:
 * FlowContext context = new FlowContext()
 *                                                                 .add()
 *                                                                 .add()
 *                                                                 .add();
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class FlowContext {
    private final HashMap<String, Action<?>> actions = new HashMap<String, Action<?>>();
    private final HashMap<String, BoundWidgetProvider<?extends BoundWidget<?>>> destinations = new HashMap<String, BoundWidgetProvider<?extends BoundWidget<?>>>();
    private ArrayList<FlowEventListener> listeners = new ArrayList<FlowEventListener>();
    private String fromName = null;

    /** Creates a new instance of FlowContext */
    public FlowContext() {
        super();
    }

    /** Returns the currently registered FlowEventListeners
     *
     * @return the currently registered FlowEventListeners
     */
    public FlowEventListener[] getFlowEventListeners() {
        FlowEventListener[] listeners = new FlowEventListener[this.listeners.size()];
        this.listeners.toArray((Object[]) listeners);

        return listeners;
    }

    /** Adds a BoundWidget to a given activity name
     *
     * @param name The activity name to register
     * @param widget The BoundWidget to use
     * @return this (builder pattern)
     */
    public FlowContext add(String name, BoundWidget<?> widget) {
        destinations.put(name, new SimpleProvider<BoundWidget<?>>(widget));

        return this;
    }

    /** Adds a BoundWidgetProvider for a given activity name.
     * This method should be used where the BoundWidget needs to be recreated
     * with each activity call (not a singleton). Optionally you can use a
     * Singleton provider to delay initialization of the BoundWidget until it
     * is needed.
     *
     * @param name The activity name to register
     * @param provider The BoundWidgetProvider to use
     * @return this (builder pattern)
     */
    public FlowContext add(String name, BoundWidgetProvider<?> provider) {
        destinations.put(name, provider);

        return this;
    }

    /** Adds a BoundWidget with an Action mapping
     *
     * @param name The activity name to register
     * @param widget The BoundWidget to use
     * @param action The Action to register with the BoundWidget
     * @return this (builder pattern)
     */
    public FlowContext add(String name, BoundWidget<?> widget, Action action) {
        add(name, widget);
        actions.put(name, action);

        return this;
    }

    /** Adds a BoundWidgetProvider with an Action
     *
     * @param name The activity name to register
     * @param provider The BoundWidgetProvider to use
     * @param action The Action to register with the BoundWidget
     * @return this (builder pattern)
     */
    public FlowContext add(String name, BoundWidgetProvider<?> provider, Action action) {
        add(name, provider);
        actions.put(name, action);

        return this;
    }

    /** Adds a FlowEventListener
     *
     * @param l Listener
     */
    public void addFlowEventListener(FlowEventListener l) {
        this.listeners.add(l);
    }

    /** Returns a BoundWidget instance for a given activity name
     *
     * @param name The activity name to lookup
     * @return BoundWidget or null
     */
    public <T> BoundWidget<T> get(String name) {
        BoundWidgetProvider<BoundWidget<T>> value = (BoundWidgetProvider<BoundWidget<T>>) destinations.get(name);
        BoundWidget<T> ret;

        ret = value.get();

        if ((ret != null) && (actions.get(name) != null)) {
            ret.setAction((Action<BoundWidget<T>>) actions.get(name));
        }

        return ret;
    }

    /** Removes a FlowEventListener
     *
     * @param l listener
     */
    public void removeFlowEventListener(FlowEventListener l) {
        this.listeners.remove(l);
    }

    FlowEvent createEvent(Widget managedWidget, String toName, BoundWidget<?> toWidget, BoundWidget<?> fromWidget) {
        FlowEvent e = new FlowEvent(
                this, managedWidget, fromWidget, (fromWidget == null) ? null
                                                                      : fromWidget.getModel(), this.fromName, toWidget,
                (toWidget == null) ? null
                                   : toWidget.getModel(), toName);
        this.fromName = e.getToName();

        return e;
    }

    void fireEvent(FlowEvent e) {
        for (FlowEventListener listener : this.listeners) {
            listener.onFlowEvent(e);
        }
    }

    private static class SimpleProvider<T extends BoundWidget<?>> implements BoundWidgetProvider<T> {
        private T widget;

        public SimpleProvider(T widget) {
            this.widget = widget;
        }

        public T get() {
            return this.widget;
        }
    }
}
