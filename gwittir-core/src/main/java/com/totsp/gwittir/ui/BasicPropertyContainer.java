/*
 * BasicPropertyContainer.java
 *
 * Created on April 12, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.BindingAction;
import com.totsp.gwittir.SourcesPropertyChangeEvents;

import com.totsp.gwittir.Validator;
import com.totsp.gwittir.validator.PassThroughValidator;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 * @author cooper
 */
public class BasicPropertyContainer extends PropertyContainer {
    private static final PassThroughValidator PASSTHROUGH = new PassThroughValidator();
    private PropertyGetter getter;
    private PropertySetter setter;
    private String properyName;
    /** Creates a new instance of BasicPropertyContainer */
    public BasicPropertyContainer(String propertyName, BoundWidget widget, String label,
            String helpHtml, Validator validator, PropertySetter setter, PropertyGetter getter) {
        this.setProperyName( propertyName );
        this.setValidator( validator );
        this.setGetter(getter);
        this.setter = setter;
        
        PopupPanel p = new PopupPanel(true);
        p.setStyleName("gwails-propertyHelp");
        p.setWidget(new HTML(helpHtml));
        
        Label l = new Label(label);
        l.setStyleName("gwails-propertyLabel");
        init(widget, l, p, this.getValidator() );
    }
    
    public PropertyGetter getGetter() {
        return getter;
    }
    
    public PropertySetter getSetter() {
        return setter;
    }
    
    public Validator getValidator() {
        Validator retValue;
        
        retValue = super.getValidator();
        
        return (retValue == null) ? PASSTHROUGH : retValue;
    }
    
    public void setGetter(PropertyGetter getter) {
        this.getter = getter;
    }
    
    public void setSetter(PropertySetter setter) {
        this.setter = setter;
    }
    
    public static interface PropertyGetter {
        public Object getProperty(Object target);
    }
    
    public static interface PropertySetter {
        public void setProperty(Object target, Object value);
    }
    
    public static class BasicBindingAction implements BindingAction {
        
        PropertyChangeListener l;
        ChangeListener c;
        PropertySetter setter;
        PropertyGetter getter;
        String propertyName;
        public BasicBindingAction(String propertyName, PropertySetter setter,
                PropertyGetter getter ){
            this.propertyName = propertyName;
            this.getter = getter;
            this.setter = setter;
        }
        
        public void bind(final BoundWidget w) {
            final SourcesPropertyChangeEvents model = (SourcesPropertyChangeEvents) w.getModel();
            
            if((l == null) || (c == null)) {
                c = new ChangeListener() {
                    public void onChange(Widget sender) {
                        GWT.log( "CHANGE "+propertyName+" "+w.getValue(), null );
                        setter.setProperty( w.getModel(), w.getValue() );
                    }
                };
                l = new PropertyChangeListener() {
                    public void propertyChange(
                            PropertyChangeEvent propertyChangeEvent) {
                        w.setValue( getter.getProperty(w.getModel() ) );
                    }
                };
            }
            
            w.addChangeListener(c);
            model.addPropertyChangeListener(propertyName, l);
        }
        
        public void unbind(BoundWidget widget) {
            final SourcesPropertyChangeEvents model = (SourcesPropertyChangeEvents) widget.getModel();
            model.removePropertyChangeListener(l);
            widget.removeChangeListener(c);
        }
        
        public void execute(BoundWidget model) {
        }
        
        public void set(BoundWidget widget) {
            widget.setValue( getter.getProperty( widget.getModel() ));
        }
        
    }
    
    public String getProperyName() {
        return properyName;
    }
    
    public void setProperyName(String properyName) {
        this.properyName = properyName;
    }
}
