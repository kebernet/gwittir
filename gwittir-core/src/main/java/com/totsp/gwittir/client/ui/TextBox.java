/*
 * TextBox.java
 *
 * Created on July 16, 2007, 2:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.action.Action;
import java.util.Comparator;


/**
 *
 * @author cooper
 */
public class TextBox extends AbstractBoundWidget {
    private com.google.gwt.user.client.ui.TextBox base = new com.google.gwt.user.client.ui.TextBox();
    private ChangeListenerCollection changeListeners = new ChangeListenerCollection();
    private String old;
    public TextBox() {
        this(false);
    }
    
    /** Creates a new instance of TextBox */
    public TextBox(final boolean updateOnKeypress) {
        final TextBox instance = this;
        old = base.getText();
        this.setRenderer(new ToStringRenderer());
        this.setComparator(new SimpleComparator());
        
        if(updateOnKeypress) {
            this.addKeyboardListener(new KeyboardListener() {
                public void onKeyPress(Widget sender, char keyCode,
                        int modifiers) {
                    
                        changes.firePropertyChange("value", old,
                                getValue() );
                        old = (String) getValue();
                     
                }
                
                public void onKeyDown(Widget sender, char keyCode,
                        int modifiers) {
                }
                
                public void onKeyUp(Widget sender, char keyCode,
                        int modifiers) {
                }
            });
        }
        
        this.base.addChangeListener(new ChangeListener() {
            public void onChange(Widget sender) {
               
                    changes.firePropertyChange("value", old, getValue() );
                    old = (String) getValue();
                    changeListeners.fireChange(instance);
               
            }
        });
        super.initWidget(this.base);
    }
    
    public void addChangeListener(ChangeListener listener) {
        this.base.addChangeListener(listener);
    }
    
    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }
    
    public void addFocusListener(FocusListener listener) {
        this.base.addFocusListener(listener);
    }
    
    public void addKeyboardListener(KeyboardListener listener) {
        this.base.addKeyboardListener(listener);
    }
    
    public void cancelKey() {
        this.base.cancelKey();
    }
    
    public int getCursorPos() {
        int retValue;
        
        retValue = this.base.getCursorPos();
        
        return retValue;
    }
    
    public int getMaxLength() {
        int retValue;
        
        retValue = this.base.getMaxLength();
        
        return retValue;
    }
    
    public String getName() {
        String retValue;
        
        retValue = this.base.getName();
        
        return retValue;
    }
    
    public int getOffsetHeight() {
        int retValue;
        
        retValue = this.base.getOffsetHeight();
        
        return retValue;
    }
    
    public int getOffsetWidth() {
        int retValue;
        
        retValue = this.base.getOffsetWidth();
        
        return retValue;
    }
    
    public String getSelectedText() {
        String retValue;
        
        retValue = this.base.getSelectedText();
        
        return retValue;
    }
    
    public int getSelectionLength() {
        int retValue;
        
        retValue = this.base.getSelectionLength();
        
        return retValue;
    }
    
    public String getStyleName() {
        String retValue;
        
        retValue = this.base.getStyleName();
        
        return retValue;
    }
    
    public int getTabIndex() {
        int retValue;
        
        retValue = this.base.getTabIndex();
        
        return retValue;
    }
    
    public String getText() {
        String retValue;
        
        retValue = this.base.getText();
        
        return retValue;
    }
    
    public String getTitle() {
        String retValue;
        
        retValue = this.base.getTitle();
        
        return retValue;
    }
    
    public Object getValue() {
        return this.base.getText().length() == 0 ? null : this.base.getText();
    }
    
    public int getVisibleLength() {
        int retValue;
        
        retValue = this.base.getVisibleLength();
        
        return retValue;
    }
    
    public void removeChangeListener(ChangeListener listener) {
        this.changeListeners.add(listener);
    }
    
    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }
    
    public void removeFocusListener(FocusListener listener) {
        this.changeListeners.remove(listener);
    }
    
    public void removeKeyboardListener(KeyboardListener listener) {
        this.base.removeKeyboardListener(listener);
    }
    
    public void removeStyleName(String style) {
        this.base.removeStyleName(style);
    }
    
    public void selectAll() {
        this.base.selectAll();
    }
    
    public void setAccessKey(char key) {
        this.base.setAccessKey(key);
    }
    
    public void setCursorPos(int pos) {
        this.base.setCursorPos(pos);
    }
    
    public void setEnabled(boolean enabled) {
        this.base.setEnabled(enabled);
    }
    
    public void setFocus(boolean focused) {
        this.base.setFocus(focused);
    }
    
    public void setHeight(String height) {
        this.base.setHeight(height);
    }
    
    public void setKey(char key) {
        this.base.setKey(key);
    }
    
    public void setMaxLength(int length) {
        this.base.setMaxLength(length);
    }
    
    public void setName(String name) {
        this.base.setName(name);
    }
    
    public void setPixelSize(int width, int height) {
        this.base.setPixelSize(width, height);
    }
    
    public void setReadOnly(boolean readOnly) {
    }
    
    public void setSelectionRange(int pos, int length) {
        this.base.setSelectionRange(pos, length);
    }
    
    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }
    
    public void setStyleName(String style) {
        this.base.setStyleName(style);
    }
    
    public void setTabIndex(int index) {
        this.base.setTabIndex(index);
    }
    
    public void setText(String text) {
        this.base.setText(text);
    }
    
    public void setTextAlignment(TextBoxBase.TextAlignConstant align) {
        this.base.setTextAlignment(align);
    }
    
    public void setTitle(String title) {
        this.base.setTitle(title);
    }
    
    public void setValue(Object value) {
        this.base.setText(this.getRenderer().render(value));
    }
    
    public void setVisibleLength(int length) {
        this.base.setVisibleLength(length);
    }
    
    public void setWidth(String width) {
        this.base.setWidth(width);
    }
    
    public void sinkEvents(int eventBitsToAdd) {
        this.base.sinkEvents(eventBitsToAdd);
    }
    
    public void unsinkEvents(int eventBitsToRemove) {
        this.base.unsinkEvents(eventBitsToRemove);
    }

    public void setModel(Object model) {
        super.setModel(model);
    }

    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
    }

    public void setAction(Action action) {
        super.setAction(action);
    }

    public Comparator getComparator() {
        Comparator retValue;
        
        retValue = super.getComparator();
        return retValue;
    }

    public Object getModel() {
        Object retValue;
        
        retValue = super.getModel();
        return retValue;
    }

    public Action getAction() {
        Action retValue;
        
        retValue = super.getAction();
        return retValue;
    }
}
