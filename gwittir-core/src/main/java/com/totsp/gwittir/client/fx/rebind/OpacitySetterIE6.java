/*
 * OpacitySetter.java
 *
 * Created on August 3, 2007, 8:06 PM
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
 * This is an opactiy setter than works by parsing the alpha() block
 * on the MSIE "filter" CSS property.
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class OpacitySetterIE6 extends OpacitySetter {
    /**
     * Text of the alpha block.
     */
    protected static final String ALPHA = "alpha(";

    /**
     * Text of the opacity block.
     */
    protected static final String OPACITY = "opacity=";

    /**
     * A string containing number characters for comparison.
     */
    protected static final String NUMBERS = "1234567890.";
    private static final String FILTER_STYLE_NAME = "filter";

    /** Creates a new instance of OpacitySetter */
    public OpacitySetterIE6() {
    }

    /**
     * Gets the current opacity value.
     * @param o UIObject to inspect.
     * @return The current opacity between 0.0 and 1.0
     */
    public Double getOpacity(UIObject o) {
        String str = DOM.getStyleAttribute(o.getElement(),FILTER_STYLE_NAME);
        str = parseOrReplace(str, null);

        if((str == null) || (str.length() == 0)) {
            return new Double(1.0);
        } else {
            return new Double(Double.parseDouble(str) / 100D);
        }
    }

    /**
     * Gets the current opacity value.
     * @param opacity the new opacity value between 0.0 and 1.0
     * @param o UIObject to inspect.
     */
    public void setOpacity(UIObject o, Double opacity) {
        String filter = DOM.getStyleAttribute(o.getElement(),FILTER_STYLE_NAME);

        if(filter == null) {
            filter = "";
        }

        if(opacity != null) {
            filter = parseOrReplace(filter, "" + Math.round(opacity.doubleValue() * 100d));
            DOM.setStyleAttribute(o.getElement(), FILTER_STYLE_NAME,filter);
        } else {
            filter = parseOrReplace(filter, "100");
            DOM.setStyleAttribute(o.getElement(), FILTER_STYLE_NAME,filter);
        }
    }

    static String parseOrReplace(String filter, String replace) {
        int start = filter.indexOf(ALPHA);

        if(start == -1) {
            if(replace != null) {
                if(filter == null) {
                    return ALPHA + OPACITY + replace + ")";
                } else {
                    return filter + ALPHA + OPACITY + replace + ")";
                }
            }

            return null;
        }

        int alphaEnd = filter.indexOf(")", start + ALPHA.length());
        String alphaBlock = filter.substring(start + ALPHA.length(), alphaEnd);
        int opacityStart = alphaBlock.indexOf(OPACITY);

        if(opacityStart == -1) {
            if(replace != null) {
                return filter.substring(0, start + ALPHA.length()) + OPACITY + replace + "," + alphaBlock
                + filter.substring(alphaEnd, filter.length());
            } else {
                return null;
            }
        }

        opacityStart += (start + ALPHA.length());

        int opacityEnd = opacityStart + OPACITY.length() + 1;

        for(; NUMBERS.indexOf(filter.charAt(opacityEnd)) != -1; opacityEnd++) {
            // Empty loop.
        }

        if(replace == null) {
            return filter.substring(opacityStart + OPACITY.length(), opacityEnd);
        } else {
            return filter.substring(0, opacityStart + OPACITY.length()) + replace
            + filter.substring(opacityEnd, filter.length());
        }
    }
}
