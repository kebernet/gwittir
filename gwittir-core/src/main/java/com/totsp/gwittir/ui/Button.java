/*
 * Button.java
 *
 * Created on July 11, 2007, 7:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Widget;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author cooper
 */
public class Button extends AbstractBoundWidget {
    
    private final com.google.gwt.user.client.ui.Button base = new com.google.gwt.user.client.ui.Button();
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private Object value;
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }
    
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyName, l);
    }
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return changes.getPropertyChangeListeners();
    }
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener l) {
        changes.removePropertyChangeListener(propertyName, l);
    }
    
    /** Creates a new instance of Button */
    public Button() {
        this.init();
    }
    
    public Button(String label){
        this.setValue(base);
        this.init();
    }
    
    private void init(){
        final Button instance = this;
        ClickListener listener = new ClickListener(){
            public void onClick(Widget sender) {
                if( getAction() != null ){
                    getAction().execute( instance );
                }
            }
        };
        this.base.addClickListener( listener );
        this.setRenderer( new ToStringRenderer() );
        this.initWidget( this.base );
    }
    
    public void setTitle(String title) {
        this.base.setTitle(title);
    }
    
    public void setText(String text) {
        this.base.setText(text);
    }
    
    public void setHTML(String html) {
        this.base.setHTML(html);
    }
    
    public void removeKeyboardListener(KeyboardListener listener) {
        this.base.removeKeyboardListener(listener);
    }
    
    public void addKeyboardListener(KeyboardListener listener) {
        this.base.addKeyboardListener(listener);
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
    
    public void setEnabled(boolean enabled) {
        this.base.setEnabled(enabled);
    }
    
    public void setFocus(boolean focused) {
        this.base.setFocus(focused);
    }
    
    public void addFocusListener(FocusListener listener) {
        this.base.addFocusListener(listener);
    }
    
    public void removeFocusListener(FocusListener listener) {
        this.base.removeFocusListener(listener);
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
    
    public String getHTML() {
        String retValue;
        
        retValue = this.base.getHTML();
        return retValue;
    }
    
    public String getStyleName() {
        String retValue;
        
        retValue = this.base.getStyleName();
        return retValue;
    }
    
    public String getTitle() {
        String retValue;
        
        retValue = this.base.getTitle();
        return retValue;
    }
    
    public void setValue(Object value) {
        Object old = this.value;
        this.value = value;
        this.base.setText( this.getRenderer().render(value) );
        this.changes.firePropertyChange("value", old, value);
    }
    
    public Object getValue() {
        return this.value;
    }

    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        this.setValue( renderer.render( this.getValue() ) );
    }

    
    
}
