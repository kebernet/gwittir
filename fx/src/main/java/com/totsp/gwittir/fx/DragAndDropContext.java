package com.totsp.gwittir.fx;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DragAndDropContext {
    private static final DraggedPlaceholderStrategy DEFAULT_DPS = new DraggedPlaceholderStrategy() {
            public void setupPlaceholderElement(Widget dragged, Element element) {
                DOM.setInnerHTML(element, dragged.toString());
                DOM.setStyleAttribute(element, "width",
                    dragged.getOffsetWidth() + "px");
                DOM.setStyleAttribute(element, "height",
                    dragged.getOffsetHeight() + "px");
                DOM.setStyleAttribute(element, "background", "#CCCCCC");
                DOM.setStyleAttribute(element, "opacity", ".5");
            }
        };

    private Draggable dragging;
    private Element placeholder;
    private HashMap<Widget, Draggable> draggables = new HashMap<Widget, Draggable>();
    private HashMap<Widget, List<DropListener>> dropListeners = new HashMap<Widget, List<DropListener>>();
    private List<Widget> dropTargets;

    public DragAndDropContext() {
    }

    public void setDraggedPlaceholderStrategy(Widget draggable,
        DraggedPlaceholderStrategy strat) {
        Draggable d = this.draggables.get(draggable);
        d.strat = strat;
    }

    public void addDragListener(SourcesMouseEvents w, DragListener l) {
        Draggable d = this.draggables.get((Widget) w);
        d.listeners.add(l);
    }

    public void addDropListener(Widget w, DropListener dl) {
        List<DropListener> listeners = (dropListeners.get(w) != null)
            ? dropListeners.get(w) : new ArrayList<DropListener>();
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

    public void removeDragListener(SourcesMouseEvents w, DragListener l) {
        Draggable d = this.draggables.get((Widget) w);
        d.listeners.remove(l);
    }

    public void removeDraggable(SourcesMouseEvents w) {
        Draggable d = this.draggables.get((Widget) w);
        w.removeMouseListener(d.listener);
        this.draggables.remove(d.widget);
    }

    public void removeDropListeners(SourcesMouseEvents w, DropListener dl) {
        List<DropListener> listeners = (dropListeners.get((Widget) w) != null)
            ? dropListeners.get((Widget) w) : new ArrayList<DropListener>();
        listeners.remove(dl);
    }

    public void removeDroppable(Widget w) {
        this.dropListeners.remove(w);
        this.dropTargets.remove(w);
    }

    class DragSupportListener extends MouseListenerAdapter {
        DraggablePopupPanel p;
        boolean cancelled;
        private boolean revert;

        public DragSupportListener(Widget w, boolean revert) {
            this.revert = revert;
        }

        @Override
        public void onMouseDown(final Widget sender, final int x, final int y) {
            super.onMouseDown(sender, x, y);

            if (dragging != null) {
                dragging.listener.onMouseUp(dragging.widget, 0, 0);
            }

            cancelled = false;

            Timer t = new Timer() {
                    @Override
                    public void run() {
                        if (cancelled) {
                            return;
                        }

                        dragging = (Draggable) draggables.get(sender);

                        int ix = dragging.widget.getAbsoluteLeft();
                        int iy = dragging.widget.getAbsoluteTop();

                        placeholder = DOM.createDiv();

                        if (revert) {
                            if (dragging.strat == null) {
                                DEFAULT_DPS.setupPlaceholderElement(dragging.widget,
                                    placeholder);
                            } else {
                                dragging.strat.setupPlaceholderElement(dragging.widget,
                                    placeholder);
                            }
                        }

                        int index = DOM.getChildIndex(DOM.getParent(
                                    dragging.widget.getElement()),
                                dragging.widget.getElement());
                        DOM.insertChild(DOM.getParent(
                                dragging.widget.getElement()), placeholder,
                            index);
                        p = new DraggablePopupPanel(dragging);
                        p.setWidth(dragging.widget.getOffsetWidth() + "px");
                        p.setHeight(dragging.widget.getOffsetHeight() + "px");
                        p.p.setWidget(revert
                            ? new HTML(dragging.widget.toString())
                            : dragging.widget);

                        if (revert) {
                            dragging.display = dragging.widget.getElement()
                                                              .getStyle()
                                                              .getProperty("display");
                            dragging.widget.getElement().getStyle()
                                           .setProperty("display", "none");
                        }

                        p.revert = revert;
                        p.setPopupPosition(ix, iy);
                        p.show();
                        p.onMouseDown(p.p, x, y);
                    }
                };

            t.schedule(150);
        }

        @Override
        public void onMouseMove(Widget sender, int x, int y) {
        }

        @Override
        public void onMouseUp(Widget sender, int x, int y) {
            cancelled = true;
            if(p != null){
                p.onMouseUp(p.p, x, y);
            }
            
        }
    }

    private class Draggable {
        public ArrayList<DragListener> listeners = new ArrayList<DragListener>();
        public DragSupportListener listener;
        public DraggedPlaceholderStrategy strat;
        public String display;
        public Widget widget;
        public int lowerX;
        public int lowerY;
        public int upperX;
        public int upperY;
    }

    private class DraggablePopupPanel extends PopupPanel
        implements MouseListener {
        FocusPanel p = new FocusPanel();
        boolean isDragging = true;
        boolean revert;
        int dragStartX;
        int dragStartY;
        private Draggable d;
        private Widget lastHover = null;

        DraggablePopupPanel(Draggable d) {
            this.setWidget(p);
            this.d = d;
            p.addMouseListener(this);
            this.setStyleName("gwittir-Draggable");

            DOM.sinkEvents(getElement(),
                DOM.getEventsSunk(getElement()) | Event.MOUSEEVENTS);
            DOM.addEventPreview(this);
        }

        @Override
        public boolean onEventPreview(Event event) {
            if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
                if (DOM.isOrHasChild(p.getElement(), DOM.eventGetTarget(event))) {
                    DOM.eventPreventDefault(event);
                }
            }

            return true;
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
                DragListener.DragPoint pt = new DragListener.DragPoint(absX - dragStartX,
                        absY - dragStartY);

                for (DragListener l : d.listeners) {
                    pt = l.onDrag(pt);
                }

                setPopupPosition(pt.getX(), pt.getY());

                Widget newHover = calc(sender, x, y, false);

                if ((lastHover != null) && (lastHover != newHover) &&
                        dropListeners.containsKey(lastHover)) {
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
            int centerY = top +
                (int) ((float) this.getOffsetHeight() / (float) 2);
            int centerX = left +
                (int) ((float) this.getOffsetWidth() / (float) 2);

            Widget hit = null;
            for (int i = dropTargets == null ? -1 : dropTargets.size() -1 ; i > -1; i-- ) {
                Widget w = dropTargets.get(i);

                if ((centerY >= w.getAbsoluteTop()) &&
                        (centerY <= (w.getAbsoluteTop() + w.getOffsetHeight())) &&
                        (centerX >= w.getAbsoluteLeft()) &&
                        (centerX <= (w.getAbsoluteLeft() + w.getOffsetWidth()))) {
                    List<DropListener> listeners = dropListeners.get(w);

                    for (int j = 0;
                            (listeners != null) && (j < listeners.size());
                            j++) {
                        DropListener l = (DropListener) listeners.get(j);

                        if ((drop && l.canDrop(dragging.widget)) ||
                                (!drop && l.onHover(sender))) {
                            if (drop) {
                                //Make sure we revert the widget before the drop event in case the user
                                // wants to do something with it.
                                if (revert) {
                                    DOM.removeChild(DOM.getParent(
                                            dragging.widget.getElement()),
                                        placeholder);
                                    dragging.widget.getElement().getStyle()
                                                   .setProperty("display",
                                        dragging.display);
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
                DOM.removeChild(DOM.getParent(dragging.widget.getElement()),
                    placeholder);
                dragging.widget.getElement().getStyle()
                               .setProperty("display", dragging.display);
                hide();
                placeholder = null;
            }

            return hit;
        }
    }
}
