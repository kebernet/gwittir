/*
 * GwailsWidget.java
 *
 * Created on April 12, 2007, 12:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.SourcesChangeEvents;

import com.totsp.gwittir.Action;
import com.totsp.gwittir.beans.Bindable;

import java.util.Comparator;


/**
 *
 * @author cooper
 */
public interface BoundWidget extends Bindable, SourcesChangeEvents {
    public Action getAction();

    public Comparator getComparator();

    public Object getModel();

    public Renderer getRenderer();

    public Object getValue();

    public void setAction(Action action);

    public void setComparator(Comparator comparator);

    public void setModel(Object model);

    public void setRenderer(Renderer renderer);

    public void setValue(Object value);
}
