package com.totsp.gwittir.client.fx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DragAndDrop {
    private static final DragAndDrop instance = new DragAndDrop();
    private Draggable dragging;
    private Element placeholder;
    private HashMap draggables = new HashMap();
    private HashMap dropListeners = new HashMap();
    private List dropTargets;

    private DragAndDrop() {
    }

    public void addDropListener(Widget w, DropListener dl) {
        List listeners = (dropListeners.get(w) != null)
            ? (List) dropListeners.get(w) : new ArrayList();
        listeners.add(dl);
        dropListeners.put(w, listeners);
    }

    public static DragAndDrop getInstance() {
        return instance;
    }

    public void makeDraggable(SourcesMouseEvents w, boolean revert) {
        Draggable d = new Draggable();
        d.widget = (Widget) w;
        d.listener = new DragSupportListener((Widget) w, revert);
        w.addMouseListener(d.listener);
        draggables.put(w, d);
    }

    public void makeDroppable(Widget w) {
        dropTargets = (dropTargets == null) ? new ArrayList() : dropTargets;
        dropTargets.add(w);
    }

    public void removeDropListeners(SourcesMouseEvents w, DropListener dl) {
        List listeners = (dropListeners.get(w) != null)
            ? (List) dropListeners.get(w) : new ArrayList();
        listeners.remove(dl);
    }

    class DragSupportListener extends MouseListenerAdapter {
        private String startFlow;
        private String startZ;
        private boolean revert;
        private int offsetX;
        private int offsetY;
        private int startX;
        private int startY;

        public DragSupportListener(Widget w, boolean revert) {
            this.revert = revert;
            DOM.addEventPreview(new EventPreview() {
                    public boolean onEventPreview(Event event) {
                        switch(DOM.eventGetType(event)) {
                        case Event.ONMOUSEDOWN:
                            DOM.eventPreventDefault(event);
                        }

                        return true;
                    }
                });
        }

        public void onMouseDown(Widget sender, int x, int y) {
            super.onMouseDown(sender, x, y);

            if(dragging != null) {
                dragging.listener.onMouseUp(dragging.widget, 0, 0);
            }

            dragging = (Draggable) draggables.get(sender);
            startFlow = DOM.getStyleAttribute(dragging.widget.getElement(),
                    "position");
            startX = dragging.widget.getAbsoluteLeft();
            startY = dragging.widget.getAbsoluteTop();
            offsetX = x;
            offsetY = y;
            GWT.log("Start X = " + startX, null);
            GWT.log("Start Y = " + startY, null);
            GWT.log("X = " + x, null);
            GWT.log("Y = " + y, null);
            GWT.log("OffsetX = " + offsetX, null);
            GWT.log("Offset Y = " + offsetY, null);
            startZ = DOM.getStyleAttribute(dragging.widget.getElement(),
                    "z-index");
            DOM.setStyleAttribute(dragging.widget.getElement(), "position",
                "absolute");
            DOM.setStyleAttribute(dragging.widget.getElement(), "top",
                "" + startY);
            DOM.setStyleAttribute(dragging.widget.getElement(), "left",
                "" + startX);
            DOM.setStyleAttribute(dragging.widget.getElement(), "z-index",
                "" + 10000);
            placeholder = DOM.createDiv();
            DOM.setStyleAttribute(placeholder, "width",
                dragging.widget.getOffsetWidth() + "px");
            DOM.setStyleAttribute(placeholder, "height",
                dragging.widget.getOffsetHeight() + "px");
            DOM.setStyleAttribute(placeholder, "background", "#CCCCCC");

            int index = DOM.getChildIndex(DOM.getParent(
                        dragging.widget.getElement()),
                    dragging.widget.getElement());
            DOM.insertChild(DOM.getParent(dragging.widget.getElement()),
                placeholder, index);
        }

        public void onMouseMove(Widget sender, int x, int y) {
            super.onMouseMove(sender, x, y);

            if(dragging != null) {
                GWT.log("Drag X:" + x + " Y:" + y, null);
                GWT.log("Offset X:" + offsetX + " Y:" + offsetY, null);
                DOM.setStyleAttribute(dragging.widget.getElement(), "top",
                    Integer.toString(sender.getAbsoluteTop() - offsetY + y));
                DOM.setStyleAttribute(dragging.widget.getElement(), "left",
                    Integer.toString(sender.getAbsoluteLeft() - offsetX + x));
            }
        }

        public void onMouseUp(Widget sender, int x, int y) {
            super.onMouseUp(sender, x, y);
            GWT.log("Up", null);
            DOM.setStyleAttribute(dragging.widget.getElement(), "z-index",
                startZ);

            GWT.log("Looking for Drop", null);

            int top = dragging.widget.getAbsoluteTop();
            int left = dragging.widget.getAbsoluteLeft();
            GWT.log("Top:" + top + " Left:" + left + "OffsetTop" +
                dragging.widget.getOffsetHeight() + " OffsetLeft:" +
                dragging.widget.getOffsetWidth(), null);

            int centerY = top +
                (int) ((float) dragging.widget.getOffsetHeight() / (float) 2);
            int centerX = left +
                (int) ((float) dragging.widget.getOffsetWidth() / (float) 2);
            GWT.log("Center Top:" + centerY + " Center Left:" + centerX, null);

            for(int i = 0; (dropTargets != null) && (i < dropTargets.size());
                    i++) {
                Widget w = (Widget) dropTargets.get(i);
                GWT.log("Top Range: " + w.getAbsoluteTop() + " .. " +
                    (w.getAbsoluteTop() + w.getOffsetHeight()), null);
                GWT.log("Left Range: " + w.getAbsoluteLeft() + " .. " +
                    (w.getAbsoluteLeft() + w.getOffsetWidth()), null);

                if((centerY >= w.getAbsoluteTop()) &&
                        (centerY <= (w.getAbsoluteTop() + w.getOffsetHeight())) &&
                        (centerX >= w.getAbsoluteLeft()) &&
                        (centerX <= (w.getAbsoluteLeft() + w.getOffsetWidth()))) {
                    List listeners = (List) dropListeners.get(w);

                    for(int j = 0;
                            (listeners != null) && (j < listeners.size());
                            j++) {
                        DropListener l = (DropListener) listeners.get(j);

                        if(l.onDrop(dragging.widget)) {
                            break;
                        }
                    }
                }
            }

            if(revert) {
                DOM.setStyleAttribute(dragging.widget.getElement(), "position",
                    startFlow);
                DOM.setStyleAttribute(dragging.widget.getElement(), "top",
                    "" + startY);
                DOM.setStyleAttribute(dragging.widget.getElement(), "left",
                    "" + startX);
                DOM.removeChild(DOM.getParent(dragging.widget.getElement()),
                    placeholder);
                placeholder = null;
            }

            dragging = null;
        }
    }

    private class Draggable {
        public DragSupportListener listener;
        public Widget widget;
        public int upperX;
        public int upperY;
        public int lowerX;
        public int lowerY;
    }
}
