/*
 * MouseMoveScrollArea.java
 *
 * Created on November 7, 2007, 8:20 PM
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.ScrollListener;
import com.google.gwt.user.client.ui.SourcesScrollEvents;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.beans.Introspectable;
import com.totsp.gwittir.client.ui.HasWidget;

/**
 *
 * @author cooper
 */
public class MouseMoveScrollArea extends Composite implements HasWidget,
    Introspectable, SourcesScrollEvents {
    FocusPanel fp = new FocusPanel();
    SoftScrollArea scroll = new SoftScrollArea();
    
    /** Creates a new instance of MouseMoveScrollArea */
    public MouseMoveScrollArea() {
        fp.add( this.scroll );
        super.initWidget( fp );
        fp.addMouseListener(new MouseListenerAdapter(){
           
            public void onMouseMove(Widget sender, int x, int y) {
                GWT.log( "MAX SCROLLL = "+scroll.getMaxHorizontalScrollPosition(), null);
                int baseX = scroll.getOffsetWidth();
                int baseY = scroll.getOffsetHeight();
                double percentX = (double) x / (double) baseX;
                double percentY = (double) y / (double) baseY;
                int currentX = (int) Math.round( (double) scroll.getMaxHorizontalScrollPosition() * percentX );
                int currentY = (int) Math.round( (double) scroll.getMaxScrollPosition() * percentY );
                scroll.setHorizontalScrollPosition( currentX );
                scroll.setScrollPosition( currentY );
            }
        });
        
    }

    public void addScrollListener(ScrollListener listener) {
        this.scroll.addScrollListener( listener );
    }

    public void removeScrollListener(ScrollListener listener) {
        this.scroll.removeScrollListener(listener);
    }
    
    public void setWidget(Widget widget){
        this.scroll.setWidget( widget );
    }
    
    public Widget getWidget(){
        return this.scroll.getWidget();
    }

    public void setHeight(String height) {
        this.fp.setHeight(height);
        this.scroll.setHeight(height);
    }

    public void setWidth(String width) {
        this.fp.setWidth(width);
        this.scroll.setWidth(width);
    }

    public void setPixelSize(int width, int height) {
        this.fp.setPixelSize( width, height );
        this.scroll.setPixelSize(width, height);
    }

    public void setSize(String width, String height) {
        this.fp.setSize(width, height);
        this.scroll.setSize(width, height);
    }
    
}
