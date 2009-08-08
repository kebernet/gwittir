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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollListener;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class SoftHorizontalScrollbar extends Composite {
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
                start = x;
            }

            public void onMouseMove(Widget sender, int x, int y) {
                if(inDrag) {
                    float newPercent = (float) (
                            (x - start) + lower.getOffsetWidth()
                        ) / (float) base.getOffsetWidth();
                    Logger.getAnonymousLogger().log( Level.SPAM, x + " " + newPercent, null);

                    int newPosition = Math.round((
                                target.getOffsetWidth()
                                + target.getMaxHorizontalScrollPosition()
                            ) * newPercent) + x;
                    target.setHorizontalScrollPosition(newPosition);
                }
            }
        };

    private MouseListener higherListener = new MouseListenerAdapter() {
            public void onMouseDown(Widget sender, int x, int y) {
                float newPercent = (float) (
                        x + lower.getOffsetWidth() + bar.getOffsetWidth()
                    ) / (float) base.getOffsetWidth();
                int newPosition = Math.round((
                            target.getOffsetWidth()
                            + target.getMaxHorizontalScrollPosition()
                        ) * newPercent) - (target.getOffsetWidth() / 2);

                target.setHorizontalScrollPosition(newPosition);
            }
        };

    private MouseListener lowerListener = new MouseListenerAdapter() {
            public void onMouseDown(Widget sender, int x, int y) {
                float newPercent = (float) x / (float) base.getOffsetWidth();
                int newPosition = Math.round((
                            target.getOffsetWidth()
                            + target.getMaxHorizontalScrollPosition()
                        ) * newPercent) - (target.getOffsetWidth() / 2);
                Logger.getAnonymousLogger().log( Level.SPAM, "New Position: " + newPosition, null);
                target.setHorizontalScrollPosition(newPosition);
            }
        };

    private WindowResizeListener windowListener = new WindowResizeListener() {
            public void onWindowResized(int width, int height) {
                refresh();
            }
        };

    /** Creates a new instance of SoftVerticalScroll */
    public SoftHorizontalScrollbar(SoftScrollArea target) {
        this.target = target;
        this.base = new SimplePanel();

        Panel horizontal = new HorizontalPanel();
        this.base.setWidget(horizontal);
        super.initWidget(this.base);
        this.higherTarget.setWidget(this.higher);
        this.barTarget.setWidget(this.bar);
        this.lowerTarget.setWidget(this.lower);

        horizontal.add(lowerTarget);
        horizontal.add(barTarget);
        horizontal.add(higherTarget);
        this.setStyleName("gwittir-SoftHorizontalScrollbar");
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
        Window.addWindowResizeListener(this.windowListener);
        this.lowerTarget.addMouseListener(this.getLowerListener());
        this.higherTarget.addMouseListener(this.getHigherListener());
        this.barTarget.addMouseListener(this.getBarListener());

        Timer t = new Timer() {
                public void run() {
                    target.setScrollPosition(target.getScrollPosition());
                    refresh();
                }
            };

        t.schedule(10);
    }

    protected void onDetach() {
        super.onDetach();
        Window.removeWindowResizeListener(this.windowListener);
        this.lowerTarget.removeMouseListener(this.getLowerListener());
        this.higherTarget.removeMouseListener(this.getHigherListener());
        this.barTarget.removeMouseListener(this.getBarListener());
        target.removeScrollListener(this.scrollListener);
    }

    public void refresh() {
        int currentWidth = this.getOffsetWidth();

        float pageSize = (float) target.getOffsetWidth() / (float) (
                target.getMaxHorizontalScrollPosition()
                + target.getOffsetWidth()
            );

        //GWT.log("Page sisze " + pageSize, null);
        float scrollPercentage = (float) target.getHorizontalScrollPosition() / (
                target.getMaxHorizontalScrollPosition() + 1
            );

        //GWT.log("Scroll Percentage " + scrollPercentage, null);
        int lowerWidth = Math.round((
                    currentWidth - Math.round((float) currentWidth * pageSize)
                ) * scrollPercentage);

        //GWT.log("Lower width " + lowerWidth, null);
        if(lowerWidth == 0) {
            lowerWidth = 1;
        }

        this.lower.setWidth(lowerWidth + "px");

        int barWidth = Math.round(currentWidth * pageSize);
        //GWT.log("Bar width " + barWidth, null);
        if(barWidth >=0 ){
            this.bar.setWidth(barWidth + "px");
        }
        int higherWidth = currentWidth - lowerWidth - barWidth;
        if( higherWidth >= 0 ){
            this.higher.setWidth(higherWidth + "px");
        }
        this.lower.setHeight(this.getOffsetHeight() + "px");
        this.bar.setHeight(this.getOffsetHeight() + "px");
        this.higher.setHeight(this.getOffsetHeight() + "px");
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
