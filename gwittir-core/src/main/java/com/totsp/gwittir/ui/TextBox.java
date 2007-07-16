/*
 * TextBox.java
 *
 * Created on July 16, 2007, 2:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author cooper
 */
public class TextBox extends AbstractBoundWidget {
    
    
    private com.google.gwt.user.client.ui.TextBox base = new com.google.gwt.user.client.ui.TextBox();
    private ChangeListenerCollection changeListeners = new ChangeListenerCollection();
    private String old;
    /** Creates a new instance of TextBox */
    public TextBox() {
        final TextBox instance = this;
        old = base.getText();
        this.setRenderer( new ToStringRenderer() );
        this.setComparator( new SimpleComparator() );
        this.addKeyboardListener( new KeyboardListener(){
            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                changes.firePropertyChange("value", old, base.getText() );
                old = base.getText();
            }

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyUp(Widget sender, char keyCode, int modifiers) {
            }
        });
        this.base.addChangeListener( new ChangeListener(){
            public void onChange(Widget sender) {
                changes.firePropertyChange("value", old, base.getText() );
                old = base.getText();
                changeListeners.fireChange( instance );
            }
            
        });
        super.initWidget(this.base);
    }
    
    public void removeKeyboardListener(KeyboardListener listener) {
        this.base.removeKeyboardListener(listener);
    }
    
    public void addKeyboardListener(KeyboardListener listener) {
        this.base.addKeyboardListener(listener);
    }
    
    public void setTextAlignment(TextBoxBase.TextAlignConstant align) {
        this.base.setTextAlignment(align);
    }
    
    public void setEnabled(boolean enabled) {
        this.base.setEnabled(enabled);
    }
    
    public void setReadOnly(boolean readOnly) {
    }
    
    public void setFocus(boolean focused) {
        this.base.setFocus(focused);
    }
    
    public void setAccessKey(char key) {
        this.base.setAccessKey(key);
    }
    
    public void setKey(char key) {
        this.base.setKey(key);
    }
    
    public void addFocusListener(FocusListener listener) {
        this.base.addFocusListener(listener);
    }
    
    public void removeFocusListener(FocusListener listener) {
        this.changeListeners.remove(listener);
    }
    
    public void removeChangeListener(ChangeListener listener) {
        this.changeListeners.add(listener);
    }
    
    public void addChangeListener(ChangeListener listener) {
        this.base.addChangeListener(listener);
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
    
    public void removeStyleName(String style) {
        this.base.removeStyleName(style);
    }
    
    public void setHeight(String height) {
        this.base.setHeight(height);
    }
    
    public void setName(String name) {
        this.base.setName(name);
    }
    
    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }
    
    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }
    
    public void unsinkEvents(int eventBitsToRemove) {
        this.base.unsinkEvents(eventBitsToRemove);
    }
    
    public void sinkEvents(int eventBitsToAdd) {
        this.base.sinkEvents(eventBitsToAdd);
    }
    
    public void setVisibleLength(int length) {
        this.base.setVisibleLength(length);
    }
    
    public void setTabIndex(int index) {
        this.base.setTabIndex(index);
    }
    
    public void setCursorPos(int pos) {
        this.base.setCursorPos(pos);
    }
    
    public void setMaxLength(int length) {
        this.base.setMaxLength(length);
    }
    
    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }
    
    public String getTitle() {
        String retValue;
        
        retValue = this.base.getTitle();
        return retValue;
    }
    
    public void selectAll() {
        this.base.selectAll();
    }
    
    public int getOffsetWidth() {
        int retValue;
        
        retValue = this.base.getOffsetWidth();
        return retValue;
    }
    
    public int getCursorPos() {
        int retValue;
        
        retValue = this.base.getCursorPos();
        return retValue;
    }
    
    public String getText() {
        String retValue;
        
        retValue = this.base.getText();
        return retValue;
    }
    
    public int getMaxLength() {
        int retValue;
        
        retValue = this.base.getMaxLength();
        return retValue;
    }
    
    public void setPixelSize(int width, int height) {
        this.base.setPixelSize(width, height);
    }
    
    public String getStyleName() {
        String retValue;
        
        retValue = this.base.getStyleName();
        return retValue;
    }
    
    public void cancelKey() {
        this.base.cancelKey();
    }
    
    public int getOffsetHeight() {
        int retValue;
        
        retValue = this.base.getOffsetHeight();
        return retValue;
    }
    
    public int getTabIndex() {
        int retValue;
        
        retValue = this.base.getTabIndex();
        return retValue;
    }
    
    public String getSelectedText() {
        String retValue;
        
        retValue = this.base.getSelectedText();
        return retValue;
    }
    
    public void setSelectionRange(int pos, int length) {
        this.base.setSelectionRange(pos, length);
    }
    
    public int getSelectionLength() {
        int retValue;
        
        retValue = this.base.getSelectionLength();
        return retValue;
    }
    
    public int getVisibleLength() {
        int retValue;
        
        retValue = this.base.getVisibleLength();
        return retValue;
    }
    
    public String getName() {
        String retValue;
        
        retValue = this.base.getName();
        return retValue;
    }

    public void setValue(Object value) {
        this.base.setText( this.getRenderer().render(value));
    }

    public Object getValue() {
        return this.base.getText();
    }
    
}
