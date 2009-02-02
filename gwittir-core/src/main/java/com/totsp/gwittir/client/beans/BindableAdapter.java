/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.beans;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.Widget;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author kebernet
 */
public abstract class BindableAdapter implements SourcesPropertyChangeEvents, SelfDescribed {
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Object watched;
    private Map<String, Object> values = new HashMap<String, Object>();
    private Property[] properties;
    private BeanDescriptor descriptor;

    public BindableAdapter(Object watched) {
        this.watched = watched;
        this.properties = Introspector.INSTANCE.getDescriptor(watched)
                                               .getProperties();
        initValues();
        initListener();
    }

    public BindableAdapter(Object watched, String... properties) {
        this.watched = watched;
        this.properties = new Property[properties.length];

        BeanDescriptor bd = Introspector.INSTANCE.getDescriptor(watched);

        for (int i = 0; i < properties.length; i++) {
            this.properties[i] = bd.getProperty(properties[i]);
        }

        initValues();
        initListener();
    }

    private void initValues() {
        try {
            for (Property p : this.properties) {
                if (p.getAccessorMethod() != null) {
                    values.put(p.getName(),
                        p.getAccessorMethod().invoke(this.watched, null));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        for (Property p : properties) {
            if (p.getAccessorMethod() == null) {
                continue;
            }

            try {
                Object old = this.values.get(p.getName());
                Object cur = p.getAccessorMethod().invoke(this.watched, null);
                this.propertyChangeSupport.firePropertyChange(p.getName(), old,
                    cur);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    protected abstract void initListener();
    protected abstract void stopListener();

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, l);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return propertyChangeSupport.getPropertyChangeListeners();
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName,
        PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, l);
    }

    public BeanDescriptor __descriptor() {
        if (this.descriptor == null) { //No lazy thread issues in the browser;
            final Property[] wrappedProps = new Property[this.properties.length];

            for (int i = 0; i < this.properties.length; i++) {
                wrappedProps[i] = new Property(this.properties[i].getName(),
                        this.properties[i].getType(),
                        (this.properties[i].getAccessorMethod() != null)
                        ? new MethodWrapper(
                            this.properties[i].getAccessorMethod()) : null,
                        (this.properties[i].getMutatorMethod() != null)
                        ? new MethodWrapper(
                            this.properties[i].getAccessorMethod()) : null);
            }

            this.descriptor = new BeanDescriptor() {
                        public Property[] getProperties() {
                            return wrappedProps;
                        }

                        public Property getProperty(String name) {
                            for (Property p : wrappedProps) {
                                if (p.getName().equals(name)) {
                                    return p;
                                }
                            }
                            return null;
                        }
                    };
        }
        return this.descriptor;
    }

    private static class MethodWrapper implements Method {
        private Method internal;

        MethodWrapper(Method internal) {
            this.internal = internal;
        }

        public String getName() {
            return internal.getName();
        }

        public Object invoke(Object target, Object[] args)
            throws Exception {
            return internal.invoke(((BindableAdapter) target).watched, args);
        }
    }
}
