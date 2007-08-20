/*
 * SlidePanel.java
 *
 * Created on August 5, 2007, 10:44 AM
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.fx.AnimationFinishedCallback;
import com.totsp.gwittir.client.fx.MutationStrategy;
import com.totsp.gwittir.client.fx.PositionWrapper;
import com.totsp.gwittir.client.fx.PropertyAnimator;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.HasWidget;

import java.util.Iterator;


/**
 *
 * @author rcooper
 */
public class SlideTransitionSimplePanel extends AbstractBoundWidget
    implements HasWidgets, HasWidget {
    public static final String HORIZONTAL = "left";
    public static final String VERTICAL = "top";
    private FlowPanel base = new FlowPanel();
    private MutationStrategy mutationStrategy = MutationStrategy.UNITS_SINOIDAL;
    private PositionWrapper currentWidget;
    private String direction;
    private int duration = 1000;

    /** Creates a new instance of SlidePanel */
    public SlideTransitionSimplePanel() {
        DOM.setStyleAttribute(base.getElement(), "overflow", "hidden");
        this.setDirection(HORIZONTAL);
        super.initWidget(base);
        this.setStyleName("gwittir-SlidePanel");
    }

    public SlideTransitionSimplePanel(String direction) {
        this();
        this.setDirection(direction);
    }

    public void add(Widget w) {
        if(currentWidget != null) {
            throw new RuntimeException("You can only add one widget at a time.");
        }

        setWidget(w);
    }

    public void clear() {
        base.clear();
        this.currentWidget = null;
    }

    public String getDirection() {
        return direction;
    }

    public int getDuration() {
        return duration;
    }

    public MutationStrategy getMutationStrategy() {
        return mutationStrategy;
    }

    public Object getValue() {
        if(currentWidget.getUIObject() instanceof BoundWidget) {
            return ((BoundWidget) currentWidget.getUIObject()).getModel();
        } else {
            return null;
        }
    }

    public Widget getWidget() {
        if(this.currentWidget == null) {
            return null;
        }

        return (Widget) this.currentWidget.getUIObject();
    }

    public Iterator iterator() {
        return base.iterator();
    }

    public boolean remove(Widget w) {
        if(currentWidget != null) {
            PropertyAnimator old = new PropertyAnimator(currentWidget,
                    getDirection(), "0%", "-100%",
                    MutationStrategy.UNITS_LINEAR, 1000,
                    new AnimationFinishedCallback() {
                        public void onFailure(PropertyAnimator animator,
                            Exception e) {
                            GWT.log("Exception animating transition", e);
                        }

                        public void onFinish(PropertyAnimator animator) {
                            currentWidget = null;
                        }
                    });
            old.start();

            return true;
        }

        return false;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setMutationStrategy(MutationStrategy mutationStrategy) {
        this.mutationStrategy = mutationStrategy;
    }

    public void setValue(Object value) {
        if(currentWidget.getUIObject() instanceof BoundWidget) {
            ((BoundWidget) currentWidget.getUIObject()).setModel(value);
        }
    }

    public void setWidget(Widget w) {
        //GWT.log( "Setting widget "+ w.toString(), null );
        final PositionWrapper nextWidget = new PositionWrapper(w);
        nextWidget.setPosition("relative");

        String high = "100%";
        String low = "0%";

        if((this.currentWidget != null)
                && this.getDirection()
                           .equals(SlideTransitionSimplePanel.VERTICAL)) {
            high = "0px";
            low = "-" + currentWidget.getUIObject().getOffsetHeight() + "px";
        }

        PropertyAnimator a = new PropertyAnimator(nextWidget,
                this.getDirection(), high, low, this.getMutationStrategy(),
                1000,
                new AnimationFinishedCallback() {
                    public void onFailure(PropertyAnimator animator, Exception e) {
                        GWT.log("Exception animating transition", e);
                    }

                    public void onFinish(PropertyAnimator animator) {
                        //GWT.log( nextWidget.getLeft(), null);
                        currentWidget = nextWidget;
                        nextWidget.setTop("0px");
                    }
                });

        if(currentWidget != null) {
            final Widget oldWidget = (Widget) currentWidget.getUIObject();

            if(this.getDirection().equals(SlideTransitionSimplePanel.HORIZONTAL)) {
                nextWidget.setTop("-" + oldWidget.getOffsetHeight() + "px");
            }

            PropertyAnimator old = new PropertyAnimator(currentWidget,
                    this.getDirection(), "0%", "-100%",
                    this.getMutationStrategy(), 1000,
                    new AnimationFinishedCallback() {
                        public void onFailure(PropertyAnimator animator,
                            Exception e) {
                            GWT.log("Exception animating transition", e);
                        }

                        public void onFinish(PropertyAnimator animator) {
                            base.remove(oldWidget);

                            if(getDirection()
                                       .equals(SlideTransitionSimplePanel.HORIZONTAL)) {
                                nextWidget.setTop("0px");
                            }
                        }
                    });
            old.start();
        }

        a.start();
        this.base.add(w);

        if(this.getDirection().equals(SlideTransitionSimplePanel.HORIZONTAL)) {
            nextWidget.setLeft("101%");
        }
    }
}
