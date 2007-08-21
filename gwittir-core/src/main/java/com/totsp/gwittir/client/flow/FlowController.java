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

        if(context == null || !(contextRoot instanceof HasWidgets || contextRoot instanceof HasWidget )) {
            throw new RuntimeException(
                "Unknown destination name for this elements context.");
        }

        Object panel = contextRoot;
        BoundWidget widget = context.get(name);

        if(widget == null) {
            return call(contextRoot.getParent(), name, model);
        }
        if( model != null ){
            widget.setModel(model);
        }
        BoundWidget old = null;
        

        if( panel instanceof SimplePanel ){
            SimplePanel sp = (SimplePanel) panel;
            if( sp.getWidget() instanceof BoundWidget ){
                old = (BoundWidget) sp.getWidget();
            }
            sp.setWidget( (Widget) widget );
        } else if( panel instanceof HasWidget ){
            HasWidget hw = (HasWidget) panel;
            if( hw.getWidget() instanceof BoundWidget ){
                old = (BoundWidget) hw.getWidget();
            }
            hw.setWidget( (Widget) widget );
        } else if( panel instanceof HasWidgets ){
            HasWidgets hw = (HasWidgets) panel;
            Iterator it = hw.iterator();
            while(it.hasNext()) {
                Object next = it.next();

                if(next instanceof BoundWidget) {
                    old = (BoundWidget) next;

                    break;
                }
            }
            hw.clear();
            hw.add((Widget) widget );
        }

        if(FlowController.manager != null) {
            manager.transition(name, old, widget);
        }
        context.fireEvents( name, widget, old );

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

    public static void setFlowContext(HasWidgets container, FlowContext context) {
        contexts.put(container, context);
    }
    
    public static void setFlowContext(HasWidget container, FlowContext context) {
        contexts.put(container, context);
    }

    public static void setHistoryManager(HistoryManager manager) {
        FlowController.manager = manager;
    }

    public static boolean unsetFlowContext(Panel container) {
        return contexts.remove(container) != null;
    }
}
