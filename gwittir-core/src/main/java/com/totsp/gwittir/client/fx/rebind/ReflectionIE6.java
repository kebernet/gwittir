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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class ReflectionIE6 extends Reflection {
    SimplePanel p = new SimplePanel();
    Image image = new Image();
    
    /** Creates a new instance of ReflectionIE6 */
    public ReflectionIE6() {
        super();
        p.add( image );
        super.setElement( p.getElement() );
    }
    
    public void paint(Image base, int baseWidth, int baseHeight, double height, double opacity){
        int reflectHeight = (int) Math.round(baseHeight * height);
        DOM.setStyleAttribute( p.getElement(), "overflow", "hidden");
        DOM.setStyleAttribute( p.getElement(), "margin", "0px");
        p.setPixelSize( baseWidth, reflectHeight );
        
        image.setUrl( base.getUrl() );
        image.setWidth( baseWidth+"px");
        //DOM.setStyleAttribute( image.getElement(), "marginBottom", "-"+(baseHeight-reflectHeight)+"px");
        DOM.setStyleAttribute( image.getElement(), "filter", "flipv progid:DXImageTransform.Microsoft.Alpha(opacity="+(opacity*100)+", style=1, finishOpacity=0, startx=0, starty=0, finishx=0, finishy="+(height*100)+")" );
        //DOM.setStyleAttribute( base.getElement(), "cssText", "vertical-align: bottom");
        GWT.log( image.toString(), null );
        
    }
    
}
