/*
 * SoftAnimatedHorizontalScrollbar.java
 *
 * Created on August 16, 2007, 5:58 PM
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
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @author cooper
 */
public class SoftAnimatedHorizontalScrollbar extends SoftHorizontalScrollbar {
    private MouseListener higherListener = new MouseListenerAdapter() {
            public void onMouseDown(Widget sender, int x, int y) {
                float newPercent = (float) (
                        x + lower.getOffsetWidth() + bar.getOffsetWidth()
                    ) / (float) base.getOffsetWidth();
                int newPosition = Math.round((
                            target.getOffsetWidth()
                            + target.getMaxHorizontalScrollPosition()
                        ) * newPercent) - (target.getOffsetWidth() / 2);

                target.animateToHorizontalScrollPosition(newPosition);
            }
        };

    private MouseListener lowerListener = new MouseListenerAdapter() {
            public void onMouseDown(Widget sender, int x, int y) {
                float newPercent = (float) x / (float) base.getOffsetWidth();
                int newPosition = Math.round((
                            target.getOffsetWidth()
                            + target.getMaxHorizontalScrollPosition()
                        ) * newPercent) - (target.getOffsetWidth() / 2);
                GWT.log("New Position: " + newPosition, null);
                target.animateToHorizontalScrollPosition(newPosition);
            }
        };

    /** Creates a new instance of SoftAnimatedHorizontalScrollbar */
    public SoftAnimatedHorizontalScrollbar(SoftScrollArea target) {
        super(target);
    }

    protected MouseListener getHigherListener() {
        return this.higherListener;
    }

    protected MouseListener getLowerListener() {
        return this.lowerListener;
    }
}
