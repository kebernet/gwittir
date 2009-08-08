/*
 * OpacityWrapper.java
 *
 * Created on August 3, 2007, 5:15 PM
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
package com.totsp.gwittir.client.fx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.UIObject;
import com.totsp.gwittir.client.beans.Introspectable;
import com.totsp.gwittir.client.fx.rebind.OpacitySetter;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class OpacityWrapper implements Introspectable {
    
    public static final Double TRANSPARENT = new Double(0.0);
    public static final Double OPAQUE = new Double(1.0);
    
    private static final OpacitySetter SETTER = (OpacitySetter) GWT
        .create(OpacitySetter.class);
    UIObject o;

    /** Creates a new instance of OpacityWrapper */
    public OpacityWrapper(UIObject o) {
        this.o = o;
    }

    public Double getOpacity() {
        return SETTER.getOpacity(this.o);
    }

    public void setOpacity(Double opacity) {
        SETTER.setOpacity(this.o, opacity);
    }
}
