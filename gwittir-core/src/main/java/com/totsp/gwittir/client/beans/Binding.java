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

import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.validator.Validator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * This class represents a DataBinding between two objects. It also supports
 * Child bindings. For more information, see <a
 * href="http://code.google.com/p/gwittir/wiki/Binding">Binding</a> in the Wiki.
 * @see com.totsp.gwittir.client.beans.BindingBuilder
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet"
 *         Cooper</a>
 */
public class Binding {
    private static final Logger LOGGER = Logger.getLogger("com.totsp.gwittir.client.beans");
    private static final Introspector INTROSPECTOR = Introspector.INSTANCE;
    BindingInstance left;
    BindingInstance right;

    /**
     * TRUE = left; FALSE = right;
     */
    private Boolean lastSet = null;
    private List<Binding> children;
    private boolean bound = false;

    /**
     * Creates an empty Binding object. This is mostly useful for top-of-tree
     * parent Bindings.
     */
    public Binding() {
        super();
    }

    /**
     * Creates a new instance of Binding
     *
     * @param left
     *            The left hand object.
     * @param leftProperty
     *            Property on the left object.
     * @param right
     *            The right hand object
     * @param rightProperty
     *            Property on the right object.
     */
    public Binding(SourcesPropertyChangeEvents left, String leftProperty, SourcesPropertyChangeEvents right, String rightProperty) {
        assert left != null : "Left hand side of listener for property "+leftProperty+" is null.";
        assert right != null : "Right hand side of listener for property "+rightProperty+" is null.";
        
        this.left = this.createBindingInstance(left, leftProperty);
        this.right = this.createBindingInstance(right, rightProperty);

        this.left.listener = new DefaultPropertyChangeListener(this.left, this.right);
        this.right.listener = new DefaultPropertyChangeListener(this.right, this.left);
    }

    /**
     * Creates a new Binding instance.
     *
     * @param left
     *            The left hand object.
     * @param leftProperty
     *            The property of the left hand object.
     * @param leftValidator
     *            A validator for the left hand property.
     * @param leftFeedback
     *            Feedback for the left hand validator.
     * @param right
     *            The right hand object.
     * @param rightProperty
     *            The property on the right hand object
     * @param rightValidator
     *            Validator for the right hand property.
     * @param rightFeedback
     *            Feedback for the right hand validator.
     */
    public Binding(
        SourcesPropertyChangeEvents left, String leftProperty, Validator leftValidator, ValidationFeedback leftFeedback, SourcesPropertyChangeEvents right,
        String rightProperty, Validator rightValidator, ValidationFeedback rightFeedback) {
        assert left != null : "Left hand side of listener for property "+leftProperty+" is null.";
        assert right != null : "Right hand side of listener for property "+rightProperty+" is null.";
        this.left = this.createBindingInstance(left, leftProperty);
        this.left.validator = leftValidator;
        this.left.feedback = leftFeedback;

        this.right = this.createBindingInstance(right, rightProperty);
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
    public Binding(
        SourcesPropertyChangeEvents left, String leftProperty, Converter leftConverter, SourcesPropertyChangeEvents right, String rightProperty,
        Converter rightConverter) {
        assert left != null : "Left hand side of listener for property "+leftProperty+" is null.";
        assert right != null : "Right hand side of listener for property "+rightProperty+" is null.";
        this.left = this.createBindingInstance(left, leftProperty);
        this.left.converter = leftConverter;
        this.right = this.createBindingInstance(right, rightProperty);
        this.right.converter = rightConverter;

        this.left.listener = new DefaultPropertyChangeListener(this.left, this.right);
        this.right.listener = new DefaultPropertyChangeListener(this.right, this.left);
    }

    /**
     * Creates a Binding with two populated binding instances.
     *
     * @param left
     *            The left binding instance.
     * @param right
     *            The right binding instance
     */
    public Binding(BindingInstance left, BindingInstance right) {
        assert left.object != null : "Left hand side of listener for property "+left.property.getName()+" is null.";
        assert right.object != null : "Right hand side of listener for property "+right.property.getName()+" is null.";
        this.left = left;
        this.right = right;
    }

    /**
     * Creates a new binding. This method is a shorthand for working with
     * BoundWidgets. The bound widget provided will become the left-hand
     * binding, and the "value" property of the bound widget will be bound to
     * the property specified by modelProperty of the object on the
     * BoundWidget's "model" property.
     *
     * @param widget
     *            BoundWidget containing the model.
     * @param validator
     *            A validator for the BouldWidget's value property.
     * @param feedback
     *            A feedback implementation for validation errors.
     * @param modelProperty
     *            The property on the Widgets model object to bind to.
     */
    public <T>Binding(BoundWidget<T> widget, Validator validator, ValidationFeedback feedback, String modelProperty) {
        this(widget, "value", validator, feedback, (SourcesPropertyChangeEvents) widget.getModel(), "modelProperty", null, null);
    }

    /**
     * Returns a list of child Bindings.
     *
     * @return List of child bindings.
     */
    public List<Binding> getChildren() {
        return children = (children == null) ? new ArrayList<Binding>()
                                             : children;
    }

    /**
     * Sets the left hand property to the current value of the right.
     */
    public void setLeft() {
        if ((left != null) && (right != null)) {
            try {
                right.listener.propertyChange(
                    new PropertyChangeEvent(
                        right.object, right.property.getName(), null,
                        right.property.getAccessorMethod().invoke(right.object, null)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } 

        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = children.get(i);
            child.setLeft();
        }

        this.lastSet = Boolean.TRUE;
    }

    /**
     * Returns the left hand BindingInstance.
     *
     * @return Returns the left hand BindingInstance.
     */
    public BindingInstance getLeft() {
        return this.left;
    }

    /**
     * Sets the right objects property to the current value of the left.
     */
    public void setRight() {
        if ((left != null) && (right != null)) {
            try {
                left.listener.propertyChange(
                    new PropertyChangeEvent(
                        left.object, left.property.getName(), null,
                        left.property.getAccessorMethod().invoke(left.object, null)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = children.get(i);
            child.setRight();
        }

        this.lastSet = Boolean.FALSE;
    }

    /**
     * Returns the right hand BindingInstance.
     *
     * @return Returns the left hand BindingInstance.
     */
    public BindingInstance getRight() {
        return this.right;
    }

    /**
     * Performs a quick validation on the Binding to determine if it is valid.
     *
     * @return boolean indicating all values are valid.
     */
    public boolean isValid() {
        try {
            if ((left != null) && (right != null)) {
                if (left.validator != null) {
                    left.validator.validate(left.property.getAccessorMethod().invoke(left.object, null));
                }

                if (right.validator != null) {
                    right.validator.validate(right.property.getAccessorMethod().invoke(right.object, null));
                }
            }

            boolean valid = true;

            for (int i = 0; (children != null) && (i < children.size()); i++) {
                Binding child = children.get(i);
                valid = valid & child.isValid();
            }

            return valid;
        } catch (ValidationException ve) {
            Binding.LOGGER.log(Level.INFO, "Invalid", ve);

            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Establishes a two-way binding between the objects.
     */
    public void bind() {
        if ((left != null) && (right != null)) {
            left.object.addPropertyChangeListener(left.property.getName(), left.listener);

            if (left.nestedListener != null) {
                left.nestedListener.setup();
            }

            right.object.addPropertyChangeListener(right.property.getName(), right.listener);

            if (right.nestedListener != null) {
                right.nestedListener.setup();
            }
        }

        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = children.get(i);
            child.bind();
        }

        this.bound = true;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if(obj.getClass() != this.getClass() ){
            return false;
        }
        final Binding other = (Binding) obj;

        if ((this.left != other.left) && ((this.left == null) || !this.left.equals(other.left))) {
            return false;
        }

        if ((this.right != other.right) && ((this.right == null) || !this.right.equals(other.right))) {
            return false;
        }

        return true;
    }

    /**
     *
     * @return int based on hash of the two objects being bound.
     */
    @Override
    public int hashCode() {
        return this.right.object.hashCode() ^ this.left.object.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Binding property ").append(left.property)
                                                      .append(" on ")
                                                      .append(left.object)
                                                      .append(" to ")
                                                      .append(right.property)
                                                      .append(" on ")
                                                      .append(right.object)
                                                      .append(" ] with children :");
        for(Binding b : this.getChildren() ){
            sb.append("\n")
              .append(b.toString());
        }
        return sb.toString();
                                                      
    }



    /**
     * Breaks the two way binding and removes all listeners.
     */
    public void unbind() {
        if ((left != null) && (right != null)) {
            left.object.removePropertyChangeListener(left.property.getName(), left.listener);
            if(left.feedback != null){
                try{
                    left.feedback.resolve(left.object);
                } catch(Exception e){
                    LOGGER.log(Level.INFO, "Exception cleaning up feedback ", e);
                }
            }
            if (left.nestedListener != null) {
                left.nestedListener.cleanup();
            }

            right.object.removePropertyChangeListener(right.property.getName(), right.listener);
            if(right.feedback != null){
                try{
                    right.feedback.resolve(right.object);
                } catch(Exception e){
                    LOGGER.log(Level.INFO, "Exception cleaning up feedback ", e);
                }
            }
            if (right.nestedListener != null) {
                right.nestedListener.cleanup();
            }
        }

        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = children.get(i);
            child.unbind();
        }

        this.bound = false;
    }

    public boolean validate() {
        boolean valid = true;

        if ((left != null) && (right != null)) {
            if (left.validator != null) {
                try {
                    left.validator.validate(left.property.getAccessorMethod().invoke(left.object, null));
                } catch (ValidationException ve) {
                    valid = false;

                    if (left.feedback != null) {
                        left.feedback.handleException(left.object, ve);
                    }

                    if (left.listener instanceof DefaultPropertyChangeListener) {
                        ((DefaultPropertyChangeListener) left.listener).lastException = ve;
                    }
                } catch (Exception e) {
                    valid = false;
                    Binding.LOGGER.log(Level.WARN, null, e);
                }
            }

            if (right.validator != null) {
                try {
                    right.validator.validate(right.property.getAccessorMethod().invoke(right.object, null));
                } catch (ValidationException ve) {
                    valid = false;

                    if (right.feedback != null) {
                        right.feedback.handleException(right.object, ve);
                    }

                    if (right.listener instanceof DefaultPropertyChangeListener) {
                        ((DefaultPropertyChangeListener) right.listener).lastException = ve;
                    }
                } catch (Exception e) {
                    valid = false;
                    Binding.LOGGER.log(Level.WARN, null, e);
                }
            }
        }

        for (int i = 0; (children != null) && (i < children.size()); i++) {
            Binding child = children.get(i);
            valid = valid & child.validate();
        }

        return valid;
    }

    final BindingInstance createBindingInstance(SourcesPropertyChangeEvents object, String propertyName) {
        int dotIndex = propertyName.indexOf(".");
        BindingInstance instance = new BindingInstance();
        NestedPropertyChangeListener rtpcl = (dotIndex == -1) ? null
                                                              : new NestedPropertyChangeListener(
                instance, object, propertyName);
        ArrayList parents = new ArrayList();
        ArrayList propertyNames = new ArrayList();

        while (dotIndex != -1) {
            String pname = propertyName.substring(0, dotIndex);
            propertyName = propertyName.substring(dotIndex + 1);
            parents.add(object);

            try {
                String descriminator = null;
                int descIndex = pname.indexOf("[");

                if (descIndex != -1) {
                    descriminator = pname.substring(descIndex + 1, pname.indexOf("]", descIndex));
                    pname = pname.substring(0, descIndex);
                }

                propertyNames.add(pname);

                if (descriminator != null) {
                    // TODO Need a loop here to handle
                    // multi-dimensional/collections of collections
                    Object collectionOrArray = INTROSPECTOR.getDescriptor(object)
                                                           .getProperty(pname)
                                                           .getAccessorMethod()
                                                           .invoke(object, null);
                    object = this.getDiscriminatedObject(collectionOrArray, descriminator);
                } else {
                    object = (SourcesPropertyChangeEvents) INTROSPECTOR.getDescriptor(object)
                                                    .getProperty(pname)
                                                    .getAccessorMethod()
                                                    .invoke(object, null);
                }
            } catch (ClassCastException cce) {
                throw new RuntimeException("Nonbindable sub property: " + object + " . " + pname, cce);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            dotIndex = propertyName.indexOf(".");
        }

        if (rtpcl != null) {
            rtpcl.parents = new SourcesPropertyChangeEvents[parents.size()];
            parents.toArray(rtpcl.parents);
            rtpcl.propertyNames = new String[propertyNames.size()];
            propertyNames.toArray(rtpcl.propertyNames);
        }

        instance.object = object;

        try {
            instance.property = INTROSPECTOR.getDescriptor(object)
                                            .getProperty(propertyName);
            if(instance.property == null ){
                throw new NullPointerException("Property Not Found.");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("Exception getting property " + propertyName, e);
        }

        instance.nestedListener = rtpcl;

        return instance;
    }

    private SourcesPropertyChangeEvents getBindableAtCollectionIndex(Collection collection, int index) {
        int i = 0;
        SourcesPropertyChangeEvents result = null;

        for (Iterator it = collection.iterator(); it.hasNext() && (i <= index); i++) {
            result = (SourcesPropertyChangeEvents) it.next();
            return result;
            // TODO - bug here i think - "i++" is never be called, so index is basically ignored 
        }

        throw new IndexOutOfBoundsException("Binding descriminator too high: " + index);
    }

    private SourcesPropertyChangeEvents getBindableAtMapKey(Map map, String key) {
        SourcesPropertyChangeEvents result = null;

        for (Iterator it = map.entrySet()
                              .iterator(); it.hasNext();) {
            Entry e = (Entry) it.next();

            if (e.getKey()
                     .toString()
                     .equals(key)) {
                result = (SourcesPropertyChangeEvents) e.getValue();

                break;
            }
        }

        return result;
    }

    private SourcesPropertyChangeEvents getBindableFromArrayWithProperty(SourcesPropertyChangeEvents[] array, String propertyName, String stringValue) {
        Method access = null;

        for (int i = 0; i < array.length; i++) {
            if (access == null) {
                access = Introspector.INSTANCE.getDescriptor(array[i])
                                              .getProperty(propertyName)
                                              .getAccessorMethod();
            }

            try {
                if ((access.invoke(array[i], null) + "").equals(stringValue)) {
                    return array[i];
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("No Object matching " + propertyName + "=" + stringValue);
    }

    private SourcesPropertyChangeEvents getBindableFromCollectionWithProperty(
        Collection collection, String propertyName, String stringValue) {
        Method access = null;

        for (Iterator it = collection.iterator(); it.hasNext();) {
            SourcesPropertyChangeEvents object = (SourcesPropertyChangeEvents) it.next();

            if (access == null) {
                access = Introspector.INSTANCE.getDescriptor(object)
                                              .getProperty(propertyName)
                                              .getAccessorMethod();
            }

            try {
                if ((access.invoke(object, null) + "").equals(stringValue)) {
                    return object;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("No Object matching " + propertyName + "=" + stringValue);
    }

    private SourcesPropertyChangeEvents getDiscriminatedObject(Object collectionOrArray, String descriminator) {
        int equalsIndex = descriminator.indexOf("=");

        if (collectionOrArray instanceof Collection && (equalsIndex == -1)) {
            return this.getBindableAtCollectionIndex((Collection) collectionOrArray, Integer.parseInt(descriminator));
        } else if (collectionOrArray instanceof Collection) {
            return this.getBindableFromCollectionWithProperty(
                (Collection) collectionOrArray, descriminator.substring(0, equalsIndex),
                descriminator.substring(equalsIndex + 1));
        } else if (collectionOrArray instanceof Map) {
            return this.getBindableAtMapKey((Map) collectionOrArray, descriminator);
        } else if (equalsIndex == -1) {
            return ((SourcesPropertyChangeEvents[]) collectionOrArray)[Integer.parseInt(descriminator)];
        } else {
            return getBindableFromArrayWithProperty(
                (SourcesPropertyChangeEvents[]) collectionOrArray, descriminator.substring(0, equalsIndex),
                descriminator.substring(equalsIndex + 1));
        }
    }

    /**
     * A data class containing the relevant data for one half of a binding
     * relationship.
     */
    public static class BindingInstance {
        /**
         * The Object being bound.
         */
        public SourcesPropertyChangeEvents object;

        /**
         * A converter when needed.
         */
        public Converter converter;

        /**
         * The property name being bound.
         */
        public Property property;

        /**
         * A ValidationFeedback object when needed.
         */
        public ValidationFeedback feedback;

        /**
         * A Validator object when needed.
         */
        public Validator validator;
        PropertyChangeListener listener;
        private NestedPropertyChangeListener nestedListener = null;

        private BindingInstance() {
            super();
        }
    }

    static class DefaultPropertyChangeListener implements PropertyChangeListener {
        private BindingInstance instance;
        private BindingInstance target;
        private ValidationException lastException = null;

        DefaultPropertyChangeListener(BindingInstance instance, BindingInstance target) {
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

                        instance.feedback.handleException(propertyChangeEvent.getSource(), ve);
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

            // Adding this to simplify simple toString conversions.
            // TODO add a nice way to register global converter defaults
            if (
                (instance.converter == null) && (target.property.getType() == String.class) &&
                    (instance.property.getType() != String.class)) {
                instance.converter = Converter.TO_STRING_CONVERTER;
            }

            if (instance.converter != null) {
                value = instance.converter.convert(value);
            }

            Object[] args = new Object[1];
            args[0] = value;

            try {
                target.property.getMutatorMethod()
                               .invoke(target.object, args);
            } catch (Exception e) {
                // throw new RuntimeException(
                // "Exception setting property: "+target.property.getName(), e);
                LOGGER.log(Level.ERROR, "Exception setting property: " + target.property.getName() + " on object "+target.object, e);
            }
        }

        @Override
        public String toString() {
            return "[Listener on : " + this.instance.object + " ] ";
        }
    }

    private class NestedPropertyChangeListener implements PropertyChangeListener {
        SourcesPropertyChangeEvents sourceObject;
        BindingInstance target;
        String propertyName;
        SourcesPropertyChangeEvents[] parents;
        String[] propertyNames;

        NestedPropertyChangeListener(BindingInstance target, SourcesPropertyChangeEvents sourceObject, String propertyName) {
            this.target = target;
            this.sourceObject = sourceObject;
            this.propertyName = propertyName;
        }

        public void cleanup() {
            for (int i = 0; i < parents.length; i++) {
                parents[i].removePropertyChangeListener(this.propertyNames[i], this);
            }
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if (bound) {
                unbind();
                bound = true;
            }

            BindingInstance newInstance = createBindingInstance(sourceObject, propertyName);
            target.object = newInstance.object;
            target.nestedListener = newInstance.nestedListener;
            target.nestedListener.target = target;
            target.property = newInstance.property;

            if (lastSet == Boolean.TRUE) {
                setLeft();
            } else if (lastSet == Boolean.FALSE) {
                setRight();
            }

            if (bound) {
                bind();
            }
        }

        public void setup() {
            for (int i = 0; i < parents.length; i++) {
                parents[i].addPropertyChangeListener(this.propertyNames[i], this);
            }
        }
    }
}
