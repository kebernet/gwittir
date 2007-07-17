/*
 * Binding.java
 *
 * Created on July 16, 2007, 12:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.beans;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.ValidationException;
import com.totsp.gwittir.ValidationFeedback;
import com.totsp.gwittir.Validator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author cooper
 */
public class Binding {
    private static final Introspector INTROSPECTOR = (Introspector) GWT.create(Introspector.class);
    private BindingInstance left;
    private BindingInstance right;
    private List children;
    
    /** Creates a new instance of Binding */
    public Binding(Bindable left, String leftProperty, Bindable right,
            String rightProperty) {
        this.left = new BindingInstance();
        this.left.object = left;
        this.left.property = INTROSPECTOR.getDescriptor(left)
        .getProperty(leftProperty);
        this.right = new BindingInstance();
        this.right.object = right;
        this.right.property = INTROSPECTOR.getDescriptor(right)
        .getProperty(rightProperty);
    }
    
    public Binding(Bindable left, String leftProperty, Validator leftValidator,
            ValidationFeedback leftFeedback, Bindable right, String rightProperty,
            Validator rightValidator, ValidationFeedback rightFeedback) {
        this.left = new BindingInstance();
        this.left.object = left;
        this.left.property = INTROSPECTOR.getDescriptor(left)
        .getProperty(leftProperty);
        this.left.validator = leftValidator;
        this.left.feedback = leftFeedback;
        
        this.right = new BindingInstance();
        this.right.object = right;
        this.right.property = INTROSPECTOR.getDescriptor(right)
        .getProperty(rightProperty);
        this.right.validator = rightValidator;
        this.right.feedback = rightFeedback;
    }
    
    public Binding(Bindable left, String leftProperty, Converter leftConverter,
            Bindable right, String rightProperty, Converter rightConverter) {
        this.left = new BindingInstance();
        this.left.object = left;
        this.left.property = INTROSPECTOR.getDescriptor(left)
        .getProperty(leftProperty);
        this.left.converter = leftConverter;
        this.right = new BindingInstance();
        this.right.object = right;
        this.right.property = INTROSPECTOR.getDescriptor(right)
        .getProperty(rightProperty);
        this.right.converter = rightConverter;
    }
    
    public Binding(BindingInstance left, BindingInstance right) {
        this.left = left;
        this.right = right;
    }
    
    public void setRight(){
        try{
            left.listener.propertyChange( new PropertyChangeEvent( left.object,
                    left.property.getName(),
                    null,
                    left.property
                    .getAccessMethod()
                    .invoke( left.object, null ) ) );
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        for(int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.setRight();
        }
    }
    
    public void setLeft(){
        try{
            right.listener.propertyChange( new PropertyChangeEvent( right.object,
                    right.property.getName(),
                    null,
                    right.property
                    .getAccessMethod()
                    .invoke( right.object, null ) ) );
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        for(int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.setLeft();
        }
    }
    
    public void bind() {
        left.listener = new DefaultPropertyChangeListener(left, right);
        left.object.addPropertyChangeListener(left.property.getName(),
                left.listener);
        
        right.listener = new DefaultPropertyChangeListener(right, left);
        right.object.addPropertyChangeListener(right.property.getName(),
                right.listener);
        
        for(int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.bind();
        }
    }
    
    public List getChildren() {
        return children = (children == null) ? new ArrayList() : children;
    }
    
    public boolean isValid(){
        try{
            if( left.validator != null ){
                left.validator.validate( left.property.getAccessMethod().invoke(left.object, null) );
            }
            if( right.validator != null ){
                right.validator.validate( right.property.getAccessMethod().invoke(right.object, null ) );
            }
            boolean valid = true;
            for(int i = 0; (children != null) && (i < children.size()); i++) {
                Binding child = (Binding) children.get(i);
                valid = valid & child.isValid();
            }
            return valid;
        } catch( ValidationException ve ){
            GWT.log( "Invalid", ve);
            return false;
        } catch(Exception e){
            throw new RuntimeException( e );
        }
    }
    
    public void unbind() {
        left.object.removePropertyChangeListener(left.listener);
        left.listener = null;
        
        right.object.removePropertyChangeListener(right.listener);
        right.listener = null;
        
        for(int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.unbind();
        }
    }
    
    public static class BindingInstance {
        public Bindable object;
        public Converter converter;
        public Property property;
        private PropertyChangeListener listener;
        public ValidationFeedback feedback;
        public Validator validator;
    }
    
    private static class DefaultPropertyChangeListener
            implements PropertyChangeListener {
        private BindingInstance instance;
        private BindingInstance target;
        private ValidationException lastException = null;
        
        DefaultPropertyChangeListener(BindingInstance instance,
                BindingInstance target) {
            this.instance = instance;
            this.target = target;
        }
        
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            
            Object value = propertyChangeEvent.getNewValue();
            if(instance.validator != null) {
                try {
                    value = instance.validator.validate(value);
                } catch(ValidationException ve) {
                    if(instance.feedback != null) {
                        if(this.lastException != null) {
                            instance.feedback.resolve();
                        }
                        
                        instance.feedback.handleException(propertyChangeEvent.getSource(),
                                ve);
                        this.lastException = ve;
                        return;
                    } else {
                        this.lastException = ve;
                        throw new RuntimeException(ve);
                    }
                }
            }
            
            if(this.instance.feedback != null) {
                this.instance.feedback.resolve();
            }
            
            this.lastException = null;
            
            if(instance.converter != null) {
                value = instance.converter.convert(value);
            }
            
            Object[] args =  new Object[1];
            args[0] = value;
            try {
                target.property.getMutatorMethod().invoke(target.object, args);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
