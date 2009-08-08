package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.fx.AnimationFinishedCallback;
import com.totsp.gwittir.client.fx.MutationStrategy;
import com.totsp.gwittir.client.fx.OpacityWrapper;
import com.totsp.gwittir.client.fx.PropertyAnimator;
import com.totsp.gwittir.client.fx.ui.ReflectedImage;
import com.totsp.gwittir.client.ui.BoundVerticalPanel;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;


public class AnimationExample extends BoundVerticalPanel<Object> {
    public AnimationExample() {
        super(new BoundWidgetTypeFactory(), null);

        final Button b = new Button("FOO!");

        final PropertyAnimator a = new PropertyAnimator(
                b, "height", "100px", "300px", MutationStrategy.UNITS_SINOIDAL, 3000,
                new AnimationFinishedCallback() {
                    public void onFinish(PropertyAnimator animator) {
                        final PropertyAnimator a = new PropertyAnimator(
                                b, "height", "300px", "100px", MutationStrategy.UNITS_SINOIDAL, 3000);
                        final PropertyAnimator a2 = new PropertyAnimator(
                                b, "width", "300px", "100px", MutationStrategy.UNITS_SINOIDAL, 3000);
                        a.start();
                        a2.start();
                    }

                    public void onFailure(PropertyAnimator animator, Exception e) {
                    }
                });
        final PropertyAnimator a2 = new PropertyAnimator(
                new OpacityWrapper(b), "opacity", new Double(0), new Double(1), MutationStrategy.DOUBLE_SINOIDAL, 3000);
        add(b);

        Button b2 = new Button(
                "Animate",
                new ClickListener() {
                    public void onClick(Widget w) {
                        a.start();
                        a2.start();
                    }
                });
        add(b2);

        final ReflectedImage ri = new ReflectedImage(GWT.getModuleBaseURL() + "gwtip.png", 163, 204, .2, .5);
        add(ri);

        Button resize = new Button(
                "resize",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        PropertyAnimator a = new PropertyAnimator(
                                ri, "width", new Integer(400), MutationStrategy.INTEGER_SINOIDAL);
                        PropertyAnimator b = new PropertyAnimator(
                                ri, "height", new Integer(400), MutationStrategy.INTEGER_SINOIDAL);
                        a.start();
                        b.start();
                    }
                });

        add(resize);
    }
}
