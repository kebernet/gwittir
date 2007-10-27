/*
 * ChangeMarkerListener.java
 *
 * Created on October 25, 2007, 2:28 PM
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

package com.totsp.gwittir.client.ui.util;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.ui.BoundWidget;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cooper
 */
public class ChangeMarkerListener implements PropertyChangeListener {
    private ChangeMarkedTypeFactory factory;
    private boolean valueHasChanged = false;
    private SimplePanel marker = new SimplePanel();
    
    /** Creates a new instance of ChangeMarkerListener */
    public ChangeMarkerListener(ChangeMarkedTypeFactory factory) {
        super();
        this.factory = factory;
        marker.setStyleName( "gwittir-ChangeMarker");
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        
        if( propertyChangeEvent.getSource() instanceof Widget ){
            Widget widget = (Widget) propertyChangeEvent.getSource();
            if(propertyChangeEvent.getPropertyName().equals("value") ){
                valueHasChanged = true;
                if( widget.isAttached() ){
                    showMarker( widget );
                }
                if(widget instanceof BoundWidget ){
                    BoundWidget bw = (BoundWidget) widget;
                    List widgets = (List) factory.widgetLookup.get( bw.getModel() );
                    if( widgets == null ){
                        widgets = new ArrayList();
                        factory.widgetLookup.put( bw.getModel(), widgets );
                    }
                    if( !widgets.contains( bw ) ){
                        widgets.add( bw );
                    } 
                        
                }
            } else if( propertyChangeEvent.getPropertyName().equals("attached") ){
                Boolean att = (Boolean) propertyChangeEvent.getNewValue();
                if( att.booleanValue() && valueHasChanged ){
                    showMarker(widget);
                } else {
                    hideMarker(widget);
                }
            }
        }
        
    }
    
    static void showMarker(Widget widget){
        widget.addStyleName("gwittir-ChangeMarker");
        /*OpacityWrapper o = new OpacityWrapper(marker);
        o.setOpacity(new Double(0.0));
        RootPanel.get().add( this.marker );
        PositionWrapper w = new PositionWrapper(marker);
        w.setPosition("absolute");
        w.setTop( widget.getAbsoluteTop()+"px" );
        w.setRight( widget.getAbsoluteLeft() + widget.getOffsetWidth() +"px");
        marker.setVisible(true);
        PropertyAnimator a = new PropertyAnimator(o, "opacity", new Double(1), MutationStrategy.DOUBLE_CUBIC, 250);
        a.start();*/
    }
    
    static void hideMarker(Widget widget){
        widget.removeStyleName("gwittir-ChangeMarker");
    }
    
    
    
    
}
