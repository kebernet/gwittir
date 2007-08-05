/*
 * ExampleEntryPoint.java
 *
 * Created on August 3, 2007, 3:51 PM
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
package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.fx.AnimationFinishedCallback;
import com.totsp.gwittir.client.fx.MutationStrategy;
import com.totsp.gwittir.client.fx.OpacityWrapper;
import com.totsp.gwittir.client.fx.PropertyAnimator;
import com.totsp.gwittir.client.ui.Button;


/**
 *
 * @author cooper
 */
public class ExampleEntryPoint implements EntryPoint {
    boolean big = false;

    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }

    public void onModuleLoad() {
        final Button b = new Button("FOO!");

        final PropertyAnimator a = new PropertyAnimator(b, "height", "100px",
                "300px", MutationStrategy.UNITS_SINOIDAL, 3000,
                new AnimationFinishedCallback() {
                    public void onFinish(PropertyAnimator animator) {
                        final PropertyAnimator a = new PropertyAnimator(b,
                                "height", "300px", "100px",
                                MutationStrategy.UNITS_SINOIDAL, 3000);
                        final PropertyAnimator a2 = new PropertyAnimator(b,
                                "width", "300px", "100px",
                                MutationStrategy.UNITS_SINOIDAL, 3000);
                        a.start();
                        a2.start();
                    }

                    public void onFailure(PropertyAnimator animator, Exception e) {
                    }
                });
        final PropertyAnimator a2 = new PropertyAnimator(new OpacityWrapper(b),
                "opacity", new Double(0), new Double(1),
                MutationStrategy.DOUBLE_SINOIDAL, 3000);
        RootPanel.get().add(b);

        Button b2 = new Button("Animate",
                new ClickListener() {
                    public void onClick(Widget w) {
                        a.start();
                        a2.start();
                    }
                });
        RootPanel.get().add(b2);
    }
}
