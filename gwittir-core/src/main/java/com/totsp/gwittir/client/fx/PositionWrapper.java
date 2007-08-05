/*
 * PositionWrapper.java
 *
 * Created on August 5, 2007, 10:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.fx;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.totsp.gwittir.client.beans.Introspectable;

/**
 *
 * @author rcooper
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
}
