/*
 * PositionWrapper.java
 *
 * Created on August 5, 2007, 10:55 AM
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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;
import com.totsp.gwittir.client.beans.Introspectable;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class PositionWrapper implements Introspectable {
    
    private UIObject o;
    
    
    /** Creates a new instance of PositionWrapper */
    public PositionWrapper(UIObject o) {
        this.o = o;
    }
    
    public String getTop() {
        return DOM.getStyleAttribute( this.o.getElement(), "top");
    }
    
    public void setTop(String top) {
        DOM.setStyleAttribute( this.o.getElement(), "top", top);
    }
    
    public String getBottom() {
        return DOM.getStyleAttribute( this.o.getElement(), "bottom");
    }
    
    public void setBottom(String bottom) {
        DOM.setStyleAttribute( this.o.getElement(), "bottom", bottom);
    }
    
    public String getLeft() {
        return DOM.getStyleAttribute( this.o.getElement(), "left");
    }
    
    public void setLeft(String left) {
        DOM.setStyleAttribute( this.o.getElement(), "left", left);
    }
    
    public String getRight() {
        return DOM.getStyleAttribute( this.o.getElement(), "right");
    }
    
    public void setRight(String right) {
        DOM.setStyleAttribute( this.o.getElement(), "right", right);
    }
    
    public String getPosition() {
        return DOM.getStyleAttribute( this.o.getElement(), "position");
    }
    
    public void setPosition(String position) {
        DOM.setStyleAttribute( this.o.getElement(), "position", position);
    }
    
    public UIObject getUIObject(){
        return this.o;
    }
    
    public int getOffsetTop(){
        return this.getOffsetTop(o.getElement() );
    }
    
    private static native int getOffsetTop(Element e) /*-{ return e.offsetTop }-*/;
    
    public int getOffsetLeft(){
        return this.getOffsetLeft(o.getElement() );
    }
    
    private static native int getOffsetLeft(Element e) /*-{ return e.offsetLeft }-*/;
    
    
}
