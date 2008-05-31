/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.action.Action;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 *
 * @author kebernet
 */
public class ContextMenuPanel<B, V> extends SimplePanel implements BoundWidget<B, V> {
    private BoundWidget<B, V> internal;
    private List<Widget> menuItems = new ArrayList<Widget>();
    protected PopupPanel popup = new PopupPanel(true, true);
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private VerticalPanel panel = new VerticalPanel();
    private boolean rightDown = false;

    public ContextMenuPanel(BoundWidget<B, V> internal) {
        super();
        this.internal = internal;
        setWidget((Widget) internal);
        this.sinkEvents(Event.MOUSEEVENTS);
        this.disableContext(this.getElement());
        this.disableSelect(this.getElement());
        this.panel.setStyleName("gwittir-ContextMenu");
        this.popup.setWidget(panel);
    }

    public void addChangeListener(ChangeListener arg0) {
    }

    public void addMenuItemWidget(Widget w) {
        this.menuItems.add(w);

        if (w instanceof MenuItem) {
            ((MenuItem) w).setMenu(this);
        }

        if (w instanceof SubMenu) {
            ((SubMenu) w).setMenu(this);
        }

        this.panel.add(w);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(propertyName, l);
    }

    private native void disableContext(Element elem) /*-{
    elem.oncontextmenu=function(a,b) {return false};
    }-*/;

    private native void disableSelect(Element elem) /*-{
    elem.onselectstart=function(a,b) {return false};
    }-*/;

    public Action getAction() {
        return this.internal.getAction();
    }

    public Comparator getComparator() {
        return null;
    }

    public BoundWidget<B, V> getInternal() {
        return this.internal;
    }

    public List<Widget> getMenuItemWidgets() {
        return this.menuItems;
    }

    public Object getModel() {
        return internal.getModel();
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.changes.getPropertyChangeListeners();
    }

    public Renderer<V, B> getRenderer() {
        return null;
    }

    public V getValue() {
        return internal.getValue();
    }

    public void hide() {
        this.popup.hide();
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONMOUSEDOWN:

            if ((DOM.eventGetButton(event) == Event.BUTTON_RIGHT) ||
                    DOM.eventGetCtrlKey(event)) {
                rightDown = true;
            } else {
                rightDown = false;
            }

            break;

        case Event.ONMOUSEUP:

            if (rightDown) {
                int x = DOM.eventGetClientX(event);
                int y = DOM.eventGetClientY(event);
                this.popup.show();
                this.popup.setPopupPosition(x, y);
                DOM.eventCancelBubble(event, true);
            } else {
                rightDown = false;
            }

            break;

        default:
            break;
        }
    }

    public void removeChangeListener(ChangeListener arg0) {
    }

    public boolean removeMenuItemWidget(Widget w) {
        this.menuItems.remove(w);

        return this.panel.remove(w);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(propertyName, l);
    }

    public void setAction(Action action) {
        this.internal.setAction(action);
    }

    public void setComparator(Comparator comparator) {
    }

    public void setInternal(BoundWidget<B, V> internal) {
        this.remove((Widget) this.internal);
        super.setWidget((Widget) internal);
        this.changes.firePropertyChange("internal", this.internal,
            this.internal = internal);
    }

    public void setModel(Object model) {
        internal.setModel(model);
    }

    public void setRenderer(Renderer<V, B> renderer) {
    }

    public void setValue(B value) {
        internal.setValue(value);
    }

    public static class MenuItem extends Button {
        public MenuItem(String text) {
            super(text);
            this.setStyleName("gwittir-ContextMenuItem");
        }

        public MenuItem(String text, ClickListener listener) {
            super();
        }

        void setMenu(final ContextMenuPanel panel) {
            this.addClickListener(new ClickListener() {
                    public void onClick(Widget w) {
                        panel.hide();
                    }
                });
        }
    }

    public static class SubMenu extends ContextMenuPanel {
        ContextMenuPanel panel;

        public SubMenu(String text) {
            super(new Button(text));
            ((Widget)this.getInternal()).setStyleName("gwittir-ContextSubMenu");
        }

        public void hide() {
            this.popup.hide();
            this.panel.hide();
        }

        @Override
        public void onBrowserEvent(Event event) {
            switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEDOWN:

                Widget w = (Widget) this.getInternal();
                int x = w.getAbsoluteLeft() + w.getOffsetWidth();
                int y = w.getAbsoluteTop();
                this.popup.show();
                this.popup.setPopupPosition(x, y);
                DOM.eventCancelBubble(event, true);

                break;

            default:
                break;
            }
        }

        void setMenu(final ContextMenuPanel panel) {
            this.panel = panel;
            ((Button) this.getInternal()).addClickListener(new ClickListener() {
                    public void onClick(Widget w) {
                        hide();
                    }
                });
        }
    }
}
