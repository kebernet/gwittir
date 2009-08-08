/*
 * FlowController.java
 *
 * Created on May 9, 2007, 6:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.flow;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.HasWidget;

import java.util.HashMap;
import java.util.Iterator;


/** The FlowController class is used to register FlowContexts to Widget maps for
 * page to page or wizard style application flows. See the package doc for details.
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class FlowController {
    static final HashMap<Object, FlowContext> contexts = new HashMap<Object, FlowContext>();
    static HistoryManager manager = null;
    static final HistoryListener hl = new HistoryListener() {
            public void onHistoryChanged(String historyToken) {
                if (manager != null) {
                    manager.apply(historyToken);
                }
            }
        };

    static {
        History.addHistoryListener(hl);
    }

    /**
     * Registers a managed widget with the given FlowContext
     * @param container the widget to manage
     * @param context The FlowContext it maps to.
     */
    public static void setFlowContext(HasWidgets container, FlowContext context) {
        contexts.put(container, context);
    }

    /**
     * Registers a managed widget with the given FlowContext
     * @param container the widget to manage
     * @param context The FlowContext it maps to.
     */
    public static void setFlowContext(HasWidget container, FlowContext context) {
        contexts.put(container, context);
    }

    /** Registers a history manager for the FlowController globally.
     *
     * @param manager A history manager implementation
     */
    public static void setHistoryManager(HistoryManager manager) {
        FlowController.manager = manager;
    }

    /**
     * Returns the current HistoryManager implementation
     * @return the current HistoryManager implementation
     */
    public static HistoryManager getHistoryManager() {
        return FlowController.manager;
    }

    /**
     *  Calls a new flow context name, from the sender context with the given object model.
     * @param sender The lowest level widget to begin looking for the name
     * @param name The activity name to look for
     * @param model The model object to pass to the registered BoundWidget
     * @return boolean indicating whether the call resulted in a state change.
     */
    public static boolean call(Widget sender, String name, Object model) {
        return call(sender, name, model, true);
    }

    /**
     *  Calls a new flow context name, from the sender context with the given object model.
     * @param sender The lowest level widget to begin looking for the name
     * @param name The activity name to look for
     * @param model The model object to pass to the registered BoundWidget
     * @return boolean indicating whether the call resulted in a state change.
     */
    public static boolean call(Widget sender, String name, Object model, boolean fireEvents) {
        Widget contextRoot = sender;
        FlowContext context = (FlowContext) contexts.get(contextRoot);

        while ((context == null) && (contextRoot != null)) {
            contextRoot = contextRoot.getParent();
            context = (FlowContext) contexts.get(contextRoot);
        }

        if ((context == null) || !(contextRoot instanceof HasWidgets || contextRoot instanceof HasWidget)) {
            throw new RuntimeException("Unknown destination name for this elements context.");
        }

        Object panel = contextRoot;
        BoundWidget<?> widget = context.get(name);

        if (widget == null) {
            return call(contextRoot.getParent(), name, model);
        }

        if (model != null) {
            widget.setModel(model);
        }

        BoundWidget<?> old = null;

        if (panel instanceof SimplePanel) {
            SimplePanel sp = (SimplePanel) panel;

            if (sp.getWidget() instanceof BoundWidget) {
                old = (BoundWidget<?>) sp.getWidget();
            }

            sp.setWidget((Widget) widget);
        } else if (panel instanceof HasWidget) {
            HasWidget hw = (HasWidget) panel;

            if (hw.getWidget() instanceof BoundWidget) {
                old = (BoundWidget<?>) hw.getWidget();
            }

            hw.setWidget((Widget) widget);
        } else if (panel instanceof HasWidgets) {
            HasWidgets hw = (HasWidgets) panel;
            Iterator<Widget> it = hw.iterator();

            while (it.hasNext()) {
                Object next = it.next();

                if (next instanceof BoundWidget) {
                    old = (BoundWidget<?>) next;

                    break;
                }
            }

            hw.clear();
            hw.add((Widget) widget);
        }

        FlowEvent event = context.createEvent(contextRoot, name, widget, old);

        if (fireEvents) {
            context.fireEvent(event);
        }

        manager.transition(event, fireEvents);

        return true;
    }

    /**
     * Find the FlowContext with a name mapping from the low level widget
     * @param widget Widget to begin searching from
     * @param name activity name to find
     * @return a FlowContext or null.
     */
    public static FlowContext findContext(Widget widget, String name) {
        Widget contextRoot = widget;
        FlowContext context = (FlowContext) contexts.get(contextRoot);

        while (((context == null) || (context.get(name) == null)) && (contextRoot != null)) {
            contextRoot = contextRoot.getParent();
            context = (FlowContext) contexts.get(contextRoot);
        }

        return context;
    }

    /**
     * Finds the first managed widget/panel from a source widget that has a name
     * mapped in its flow context
     * @param widget The low-level widget to search from
     * @param name The name to search a flow context for
     * @return the Managed widget that matches first.
     */
    public static Widget findContextWidget(Widget widget, String name) {
        Widget contextRoot = widget;
        FlowContext context = (FlowContext) contexts.get(contextRoot);

        while (((context == null) || (context.get(name) == null)) && (contextRoot != null)) {
            contextRoot = contextRoot.getParent();
            context = (FlowContext) contexts.get(contextRoot);
        }

        return contextRoot;
    }

    /** Unregisters a FlowContext for a manage widget
     *
     * @param container Container to unregister
     * @return boolean if there was a change.
     */
    public static boolean unsetFlowContext(Panel container) {
        return contexts.remove(container) != null;
    }
}
