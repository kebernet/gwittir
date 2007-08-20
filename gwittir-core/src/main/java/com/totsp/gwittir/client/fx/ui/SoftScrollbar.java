/*
 * SoftVerticalScroll.java
 *
 * Created on August 16, 2007, 12:21 PM
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
package com.totsp.gwittir.client.fx.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollListener;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @author cooper
 */
public class SoftScrollbar extends Composite {
    FocusPanel barTarget = new FocusPanel();
    FocusPanel higherTarget = new FocusPanel();
    FocusPanel lowerTarget = new FocusPanel();
    ScrollListener scrollListener = new ScrollListener() {
            public void onScroll(Widget widget, int scrollLeft, int scrollTop) {
                refresh();
            }
        };

    SimplePanel bar = new SimplePanel();
    SimplePanel base;
    SimplePanel higher = new SimplePanel();
    SimplePanel lower = new SimplePanel();
    SoftScrollArea target;
    private MouseListener barListener = new MouseListenerAdapter() {
            boolean inDrag = false;
            int start;

            public void onMouseLeave(Widget sender) {
                inDrag = false;
            }

            public void onMouseUp(Widget sender, int x, int y) {
                inDrag = false;
            }

            public void onMouseDown(Widget sender, int x, int y) {
                inDrag = true;
                start = y;
            }

            public void onMouseMove(Widget sender, int x, int y) {
                if(inDrag) {
                    float newPercent = (float) (
                            (y - start) + lower.getOffsetHeight()
                        ) / (float) base.getOffsetHeight();
                    int newPosition = Math.round((
                                target.getOffsetHeight()
                                + target.getMaxScrollPosition()
                            ) * newPercent) + x;
                    target.setScrollPosition(newPosition);
                }
            }
        };

    private MouseListener higherListener = new MouseListenerAdapter() {
            public void onMouseDown(Widget sender, int x, int y) {
                float newPercent = (float) (
                        y + lower.getOffsetHeight() + bar.getOffsetHeight()
                    ) / (float) base.getOffsetHeight();
                int newPosition = Math.round((
                            target.getOffsetHeight()
                            + target.getMaxScrollPosition()
                        ) * newPercent) - (target.getOffsetHeight() / 2);

                target.setScrollPosition(newPosition);
            }
        };

    private MouseListener lowerListener = new MouseListenerAdapter() {
            public void onMouseDown(Widget sender, int x, int y) {
                float newPercent = (float) y / (float) base.getOffsetHeight();
                int newPosition = Math.round((
                            target.getOffsetHeight()
                            + target.getMaxScrollPosition()
                        ) * newPercent) - (target.getOffsetHeight() / 2);
                target.setScrollPosition(newPosition);
            }
        };

    private WindowResizeListener windowListener = new WindowResizeListener() {
            public void onWindowResized(int width, int height) {
                refresh();
            }
        };

    /** Creates a new instance of SoftVerticalScroll */
    public SoftScrollbar(SoftScrollArea target) {
        this.target = target;
        this.base = new SimplePanel();

        Panel vertical = new VerticalPanel();
        this.base.setWidget(vertical);
        super.initWidget(this.base);
        this.higherTarget.setWidget(this.higher);
        this.barTarget.setWidget(this.bar);
        this.lowerTarget.setWidget(this.lower);

        vertical.add(lowerTarget);
        vertical.add(barTarget);
        vertical.add(higherTarget);
        this.setStyleName("gwittir-SoftScrollbar");
        this.bar.setStyleName("bar");
        this.lower.setStyleName("lower");
        this.higher.setStyleName("higher");
        DOM.setStyleAttribute(this.lower.getElement(), "overflow", "hidden");
        DOM.setStyleAttribute(this.bar.getElement(), "overflow", "hidden");
        DOM.setStyleAttribute(this.higher.getElement(), "overflow", "hidden");
        DOM.setStyleAttribute(this.getElement(), "overflow", "hidden");
    }

    protected MouseListener getBarListener() {
        return barListener;
    }

    protected MouseListener getHigherListener() {
        return this.higherListener;
    }

    protected MouseListener getLowerListener() {
        return this.lowerListener;
    }

    protected void onAttach() {
        super.onAttach();
        target.addScrollListener(this.scrollListener);
        this.lowerTarget.addMouseListener(this.getLowerListener());
        this.higherTarget.addMouseListener(this.getHigherListener());
        this.barTarget.addMouseListener(this.getBarListener());
        Window.addWindowResizeListener(this.windowListener);

        Timer t = new Timer() {
                public void run() {
                    refresh();
                }
            };

        t.schedule(10);
    }

    protected void onDetach() {
        super.onDetach();
        Window.removeWindowResizeListener(this.windowListener);
        target.removeScrollListener(this.scrollListener);
        this.lowerTarget.removeMouseListener(this.getLowerListener());
        this.higherTarget.removeMouseListener(this.getHigherListener());
        this.barTarget.removeMouseListener(this.getBarListener());
    }

    public void refresh() {
        int currentHeight = this.getOffsetHeight();

        float pageSize = (float) target.getOffsetHeight() / (float) (
                target.getMaxScrollPosition() + target.getOffsetHeight()
            );

        //GWT.log("Page sisze " + pageSize, null);
        float scrollPercentage = (float) target.getScrollPosition() / (
                target.getMaxScrollPosition() + 1
            );

        //GWT.log("Scroll Percentage " + scrollPercentage, null);
        int lowerHeight = Math.round((
                    currentHeight
                    - Math.round((float) currentHeight * pageSize)
                ) * scrollPercentage);
        //GWT.log("Lower height " + lowerHeight, null);
        this.lower.setHeight(lowerHeight + "px");

        int barHeight = Math.round(currentHeight * pageSize);
        //GWT.log("Bar height " + barHeight, null);
        this.bar.setHeight(barHeight + "px");

        int higherHeight = currentHeight - lowerHeight - barHeight;
        this.higher.setHeight(higherHeight + "px");
        this.lower.setWidth(this.getOffsetWidth() + "px");
        this.bar.setWidth(this.getOffsetWidth() + "px");
        this.higher.setWidth(this.getOffsetWidth() + "px");
    }

    public void setBarWidget(Widget w) {
        this.bar.setWidget(w);
        DOM.setStyleAttribute(this.bar.getElement(), "overflow", "hidden");
    }

    public void setHigherWidget(Widget w) {
        this.higher.setWidget(w);
        DOM.setStyleAttribute(this.higher.getElement(), "overflow", "hidden");
    }

    public void setLowerWidget(Widget w) {
        this.lower.setWidget(w);
        DOM.setStyleAttribute(this.lower.getElement(), "overflow", "hidden");
    }
}
