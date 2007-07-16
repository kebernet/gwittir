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
    
    public Binding(Bindable left, String leftProperty, Validator leftValidator, ValidationFeedback leftFeedback,
            Bindable right, String rightProperty, Validator rightValidator, ValidationFeedback rightFeedback ){
     
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
    
    public Binding(Bindable left, String leftProperty, Converter leftConverter, Bindable right,
        String rightProperty, Converter rightConverter ) {
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
    
    public Binding(BindingInstance left, BindingInstance right){
        this.left = left;
        this.right = right;
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

        DefaultPropertyChangeListener(BindingInstance instance,
            BindingInstance target) {
            this.instance = instance;
            this.target = target;
        }

        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            GWT.log("PropertyChange: [" +
                propertyChangeEvent.getPropertyName() + " old: " +
                propertyChangeEvent.getOldValue() + " new: " +
                propertyChangeEvent.getNewValue() + "]", null);

            Object value = propertyChangeEvent.getNewValue();

            if(instance.validator != null) {
                try {
                    value = instance.validator.validate(value);
                } catch(ValidationException ve) {
                    if(instance.feedback != null) {
                        instance.feedback.handleException(propertyChangeEvent.getSource(),
                            ve);
                    } else {
                        throw new RuntimeException(ve);
                    }
                }
            }

            if(instance.converter != null) {
                value = instance.converter.convert(value);
            }

            Object[] args = { value };

            try {
                target.property.getMutatorMethod().invoke(target.object, args);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
