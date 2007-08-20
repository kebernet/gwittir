/*
 * Reflection.java
 *
 * Created on August 15, 2007, 11:50 AM
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @author cooper
 */
public class Reflection extends Widget {
    private Canvas canvas = new Canvas();
    private Element element = canvas.getElement();
    private Image base;
    private double height;
    private double opacity;
    private int baseWidth;
    private int baseHeight;
    private SimplePanel panel = new SimplePanel();

    /** Creates a new instance of Reflection */
    public Reflection() {
        super();
        super.setElement(panel.getElement());
        panel.setWidget( canvas );
    }

    public void paint(Image base, int baseWidth, int baseHeight, double height, double opacity) {
        this.height = height;
        this.opacity = opacity;
        if( this.base != null ){
            paint();
        }
        this.base = base;
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;

        int reflectHeight = (int) Math.round(baseHeight * height);
        DOM.setStyleAttribute(element, "width", baseWidth + "px");
        DOM.setStyleAttribute(element, "height", reflectHeight + "px");
        DOM.setStyleAttribute(element, "marginBottom",
            "-" + reflectHeight + "px");
        DOM.setElementAttribute(element, "width", "" + baseWidth);
        DOM.setElementAttribute(element, "height", "" + reflectHeight);
        this.panel.setPixelSize(baseWidth, reflectHeight);
        paint();
    }

    private void paint() {
        int reflectHeight = (int) Math.round(baseHeight * height);
        Canvas.Context ctx = canvas.getContext();
        ctx.save();
        ctx.clearRect(0, 0, this.baseWidth, reflectHeight );
        ctx.save();
        ctx.translate(0, this.baseHeight -1 );
        ctx.scale(1, -1);
        ctx.drawImage(this.base, 0, 0, this.baseWidth, this.baseHeight );
        ctx.restore();
        ctx.setGlobalCompositeOperation("destination-out");
        Canvas.LinearGradient gradient = ctx.createLinearGradient(0,0, 0, reflectHeight );
        gradient.addColorStop(1, "rgba(255, 255, 255, 1.0)");
	gradient.addColorStop(0, "rgba(255, 255, 255, "+(1-this.opacity)+")");
        ctx.setFillStyle( gradient );
        ctx.fillRect(0,0, this.baseWidth, reflectHeight );
        ctx.restore();
    }
}
