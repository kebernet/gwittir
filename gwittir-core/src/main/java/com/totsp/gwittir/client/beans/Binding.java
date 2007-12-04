/*
 * Binding.java
 *
 * Created on July 16, 2007, 12:49 PM
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
package com.totsp.gwittir.client.beans;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.ui.BoundWidget;

import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.validator.Validator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a DataBinding between two objects. It also supports
 * Child bindings. For more information, see 
 * <a href="http://code.google.com/p/gwittir/wiki/Binding">Binding</a> in the Wiki.
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class Binding {

    private static final Introspector INTROSPECTOR = (Introspector) GWT.create(Introspector.class);
    private BindingInstance left;
    private BindingInstance right;
    private List children;
    /**
     *  TRUE = left; FALSE = right;
     */
    private Boolean lastSet = null;
    private boolean bound = false;

    /**
     * Creates an empty Binding object. This is mostly useful for top-of-tree
     * parent Bindings.
     */
    public Binding() {
        super();
    }

    /**
     * Creates a new binding. This method is a shorthand for working with BoundWidgets.
     * The bound widget provided will become the left-hand binding, and the "value"
     * property of the bound widget will be bound to the property specified by 
     * modelProperty of the object on the BoundWidget's "model" property.
     * @param widget BoundWidget containing the model.
     * @param validator A validator for the BouldWidget's value property.
     * @param feedback A feedback implementation for validation errors.
     * @param modelProperty The property on the Widgets model object to bind to.
     */
    public Binding(BoundWidget widget, Validator validator, ValidationFeedback feedback, String modelProperty) {
        this(widget, "value", validator, feedback, (Bindable) widget.getModel(), "modelProperty", null, null);
    }

    /**
     * Creates a new instance of Binding
     * @param left The left hand object.
     * @param leftProperty Property on the left object.
     * @param right The right hand object
     * @param rightProperty Property on the right object.
     */
    public Binding(Bindable left, String leftProperty, Bindable right,
            String rightProperty) {
        this.left = this.resolveDottedObject(left, leftProperty);
        this.right = this.resolveDottedObject(right, rightProperty);

        this.left.listener = new DefaultPropertyChangeListener(this.left, this.right);
        this.right.listener = new DefaultPropertyChangeListener(this.right, this.left);
    }

    /**
     * Creates a new Binding instance.
     * @param left The left hand object.
     * @param leftProperty The property of the left hand object.
     * @param leftValidator A validator for the left hand property.
     * @param leftFeedback Feedback for the left hand validator.
     * @param right The right hand object.
     * @param rightProperty The property on the right hand object
     * @param rightValidator Validator for the right hand property.
     * @param rightFeedback Feedback for the right hand validator.
     */
    public Binding(Bindable left, String leftProperty, Validator leftValidator,
            ValidationFeedback leftFeedback, Bindable right, String rightProperty,
            Validator rightValidator, ValidationFeedback rightFeedback) {

        this.left = this.resolveDottedObject(left, leftProperty);
        this.left.validator = leftValidator;
        this.left.feedback = leftFeedback;

        this.right = this.resolveDottedObject(right, rightProperty);
        this.right.validator = rightValidator;
        this.right.feedback = rightFeedback;

        this.left.listener = new DefaultPropertyChangeListener(this.left, this.right);
        this.right.listener = new DefaultPropertyChangeListener(this.right, this.left);

    }

    /**
     * 
     * @param left 
     * @param leftProperty 
     * @param leftConverter 
     * @param right 
     * @param rightProperty 
     * @param rightConverter 
     */
    public Binding(Bindable left, String leftProperty, Converter leftConverter,
            Bindable right, String rightProperty, Converter rightConverter) {

        this.left = this.resolveDottedObject(left, leftProperty);
        this.left.converter = leftConverter;
        this.right = this.resolveDottedObject(right, rightProperty);
        this.right.converter = rightConverter;

        this.left.listener = new DefaultPropertyChangeListener(this.left, this.right);
        this.right.listener = new DefaultPropertyChangeListener(this.right, this.left);
    }

    /**
     * Creates a Binding with two populated binding instances.
     * @param left The left binding instance.
     * @param right The right binding instance
     */
    public Binding(BindingInstance left, BindingInstance right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Sets the right objects property to the current value of the left.
     */
    public void setRight() {
        if (left != null && right != null) {
            try {
                left.listener.propertyChange(new PropertyChangeEvent(left.object,
                        left.property.getName(),
                        null,
                        left.property.getAccessorMethod().invoke(left.object, null)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.setRight();
        }
        this.lastSet = Boolean.FALSE;
    }

    /**
     * Sets the left hand property to the current value of the right.
     */
    public void setLeft() {
        if (left != null && right != null) {
            try {
                right.listener.propertyChange(new PropertyChangeEvent(right.object,
                        right.property.getName(),
                        null,
                        right.property.getAccessorMethod().invoke(right.object, null)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.setLeft();
        }
        this.lastSet = Boolean.TRUE;
    }

    /**
     * Establishes a two-way binding between the objects.
     */
    public void bind() {
        if (left != null && right != null) {
            left.object.addPropertyChangeListener(left.property.getName(),
                    left.listener);

            right.object.addPropertyChangeListener(right.property.getName(),
                    right.listener);
        }
        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.bind();
        }
        this.bound = true;
    }

    /**
     * Returns a list of child Bindings.
     * @return List of child bindings.
     */
    public List getChildren() {
        return children = (children == null) ? new ArrayList() : children;
    }

    /**
     * Performs a quick validation on the Binding to determine if it is valid.
     * @return boolean indicating all values are valid.
     */
    public boolean isValid() {

        try {
            if (left != null && right != null) {
                if (left.validator != null) {
                    left.validator.validate(left.property.getAccessorMethod().invoke(left.object, null));
                }
                if (right.validator != null) {
                    right.validator.validate(right.property.getAccessorMethod().invoke(right.object, null));
                }
            }
            boolean valid = true;
            for (int i = 0; (children != null) && (i < children.size()); i++) {
                Binding child = (Binding) children.get(i);
                valid = valid & child.isValid();
            }
            return valid;
        } catch (ValidationException ve) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid", ve);
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Breaks the two way binding and removes all listeners.
     */
    public void unbind() {
        if (left != null && right != null) {
            left.object.removePropertyChangeListener(left.listener);
            right.object.removePropertyChangeListener(right.listener);
        }
        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = (Binding) children.get(i);
            child.unbind();
        }
        this.bound = false;
    }

    /**
     * A data class containing the relevant data for one half of a binding relationship.
     */
    public static class BindingInstance {

        /**
         * The Object being bound.
         */
        public Bindable object;
        /**
         * A converter when needed.
         */
        public Converter converter;
        /**
         * The property name being bound.
         */
        public Property property;
        private PropertyChangeListener listener;
        /**
         * A ValidationFeedback object when needed.
         */
        public ValidationFeedback feedback;
        /**
         * A Validator object when needed.
         */
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
            if (instance.validator != null) {
                try {
                    value = instance.validator.validate(value);
                } catch (ValidationException ve) {
                    if (instance.feedback != null) {
                        if (this.lastException != null) {
                            instance.feedback.resolve(propertyChangeEvent.getSource());
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

            if (this.instance.feedback != null) {
                this.instance.feedback.resolve(propertyChangeEvent.getSource());
            }

            this.lastException = null;

            if (instance.converter != null) {
                value = instance.converter.convert(value);
            }

            Object[] args = new Object[1];
            args[0] = value;
            try {
                target.property.getMutatorMethod().invoke(target.object, args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Returns the left hand BindingInstance.
     * @return Returns the left hand BindingInstance.
     */
    public BindingInstance getLeft() {
        return this.left;
    }

    /**
     * Returns the right hand BindingInstance.
     * @return Returns the left hand BindingInstance.
     */
    public BindingInstance getRight() {
        return this.right;
    }

    /**
     * 
     * @return int based on hash of the two objects being bound.
     */
    public int hashCode() {
        return this.right.object.hashCode() ^ this.left.object.hashCode();
    }

    /**
     *  
     * @return  String value representing the binding.
     */
    public String toString() {
        return "Binding [ \n + " + this.right.object + " \n " + this.left.object + "\n ]";
    }

    private BindingInstance resolveDottedObject(Bindable object, String propertyName) {
        int dotIndex = propertyName.indexOf(".");
        BindingInstance instance = new BindingInstance();
        ResetTargetPropertyChangeListener rtpcl = 
                dotIndex == -1 ? 
                    null : 
                    new ResetTargetPropertyChangeListener(instance, object, propertyName );
        while (dotIndex != -1) {
            String pname = propertyName.substring(0, dotIndex);
            propertyName = propertyName.substring(dotIndex + 1);
            try {
                object.addPropertyChangeListener(pname, rtpcl);
                object = (Bindable) INTROSPECTOR.getDescriptor(object).getProperty(pname).getAccessorMethod().invoke(object, null);
                
            } catch (ClassCastException cce) {
                throw new RuntimeException("Nonbindable sub property: " + object + " . " + pname, cce);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dotIndex = propertyName.indexOf(".");
        }
        instance.object = object;
        instance.property = INTROSPECTOR.getDescriptor(object).getProperty(propertyName);
        return instance;

    }

    private class ResetTargetPropertyChangeListener
            implements PropertyChangeListener {
        
        BindingInstance target;
        Bindable sourceObject;
        String propertyName;
        ResetTargetPropertyChangeListener(BindingInstance target, 
                Bindable sourceObject, String propertyName){
            this.target = target;
            this.sourceObject = sourceObject;
            this.propertyName = propertyName;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if(bound){
                unbind();
                bound = true;
            }
            BindingInstance newInstance = resolveDottedObject( sourceObject, propertyName);
            target.object = newInstance.object;
            if( lastSet == Boolean.TRUE ){
                setLeft();
            } else if ( lastSet == Boolean.FALSE ){
                setRight();
            }
            if(bound){
                bind();
            }
            ((Bindable) evt.getSource() ).removePropertyChangeListener(this);
        }
        
    }
}
