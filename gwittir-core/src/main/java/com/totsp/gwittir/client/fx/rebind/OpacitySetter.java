/*
 * OpacitySetter.java
 *
 * Created on August 3, 2007, 8:18 PM
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
package com.totsp.gwittir.client.fx.rebind;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;


/**
 * This is a simple class for setting the opacity on a UIObject.
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class OpacitySetter {
    /** Creates a new instance of OpacitySetter */
    public OpacitySetter() {
    }

    /**
     * Sets the opactiy.
     * @param o The UIObject to set.
     * @param opacity The new opacity between 0.0 and 1.0
     */
    public void setOpacity(UIObject o, Double opacity) {
        if (opacity != null) {
            DOM.setStyleAttribute(o.getElement(), "opacity", opacity.toString());
        } else {
            DOM.setStyleAttribute(o.getElement(), "opacity", "inherit");
        }
    }

    /**
     * Gets the current opacity value.
     * @param o UIObject to inspect.
     * @return The current opacity between 0.0 and 1.0
     */
    public Double getOpacity(UIObject o) {
        String str = DOM.getStyleAttribute(o.getElement(), "opacity");

        if ((str == null) || (str.length() == 0)) {
            return new Double(1.0);
        } else {
            return Double.valueOf(str);
        }
    }
}
