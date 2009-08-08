/*
 * AbstractCollectionContainer.java
 *
 * Created on November 30, 2007, 2:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui;

import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.beans.Introspectable;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.ui.util.ActionProvider;
import com.totsp.gwittir.client.ui.util.ActionTypeFactory;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;


/**
 *
 * @author rcooper
 */
public class AbstractCollectionContainer<T> extends
        AbstractBoundWidget<Collection<T>>implements HasWidgets {
    private ActionTypeFactory actionFactory;
    private BoundWidgetTypeFactory factory;
    private Collection<T> value;
    private HasWidgets base;

    /** Creates a new instance of AbstractCollectionContainer */
    public AbstractCollectionContainer(
        final HasWidgets base, final BoundWidgetTypeFactory factory, final ActionTypeFactory actionFactory
    ) {
        super();
        assert factory != null : "You must provide a BoundWidgetTypeFactory.";
        this.base = base;
        this.setFactory(factory);
        this.setActionFactory(actionFactory);
        initWidget((Widget)base);
    }

    public AbstractCollectionContainer(
        final HasWidgets base, final BoundWidgetTypeFactory factory, final ActionTypeFactory actionFactory,
        final Collection value
    ) {
        this(base, factory, actionFactory);
        this.setValue(value);
    }

    public void setActionFactory(final ActionTypeFactory actionFactory) {
        this.actionFactory = actionFactory;
    }

    public ActionTypeFactory getActionFactory() {
        return actionFactory;
    }

    public void setFactory(final BoundWidgetTypeFactory factory) {
        this.factory = factory;
    }

    public BoundWidgetTypeFactory getFactory() {
        return factory;
    }

    public void setValue(final Collection<T> value) {
        final Collection<T> old = this.value;
        this.value = value;
        this.render();
        this.changes.firePropertyChange("value", old, value);
    }

    public Collection<T> getValue() {
        return this.value;
    }

    public void add(final Widget w) {
        this.base.add(w);
    }

    public void clear() {
        this.base.clear();

        if (this.value != null) {
            this.value.clear();
        }
    }

    public Iterator<Widget> iterator() {
        return this.base.iterator();
    }

    public boolean remove(final Widget w) {
        return this.base.remove(w);
    }

    private void render() {
        this.base.clear();

        if ((this.value == null) || (this.value.size() == 0)) {
            return;
        }

        for (Iterator it = this.value.iterator(); it.hasNext();) {
            Introspectable o = (Introspectable) it.next();
            BoundWidget w = this.getFactory().getWidgetProvider(Introspector.INSTANCE.resolveClass(o)).get();
            w.setModel(o);

            ActionProvider ap = this.getActionFactory().getActionProvider(Introspector.INSTANCE.resolveClass(w));

            if (ap != null) {
                w.setAction(ap.get());
            }

            base.add((Widget) w);
        }
    }

    
}
