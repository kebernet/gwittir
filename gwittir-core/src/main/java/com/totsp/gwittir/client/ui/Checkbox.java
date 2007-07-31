/*
 * Checkbox.java
 *
 * Created on July 15, 2007, 1:40 PM
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
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author cooper
 */
public class Checkbox extends AbstractBoundWidget implements HasEnabled, SourcesClickEvents, HasFocus {
    
    com.google.gwt.user.client.ui.CheckBox base;
    
    /** Creates a new instance of Checkbox */
    public Checkbox() {
        super();
        init(null);
    }
    
    public Checkbox(String label){
        super();
        init( label );
    }
    
    public Checkbox(String label, boolean value ){
        super();
        init(label);
        this.setChecked( value );
    }
    
    private void init(String label){
        this.base = new com.google.gwt.user.client.ui.CheckBox(label);
        this.setRenderer( new ToBooleanRenderer() );
        super.initWidget( this.base );
        this.base.addClickListener( new ClickListener(){
            public void onClick(Widget sender) {
                Boolean old = isChecked() ? Boolean.FALSE : Boolean.TRUE;
                changes.firePropertyChange( "value", old, getValue() );
            }
            
        });
    }

    public void addKeyboardListener(KeyboardListener listener) {
        this.base.addKeyboardListener(listener);
    }

    public void removeKeyboardListener(KeyboardListener listener) {
        this.base.removeKeyboardListener(listener);
    }

    public void setChecked(boolean checked) {
        this.base.setChecked(checked);
    }

    public void setEnabled(boolean enabled) {
        this.base.setEnabled(enabled);
    }

    public void setFocus(boolean focused) {
        this.base.setFocus(focused);
    }

    public void onBrowserEvent(Event event) {
        this.base.onBrowserEvent(event);
    }

    public void setAccessKey(char key) {
        this.base.setAccessKey(key);
    }

    public void addFocusListener(FocusListener listener) {
        this.base.addFocusListener(listener);
    }

    public void removeFocusListener(FocusListener listener) {
        this.base.removeFocusListener(listener);
    }

    public void setWidth(String width) {
        this.base.setWidth(width);
    }

    public void setTitle(String title) {
        this.base.setTitle(title);
    }

    public void setText(String text) {
        this.base.setText(text);
    }

    public void setStyleName(String style) {
        this.base.setStyleName(style);
    }

    public void setName(String name) {
        this.base.setName(name);
    }

    public void setHeight(String height) {
        this.base.setHeight(height);
    }

    public void addStyleName(String style) {
        this.base.addStyleName(style);
    }

    public void removeStyleName(String style) {
        this.base.removeStyleName(style);
    }

    public void setHTML(String html) {
        this.base.setHTML(html);
    }

    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }

    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }

    public void setTabIndex(int index) {
        this.base.setTabIndex(index);
    }

    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }

    public void setPixelSize(int width, int height) {
        this.base.setPixelSize(width, height);
    }

    public boolean isChecked() {
        boolean retValue;
        
        retValue = this.base.isChecked();
        return retValue;
    }

    public boolean isAttached() {
        boolean retValue;
        
        retValue = this.base.isAttached();
        return retValue;
    }

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

    public int getTabIndex() {
        int retValue;
        
        retValue = this.base.getTabIndex();
        return retValue;
    }

    public String getStyleName() {
        String retValue;
        
        retValue = this.base.getStyleName();
        return retValue;
    }

    public String getName() {
        String retValue;
        
        retValue = this.base.getName();
        return retValue;
    }

    public int getAbsoluteTop() {
        int retValue;
        
        retValue = this.base.getAbsoluteTop();
        return retValue;
    }

    public int getAbsoluteLeft() {
        int retValue;
        
        retValue = this.base.getAbsoluteLeft();
        return retValue;
    }

    public boolean isEnabled() {
        boolean retValue;
        
        retValue = this.base.isEnabled();
        return retValue;
    }

    public String getHTML() {
        String retValue;
        
        retValue = this.base.getHTML();
        return retValue;
    }

    public void setValue(Object value) {
        Boolean old = (Boolean) this.getValue();
        this.setChecked( value == null ? false : ((Boolean) this.getRenderer().render( value )).booleanValue() );
        if( old != this.getValue() && !old.equals( this.getValue() ) ){
            this.changes.firePropertyChange( "value", old, this.getValue() );
        }
    }

    public Object getValue() {
        return  this.isChecked() ? Boolean.TRUE : Boolean.FALSE;
    }
    
    
}
