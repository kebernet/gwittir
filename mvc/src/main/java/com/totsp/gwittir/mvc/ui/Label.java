/*
 * Label.java
 *
 * Created on July 24, 2007, 5:35 PM
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

package com.totsp.gwittir.mvc.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class Label extends AbstractBoundWidget<String> {
    private com.google.gwt.user.client.ui.Label base;
    
    /** Creates a new instance of Label */
    public Label() {
        this.init( null );
    }
    public Label(String text){
        this.init( text );
    }
    
    private void init(String text){
        base =  text == null ? new com.google.gwt.user.client.ui.Label() : new com.google.gwt.user.client.ui.Label(text);
        super.initWidget( base );
    }
    
    public void addMouseWheelListener(MouseWheelListener listener) {
    }
    
    public void removeMouseWheelListener(MouseWheelListener listener) {
    }
    
    public void setWordWrap(boolean wrap) {
        this.base.setWordWrap(wrap);
    }
    
    @Override
    public void setVisible(boolean visible) {
        this.base.setVisible(visible);
    }
    
    public void addMouseListener(MouseListener listener) {
        this.base.addMouseListener(listener);
    }
    
    public void removeMouseListener(MouseListener listener) {
        this.base.removeMouseListener(listener);
    }
    
    @Override
    public void setWidth(String width) {
        this.base.setWidth(width);
    }
    
    @Override
    public void addStyleName(String style) {
        this.base.addStyleName(style);
    }
    
    @Override
    public void removeStyleName(String style) {
        this.base.removeStyleName(style);
    }
    
    public void setHeight(String height) {
        this.base.setHeight(height);
    }
    
    @Override
    public void setStyleName(String style) {
        this.base.setStyleName(style);
    }
    
    public void setText(String text) {
        this.base.setText(text);
    }
    
    @Override
    public void setTitle(String title) {
        this.base.setTitle(title);
    }
    
    public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
        this.base.setHorizontalAlignment(align);
    }
    
    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }
    
    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }
    
    @Override
    public void unsinkEvents(int eventBitsToRemove) {
        this.base.unsinkEvents(eventBitsToRemove);
    }
    
    @Override
    public void sinkEvents(int eventBitsToAdd) {
        this.base.sinkEvents(eventBitsToAdd);
    }
    
    @Override
    public boolean isVisible() {
        boolean retValue;
        
        retValue = this.base.isVisible();
        return retValue;
    }
    
    public boolean getWordWrap() {
        boolean retValue;
        
        retValue = this.base.getWordWrap();
        return retValue;
    }
    
    @Override
    public String getTitle() {
        String retValue;
        
        retValue = this.base.getTitle();
        return retValue;
    }
    
    public String getText() {
        String retValue;
        
        retValue = this.base.getText();
        return retValue;
    }
    
    @Override
    public int getOffsetWidth() {
        int retValue;
        
        retValue = this.base.getOffsetWidth();
        return retValue;
    }
    
    @Override
    public int getOffsetHeight() {
        int retValue;
        
        retValue = this.base.getOffsetHeight();
        return retValue;
    }
    
    public HorizontalAlignmentConstant getHorizontalAlignment() {
        HorizontalAlignmentConstant retValue;
        
        retValue = this.base.getHorizontalAlignment();
        return retValue;
    }
    
    @Override
    public int getAbsoluteTop() {
        int retValue;
        
        retValue = this.base.getAbsoluteTop();
        return retValue;
    }
    
    @Override
    public int getAbsoluteLeft() {
        int retValue;
        
        retValue = this.base.getAbsoluteLeft();
        return retValue;
    }
    
    @Override
    public String getStyleName() {
        String retValue;
        
        retValue = this.base.getStyleName();
        return retValue;
    }
    
    @Override
    public void setPixelSize(int width, int height) {
        this.base.setPixelSize(width, height);
    }
    
    @Override
    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }

     public void setValue(String value) {
        //("Setting value "+ value, null );
        Object old = this.getValue();
        this.setText( value );
        if( this.getValue() != old && this.getValue() != null && this.getValue().equals( old ) ){
            this.changes.firePropertyChange("value", old, this.getValue());
        }
        
    }

    public String getValue() {
        return this.base.getText().length() == 0 ? null : this.base.getText();
    }

    public void addChangeListener(ClickListener clickListener) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
