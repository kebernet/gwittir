/*
 * Image.java
 *
 * Created on November 11, 2007, 2:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesLoadEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;

/**
 *
 * @author rcooper
 */
public class Image extends AbstractBoundWidget 
        implements SourcesClickEvents, SourcesLoadEvents, 
        SourcesMouseEvents, SourcesMouseWheelEvents{
    private Object value;
    private com.google.gwt.user.client.ui.Image base =
            new com.google.gwt.user.client.ui.Image();
    
    
    
    /** Creates a new instance of Image */
    public Image() {
        super.initWidget( base );
        this.setStyleName("gwittir-Image");
        this.setRenderer( new ToStringRenderer() );
    }

    public Image(String value ){
        this();
        this.setValue( value );
    }
    
    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        Object old = this.value;
        this.value = value;
        this.base.setUrl( (String) this.getRenderer().render(value) );
        this.changes.firePropertyChange("value", old, value );
    }

    public void addClickListener(ClickListener listener) {
        this.base.addClickListener( listener );
    }

    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener( listener );
    }

    public void addLoadListener(LoadListener listener) {
        this.base.addLoadListener( listener );
    }

    public void removeLoadListener(LoadListener listener) {
        this.base.removeLoadListener( listener );
    }

    public void addMouseListener(MouseListener listener) {
        this.base.addMouseListener( listener );
    }

    public void removeMouseListener(MouseListener listener) {
        this.base.removeMouseListener( listener );
    }

    public void addMouseWheelListener(MouseWheelListener listener) {
        this.base.addMouseWheelListener( listener );
    }

    public void removeMouseWheelListener(MouseWheelListener listener) {
        this.base.removeMouseWheelListener( listener );
    }
    
    public void setVisibleRect(int left, int top,  int width, int height ){
        this.base.setVisibleRect( left, top, width, height );
    }
    
    public void setPixelSize( int width, int height ){
        this.base.setPixelSize( width, height );
    }
    
    public int getOriginLeft(){
        return this.base.getOriginLeft();
    }
    
    public int getOriginTop(){
        return this.base.getOriginTop();
    }
    
    

}
