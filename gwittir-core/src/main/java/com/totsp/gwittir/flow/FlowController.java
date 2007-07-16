/*
 * FlowController.java
 *
 * Created on May 9, 2007, 6:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.flow;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.ui.BoundWidget;

import java.util.HashMap;
import java.util.Iterator;


/**
 *
 * @author cooper
 */
public class FlowController {
    static final HashMap /*<Widget, FlowContext>*/ contexts = new HashMap();
    static HistoryManager manager = null;
    static final HistoryListener hl = new HistoryListener() {
            public void onHistoryChanged(String historyToken) {
                if(manager != null) {
                    manager.apply(historyToken);
                }
            }
        };

    static {
        History.addHistoryListener(hl);
    }

    public static boolean call(Widget sender, String name, Object model) {
        Widget contextRoot = sender;
        FlowContext context = (FlowContext) contexts.get(contextRoot);

        while((context == null) && (contextRoot != null)) {
            contextRoot = contextRoot.getParent();
            context = (FlowContext) contexts.get(contextRoot);
        }

        if(context == null) {
            throw new RuntimeException(
                "Unknown destination name for this elements context.");
        }

        Panel panel = (Panel) contextRoot;
        BoundWidget widget = context.get(name);

        if(widget == null) {
            call(contextRoot.getParent(), name, model);
        }

        widget.setModel(model);

        BoundWidget old = null;
        Iterator it = panel.iterator();

        while(it.hasNext()) {
            Object next = it.next();

            if(next instanceof BoundWidget) {
                old = (BoundWidget) next;

                break;
            }
        }

        panel.clear();
        panel.add((Widget) widget);

        if(FlowController.manager != null) {
            manager.transition(name, old, widget);
        }

        return true;
    }

    public static Widget findContextWidget(Widget widget, String name) {
        Widget contextRoot = widget;
        FlowContext context = (FlowContext) contexts.get(contextRoot);

        while((context == null) && (contextRoot != null)) {
            contextRoot = contextRoot.getParent();
            context = (FlowContext) contexts.get(contextRoot);
        }

        return contextRoot;
    }

    public static HistoryManager getHistoryManager() {
        return FlowController.manager;
    }

    public static void setFlowContext(Panel container, FlowContext context) {
        contexts.put(container, context);
    }

    public static void setHistoryManager(HistoryManager manager) {
        FlowController.manager = manager;
    }

    public static boolean unsetFlowContext(Panel container) {
        return contexts.remove(container) != null;
    }
}
