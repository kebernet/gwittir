package com.totsp.gwittir.client.fx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DragAndDrop {
    private static final DragAndDrop instance = new DragAndDrop();
    private Draggable dragging;
    private Element placeholder;
    private HashMap<Widget, Draggable> draggables = new HashMap<Widget, Draggable>();
    private HashMap<Widget, List<DropListener>> dropListeners = new HashMap<Widget, List<DropListener>>();
    private List<Widget> dropTargets;

    private DragAndDrop() {
    }

    public static DragAndDrop getInstance() {
        return instance;
    }

    public void addDropListener(Widget w, DropListener dl) {
        List<DropListener> listeners = (dropListeners.get(w) != null) ? dropListeners.get(w)
                                                                      : new ArrayList<DropListener>();
        listeners.add(dl);
        dropListeners.put(w, listeners);
    }

    public void makeDraggable(SourcesMouseEvents w, boolean revert) {
        Draggable d = new Draggable();
        d.widget = (Widget) w;
        d.listener = new DragSupportListener((Widget) w, revert);
        w.addMouseListener(d.listener);
        draggables.put(d.widget, d);
    }

    public void makeDroppable(Widget w) {
        dropTargets = (dropTargets == null) ? new ArrayList<Widget>()
                                            : dropTargets;
        dropTargets.add(w);
    }

    public void removeDropListeners(SourcesMouseEvents w, DropListener dl) {
        List<DropListener> listeners = (dropListeners.get((Widget) w) != null) ? dropListeners.get((Widget) w)
                                                        : new ArrayList<DropListener>();
        listeners.remove(dl);
    }

    class DragSupportListener extends MouseListenerAdapter {
        DraggablePopupPanel p;
        private boolean revert;

        public DragSupportListener(Widget w, boolean revert) {
            this.revert = revert;
        }

        @Override
        public void onMouseDown(Widget sender, int x, int y) {
            super.onMouseDown(sender, x, y);

            if (dragging != null) {
                dragging.listener.onMouseUp(dragging.widget, 0, 0);
            }

            dragging = (Draggable) draggables.get(sender);

            int ix = dragging.widget.getAbsoluteLeft();
            int iy = dragging.widget.getAbsoluteTop();
            GWT.log(ix + "::" + iy, null);

            placeholder = DOM.createDiv();
            DOM.setInnerHTML(placeholder, sender.toString());
            DOM.setStyleAttribute(placeholder, "width", dragging.widget.getOffsetWidth() + "px");
            DOM.setStyleAttribute(placeholder, "height", dragging.widget.getOffsetHeight() + "px");
            DOM.setStyleAttribute(placeholder, "background", "#CCCCCC");
            DOM.setStyleAttribute(placeholder, "opacity", ".5");

            int index = DOM.getChildIndex(DOM.getParent(dragging.widget.getElement()), dragging.widget.getElement());
            DOM.insertChild(DOM.getParent(dragging.widget.getElement()), placeholder, index);
            p = new DraggablePopupPanel();
            p.p.setWidget(revert ? new HTML(dragging.widget.toString())
                                 : dragging.widget);

            if (revert) {
                dragging.display = dragging.widget.getElement()
                                                  .getStyle()
                                                  .getProperty("display");
                dragging.widget.getElement()
                               .getStyle()
                               .setProperty("display", "none");
            }

            p.revert = revert;
            p.setPopupPosition(ix, iy);
            p.show();
            p.onMouseDown(p.p, x, y);
        }

        @Override
        public void onMouseMove(Widget sender, int x, int y) {
        }

        @Override
        public void onMouseUp(Widget sender, int x, int y) {
            p.onMouseUp(p.p, x, y);
        }
    }

    private class Draggable {
        public DragSupportListener listener;
        public String display;
        public Widget widget;
        public int lowerX;
        public int lowerY;
        public int upperX;
        public int upperY;
    }

    private class DraggablePopupPanel extends PopupPanel implements MouseListener {
        FocusPanel p = new FocusPanel();
        boolean isDragging;
        boolean revert;
        int dragStartX;
        int dragStartY;

        DraggablePopupPanel() {
            this.setWidget(p);
            p.addMouseListener(this);
            this.setStyleName("gwittir-Draggable");
        }

        @Override
        public boolean onEventPreview(Event event) {
            if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
                if (DOM.isOrHasChild(p.getElement(), DOM.eventGetTarget(event))) {
                    DOM.eventPreventDefault(event);
                }
            }

            return super.onEventPreview(event);
        }

        public void onMouseDown(Widget sender, int x, int y) {
            isDragging = true;
            DOM.setCapture(p.getElement());
            dragStartX = x;
            dragStartY = y;
        }

        public void onMouseEnter(Widget sender) {
        }

        public void onMouseLeave(Widget sender) {
        }

        public void onMouseMove(Widget sender, int x, int y) {
            if (isDragging) {
                int absX = x + getAbsoluteLeft();
                int absY = y + getAbsoluteTop();
                setPopupPosition(absX - dragStartX, absY - dragStartY);
                calc(sender, x, y, false);
            }
        }

        public void onMouseUp(Widget sender, int x, int y) {
            if (isDragging) {
                calc(sender, x, y, true);
            }
        }

        private void calc(Widget sender, int x, int y, boolean drop) {
            if (drop) {
                isDragging = false;
                dragStartX = (x + getAbsoluteLeft()) - dragStartX;
                dragStartY = (y + getAbsoluteTop()) - dragStartY;

                DOM.releaseCapture(p.getElement());
            }

            int top = this.getAbsoluteTop();
            int left = this.getAbsoluteLeft();
            Logger.getAnonymousLogger()
                  .log(
                Level.SPAM,
                "Drop: " + drop + "Top:" + top + " Left:" + left + "OffsetTop" + this.getOffsetHeight() +
                " OffsetLeft:" + this.getOffsetWidth(), null);

            int centerY = top + (int) ((float) this.getOffsetHeight() / (float) 2);
            int centerX = left + (int) ((float) this.getOffsetWidth() / (float) 2);
            Logger.getAnonymousLogger()
                  .log(Level.SPAM, "Drop: " + drop + "Center Top:" + centerY + " Center Left:" + centerX, null);

            for (int i = 0; (dropTargets != null) && (i < dropTargets.size()); i++) {
                Widget w = (Widget) dropTargets.get(i);
                Logger.getAnonymousLogger()
                      .log(
                    Level.SPAM,
                    "Drop: " + drop + "Top Range: " + w.getAbsoluteTop() + " .. " +
                    (w.getAbsoluteTop() + w.getOffsetHeight()), null);
                Logger.getAnonymousLogger()
                      .log(
                    Level.SPAM,
                    "Drop: " + drop + "Left Range: " + w.getAbsoluteLeft() + " .. " +
                    (w.getAbsoluteLeft() + w.getOffsetWidth()), null);

                if (
                    (centerY >= w.getAbsoluteTop()) && (centerY <= (w.getAbsoluteTop() + w.getOffsetHeight())) &&
                        (centerX >= w.getAbsoluteLeft()) && (centerX <= (w.getAbsoluteLeft() + w.getOffsetWidth()))) {
                    List<DropListener> listeners = dropListeners.get(w);

                    for (int j = 0; (listeners != null) && (j < listeners.size()); j++) {
                        DropListener l = (DropListener) listeners.get(j);

                        if ((drop && l.onDrop(dragging.widget)) || (!drop && l.onHover(sender))) {
                            break;
                        }
                    }
                }
            }

            if (drop && revert) {
                DOM.removeChild(DOM.getParent(dragging.widget.getElement()), placeholder);
                dragging.widget.getElement()
                               .getStyle()
                               .setProperty("display", dragging.display);
                hide();
                placeholder = null;
            }
        }
    }
}
