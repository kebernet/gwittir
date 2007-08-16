/*
 * SoftScrollPanel.java
 *
 * Created on August 16, 2007, 9:11 AM
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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.beans.Introspectable;
import com.totsp.gwittir.client.fx.MutationStrategy;
import com.totsp.gwittir.client.fx.PositionWrapper;
import com.totsp.gwittir.client.fx.PropertyAnimator;
import com.totsp.gwittir.client.ui.*;
import com.totsp.gwittir.client.util.UnitsParser;

/**
 *
 * @author cooper
 */
public class SoftScrollPanel extends Composite implements HasWidget, Introspectable {
    
    private SimplePanel base = new SimplePanel();
    private Widget widget;
    private PositionWrapper position;
    
    /** Creates a new instance of SoftScrollPanel */
    public SoftScrollPanel() {
        DOM.setStyleAttribute( base.getElement(), "overflow", "hidden");
        super.initWidget( base );
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
        this.position = new PositionWrapper( widget );
        this.position.setPosition("relative");
        this.position.setTop("0px");
        this.position.setLeft("0px");
        this.base.setWidget( widget );
    }
    
    public int getScrollPosition(){
        return -1 * UnitsParser.parse( position.getTop() ).value;
    }
    
    public void setScrollPosition(int position){
        int max =widget.getOffsetHeight() - base.getOffsetHeight();
        if( position >  max){
            position = max;
        }
        if( position < 0 ){
            position = 0;
        }
        this.position.setTop( (-1 *position)+"px");
    }
    
    public void pageDown(){
        this.setScrollPosition( this.getScrollPosition() + this.base.getOffsetHeight() );
    }
    
    public void pageUp(){
        this.setScrollPosition( this.getScrollPosition() - this.base.getOffsetHeight() );
    }
    
    public int getHorizontalScrollPosition(){
        return -1 * UnitsParser.parse( position.getLeft() ).value;
    }
    
    public void setHorizontalScrollPosition( int position ){
        int max = widget.getOffsetWidth() - base.getOffsetWidth();
        if( position > max ){
            position = max;
        }
        if( position < 0 ){
            position = 0;
        }
        this.position.setLeft( (-1 *position)+"px");
    }
    
    public void pageLeft(){
        this.setHorizontalScrollPosition( this.getHorizontalScrollPosition() - this.base.getOffsetWidth() );
    }
    
    public void pageRight(){ 
        this.setHorizontalScrollPosition( this.getHorizontalScrollPosition() + this.base.getOffsetWidth() );
    }
    
    public void animateToScrollPosition( int newPosition, MutationStrategy integerStrategy, int duration ){
        PropertyAnimator a = new PropertyAnimator( this, "scrollPosition", new Integer(newPosition), integerStrategy, duration );
        a.start();
    }
    
    public void animateToScrollPosition( int newPosition ){
        this.animateToScrollPosition( newPosition, MutationStrategy.INTEGER_SINOIDAL, 750 );
    }
    
    public void animateToHorizontalScrollPosition( int newPosition, MutationStrategy integerStrategy, int duration ){
        PropertyAnimator a = new PropertyAnimator( this, "horizontalScrollPosition", new Integer(newPosition), integerStrategy, duration );
        a.start();
    }
    
    public void animateToHorizontalScrollPosition( int newPosition ){
        this.animateToHorizontalScrollPosition( newPosition, MutationStrategy.INTEGER_SINOIDAL, 750 );
    }
    
    
    public void pageLeftAnimated(){
        this.animateToHorizontalScrollPosition( this.getHorizontalScrollPosition() - this.base.getOffsetWidth() );
    }
    
    public void pageRightAnimated(){ 
        this.animateToHorizontalScrollPosition( this.getHorizontalScrollPosition() + this.base.getOffsetWidth() );
    }
    public void pageUpAnimated(){
        this.animateToScrollPosition( this.getScrollPosition() - this.base.getOffsetHeight() );
    }
    
    public void pageDownAnimated(){ 
        this.animateToScrollPosition( this.getScrollPosition() + this.base.getOffsetHeight() );
    }
}
