/*
 * ReflectionIE6.java
 *
 * Created on August 15, 2007, 11:36 AM
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
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author cooper
 */
public class ReflectionIE6 extends Reflection {
    
    Element element = DOM.createElement("image");
    
    /** Creates a new instance of ReflectionIE6 */
    public ReflectionIE6() {
        super();
        super.setElement( element );
    }
    
    public void paint(Image base, int baseWidth, int baseHeight, double height, double opacity){
        long reflectHeight = Math.round(base.getHeight() * height);
        DOM.setElementAttribute( element, "src", base.getUrl() );
        DOM.setStyleAttribute( element, "width", base.getWidth()+"px" );
        DOM.setStyleAttribute( element, "height", reflectHeight+"px" );
        DOM.setStyleAttribute( element, "marginBottom", "-"+reflectHeight+"px");
        DOM.setStyleAttribute( element, "filter", "flipv progid:DXImageTransform.Microsoft.Alpha(opacity="+(opacity*100)+", style=1, finishOpacity=0, startx=0, starty=0, finishx=0, finishy="+(height*100)+")" );
        DOM.setStyleAttribute( base.getElement(), "cssText", "vertical-align: bottom");
        
    }
    
}
