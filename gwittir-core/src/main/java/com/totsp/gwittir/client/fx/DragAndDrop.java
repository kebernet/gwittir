package com.totsp.gwittir.client.fx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
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
    private static final DraggedPlaceholderStrategy DEFAULT_DPS = new DraggedPlaceholderStrategy() {
            public void setupPlaceholderElement(Widget dragged, Element element) {
                DOM.setInnerHTML(element, dragged.toString());
                DOM.setStyleAttribute(element, "width", dragged.getOffsetWidth() + "px");
                DOM.setStyleAttribute(element, "height", dragged.getOffsetHeight() + "px");
                DOM.setStyleAttribute(element, "background", "#CCCCCC");
                DOM.setStyleAttribute(element, "opacity", ".5");
            }
        };

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

    public void setDraggedPlaceholderStrategy(Widget draggable, DraggedPlaceholderStrategy strat) {
        Draggable d = this.draggables.get(draggable);
        d.strat = strat;
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

    public void removeDraggable(SourcesMouseEvents w) {
        Draggable d = this.draggables.get((Widget) w);
        w.removeMouseListener(d.listener);
        this.draggables.remove(d.widget);
    }

    public void removeDropListeners(SourcesMouseEvents w, DropListener dl) {
        List<DropListener> listeners = (dropListeners.get((Widget) w) != null) ? dropListeners.get((Widget) w)
                                                                               : new ArrayList<DropListener>();
        listeners.remove(dl);
    }

    public void removeDroppable(Widget w) {
        this.dropListeners.remove(w);
        this.dropTargets.remove(w);
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

            placeholder = DOM.createDiv();

            if (dragging.strat == null) {
                DEFAULT_DPS.setupPlaceholderElement(dragging.widget, placeholder);
            } else {
                dragging.strat.setupPlaceholderElement(dragging.widget, placeholder);
            }

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
        public DraggedPlaceholderStrategy strat;
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
        private Widget lastHover = null;

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

                Widget newHover = calc(sender, x, y, false);
                Logger.getAnonymousLogger()
                      .log(Level.SPAM, "newHover: " + newHover, null);

                if ((lastHover != null) && (lastHover != newHover) && dropListeners.containsKey(lastHover)) {
                    for (DropListener l : dropListeners.get(lastHover)) {
                        l.onEndHover(sender);
                    }
                }

                lastHover = newHover;
            }
        }

        public void onMouseUp(Widget sender, int x, int y) {
            if (isDragging) {
                calc(sender, x, y, true);
            }
        }

        private Widget calc(Widget sender, int x, int y, boolean drop) {
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

            Widget hit = null;

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

                        if ((drop && l.canDrop(dragging.widget)) || (!drop && l.onHover(sender))) {
                            if (drop) {
                                //Make sure we revert the widget before the drop event in case the user
                                // wants to do something with it.
                                if (revert) {
                                    DOM.removeChild(DOM.getParent(dragging.widget.getElement()), placeholder);
                                    dragging.widget.getElement()
                                                   .getStyle()
                                                   .setProperty("display", dragging.display);
                                    hide();
                                    placeholder = null;
                                }

                                l.onDrop(dragging.widget);
                            }

                            hit = w;

                            break;
                        }
                    }
                }
            }

            if (drop && revert && (hit == null)) {
                DOM.removeChild(DOM.getParent(dragging.widget.getElement()), placeholder);
                dragging.widget.getElement()
                               .getStyle()
                               .setProperty("display", dragging.display);
                hide();
                placeholder = null;
            }

            return hit;
        }
    }
}
