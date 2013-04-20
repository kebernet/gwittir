/*
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
package com.totsp.gwittir.mvc.beans.adapters;

import com.totsp.gwittir.mvc.beans.BeanDescriptor;
import com.totsp.gwittir.introspection.Introspector;
import com.totsp.gwittir.introspection.Method;
import com.totsp.gwittir.mvc.beans.Property;
import com.totsp.gwittir.mvc.beans.SelfDescribed;
import com.totsp.gwittir.mvc.beans.SourcesPropertyChangeEvents;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.HashMap;
import java.util.Map;


/**
 * This is an abstract class for creating Adapters for @Introspectable types that
 * do not source PropertyChangeEvents.
 * 
 * @author <a href="mailto:kebernet@gmail.com">Robert "kebernet" Cooper</a>
 */
public abstract class BindableAdapter implements SourcesPropertyChangeEvents, SelfDescribed {
    private BeanDescriptor descriptor;
    private Map<String, Object> values = new HashMap<String, Object>();
    private Object watched;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Property[] properties;

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

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return propertyChangeSupport.getPropertyChangeListeners();
    }

    /**
     * @return the watched
     */
    public Object getWatched() {
        return watched;
    }

    public BeanDescriptor __descriptor() {
        if (this.descriptor == null) { //No lazy thread issues in the browser;

            final Property[] wrappedProps = new Property[this.properties.length];

            for (int i = 0; i < this.properties.length; i++) {
                wrappedProps[i] = new Property(
                        this.properties[i].getName(),
                        this.properties[i].getType(),
                        (this.properties[i].getAccessorMethod() != null) ? new MethodWrapper(this.properties[i].getAccessorMethod()) : null,
                        (this.properties[i].getMutatorMethod() != null) ? new MethodWrapper(this.properties[i].getAccessorMethod()) : null);
            }

            this.descriptor = new BeanDescriptor() {
                        public Property[] getProperties() {
                            return wrappedProps;
                        }

                        public Property getProperty(String name) {
                            for (Property p : wrappedProps) {
                                if (p.getName()
                                         .equals(name)) {
                                    return p;
                                }
                            }

                            return null;
                        }
                    };
        }

        return this.descriptor;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, l);
    }

    public void update() {
        for (Property p : properties) {
            if (p.getAccessorMethod() == null) {
                continue;
            }

            try {
                Object old = this.values.get(p.getName());
                Object cur = p.getAccessorMethod()
                              .invoke(
                        this.getWatched(),
                        null);
                this.propertyChangeSupport.firePropertyChange(
                    p.getName(),
                    old,
                    cur);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    protected abstract void initListener();

    protected void initValues() {
        try {
            for (Property p : this.properties) {
                if (p.getAccessorMethod() != null) {
                    values.put(
                        p.getName(),
                        p.getAccessorMethod().invoke(
                            this.getWatched(),
                            null));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void stopListener();

    protected static class MethodWrapper implements Method {
        private Method internal;

        MethodWrapper(Method internal) {
            this.internal = internal;
        }

        public String getName() {
            return internal.getName();
        }

        public Object invoke(Object target, Object[] args)
            throws Exception {
            return internal.invoke(
                ((BindableAdapter) target).getWatched(),
                args);
        }
    }
}
