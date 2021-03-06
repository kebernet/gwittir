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
package com.totsp.gwittir.mvc.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.fx.AnimationFinishedCallback;
import com.totsp.gwittir.fx.MutationStrategy;
import com.totsp.gwittir.fx.PositionWrapper;
import com.totsp.gwittir.fx.PropertyAnimator;
import com.totsp.gwittir.fx.rebind.Dimensions;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class SlideTransitionSimplePanel extends AbstractBoundWidget
    implements HasWidgets, com.totsp.gwittir.fx.ui.HasWidget {
    private static final Logger LOG = Logger.getLogger(
            "com.totsp.gwittir.client.ui.fx");
    public static final String HORIZONTAL = "left";
    public static final String VERTICAL = "top";
    private FlowPanel base = new FlowPanel();
    private MutationStrategy mutationStrategy = MutationStrategy.UNITS_SINOIDAL;
    private PositionWrapper currentWidget;
    private PositionWrapper toWidget;
    private String direction;
    private int duration = 1000;
    private PropertyAnimator currentAnimator;

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
        if (currentWidget != null) {
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
        if (currentWidget.getUIObject() instanceof BoundWidget) {
            return ((BoundWidget) currentWidget.getUIObject()).getModel();
        } else {
            return null;
        }
    }

    public Widget getWidget() {
        if (this.currentWidget == null) {
            return null;
        }

        return (Widget) this.currentWidget.getUIObject();
    }

    public Iterator iterator() {
        return base.iterator();
    }

    public boolean remove(Widget w) {
        if (currentWidget != null) {
            PropertyAnimator old = new PropertyAnimator(currentWidget,
                    getDirection(), "0%", "-100%",
                    MutationStrategy.UNITS_LINEAR, 1000,
                    new AnimationFinishedCallback() {
                        public void onFailure(PropertyAnimator animator,
                            Exception e) {
                            Logger.getAnonymousLogger().log( Level.INFO, "Exception animating transition", e);
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
        if (currentWidget.getUIObject() instanceof BoundWidget) {
            ((BoundWidget) currentWidget.getUIObject()).setModel(value);
        }
    }

    public void setWidget(final Widget w) {
        LOG.log(Level.FINE, "Set widget: "+ w );
        if ((this.currentWidget != null) &&
                ( (this.currentAnimator == null && this.currentWidget.getUIObject() == w) ||
                  (this.currentAnimator != null && this.toWidget.getUIObject() == w )) ) {
            LOG.log(Level.FINE, "Attempted to set the same object twice." );
            return;
        }

        if (this.currentAnimator != null) {
            LOG.log(Level.FINE, "Cancelling animation.");
            this.currentAnimator.cancel();
            this.clear();
            LOG.log(Level.FINE, "Setting new widget:" + this.toWidget.getUIObject());
            this.add((Widget) this.toWidget.getUIObject());
        }

        //GWT.log( "Setting widget "+ w.toString(), null );
        final PositionWrapper nextWidget = new PositionWrapper(w);
        nextWidget.setPosition("relative");

        String high = "100%";
        String low = "0%";

        if ((this.currentWidget != null) &&
                this.getDirection().equals(SlideTransitionSimplePanel.VERTICAL)) {
            high = "0px";
            low = "-" + currentWidget.getUIObject().getOffsetHeight() + "px";
        }

        this.currentAnimator = new PropertyAnimator(nextWidget,
                this.getDirection(), high, low, this.getMutationStrategy(),
                1000,
                new AnimationFinishedCallback() {
                    public void onFailure(PropertyAnimator animator, Exception e) {
                        currentAnimator = null;
                        clear();
                        add(w);
                        currentWidget = nextWidget;
                        toWidget = null;
                    }

                    public void onFinish(PropertyAnimator animator) {
                        currentWidget = nextWidget;
                        toWidget = null;
                        nextWidget.setTop("0px");
                        currentAnimator = null;
                    }
                });

        if (currentWidget != null) {
            final Widget oldWidget = (Widget) currentWidget.getUIObject();
            int totalOffset = this.currentWidget.getUIObject().getOffsetHeight() +
                Dimensions.INSTANCE.getTotalVerticalMargin(this.currentWidget.getUIObject()
                                                                             .getElement());
            Logger.getAnonymousLogger().log( Level.FINE, "Total offset " + totalOffset);

            if (this.getDirection().equals(SlideTransitionSimplePanel.HORIZONTAL)) {
                nextWidget.setTop("-" + totalOffset + "px");
            }

            PropertyAnimator old = new PropertyAnimator(currentWidget,
                    this.getDirection(), "0%", "-100%",
                    this.getMutationStrategy(), 1000,
                    new AnimationFinishedCallback() {
                        public void onFailure(PropertyAnimator animator,
                            Exception e) {
                            Logger.getAnonymousLogger().log( Level.INFO, "Exception animating transition", e);
                        }

                        public void onFinish(PropertyAnimator animator) {
                            base.remove(oldWidget);

                            if (getDirection()
                                        .equals(SlideTransitionSimplePanel.HORIZONTAL)) {
                                nextWidget.setTop("0px");
                            }
                        }
                    });
            old.start();
        }

        this.toWidget = nextWidget;
        this.currentAnimator.start();
        this.base.add(w);

        if (this.getDirection().equals(SlideTransitionSimplePanel.HORIZONTAL)) {
            nextWidget.setLeft("101%");
        }
    }
}
