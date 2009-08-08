/*
 * ToggleEditContainer.java
 *
 * Created on October 25, 2007, 1:14 PM
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
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.fx.MutationStrategy;
import com.totsp.gwittir.client.fx.OpacityWrapper;
import com.totsp.gwittir.client.fx.PositionWrapper;
import com.totsp.gwittir.client.fx.PropertyAnimator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 * @author cooper
 */
public class ToggleEditContainer extends AbstractBoundWidget {
    private BoundWidget display;
    private BoundWidget edit;
    private FocusPanel focus = new FocusPanel();
    private Object firstValue;
    private SimplePanel marker = new SimplePanel();
    private SimplePanel root = new SimplePanel();
    private boolean hasChanged = false;
    private boolean hasChangedOnce = false;

    /** Creates a new instance of ToggleEditContainer */
    public ToggleEditContainer(final BoundWidget display, final BoundWidget edit) {
        this.display = display;
        this.edit = edit;
        ((Widget) display).setWidth("100%");
        ((Widget) edit).setWidth("100%");
        focus.setWidth("100%");
        focus.setWidget((Widget) this.display);
        root.setWidget(focus);
        super.initWidget(root);
        focus.addFocusListener(
            new FocusListener() {
                public void onLostFocus(Widget sender) {
                }

                public void onFocus(Widget sender) {
                    root.setWidget((Widget) edit);

                    if (edit instanceof HasFocus) {
                        ((HasFocus) edit).setFocus(true);
                    }
                }
            });
        focus.addClickListener(
            new ClickListener() {
                public void onClick(Widget sender) {
                    root.setWidget((Widget) edit);

                    if (edit instanceof HasFocus) {
                        ((HasFocus) edit).setFocus(true);
                    }
                }
            });

        if (edit instanceof HasFocus) {
            ((HasFocus) edit).addFocusListener(
                new FocusListener() {
                    public void onLostFocus(Widget sender) {
                        root.setWidget((Widget) focus);
                    }

                    public void onFocus(Widget sender) {
                    }
                });
        }

        edit.addPropertyChangeListener(
            "value",
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                    if (!hasChangedOnce) {
                        hasChangedOnce = true;
                        firstValue = propertyChangeEvent.getOldValue();
                    }

                    hasChanged = true;
                    showChangedIndicator();
                }
            });
        marker.setStyleName("gwittir-ChangeMarker");
    }

    public void setModel(Object model) {
        this.edit.setModel(model);
        this.display.setModel(model);
    }

    public Object getModel() {
        return this.edit.getModel();
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.edit.getPropertyChangeListeners();
    }

    public void setValue(Object value) {
        this.edit.setValue(value);
        this.display.setValue(value);
    }

    public Object getValue() {
        return this.edit.getValue();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        this.edit.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        this.edit.addPropertyChangeListener(propertyName, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        this.edit.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        this.edit.removePropertyChangeListener(propertyName, l);
    }

    protected void onAttach() {
        super.onAttach();
        showChangedIndicator();
    }

    protected void onDetach() {
        super.onDetach();

        if (this.hasChanged) {
            RootPanel.get()
                     .remove(this.marker);
        }
    }

    private void showChangedIndicator() {
        if (this.isAttached() && this.hasChanged) {
            OpacityWrapper o = new OpacityWrapper(marker);
            o.setOpacity(new Double(0.0));
            RootPanel.get()
                     .add(this.marker);

            PositionWrapper w = new PositionWrapper(marker);
            w.setPosition("absolute");
            w.setTop(this.root.getAbsoluteTop() + "px");
            w.setRight(this.root.getAbsoluteLeft() + this.root.getOffsetWidth() + "px");

            PropertyAnimator a = new PropertyAnimator(o, "opacity", new Double(1), MutationStrategy.DOUBLE_CUBIC, 250);
            a.start();
        }
    }
}
