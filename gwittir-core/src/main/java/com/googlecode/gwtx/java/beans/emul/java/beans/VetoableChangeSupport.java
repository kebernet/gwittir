/*
 * Copyright 2007 Sandy McArthur, Jr.
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This is a utility class that can be used by beans that support constrained
 * properties.
 *
 * <p>
 * Note: The emulated version differs from the JDK version in that it is
 * <em>not</em> Serializable.
 * </p>
 */
public class VetoableChangeSupport {
    /*
     * This file is based on code from the Apache Harmony Project.
     * http://svn.apache.org/repos/asf/harmony/enhanced/classlib/trunk/modules/beans/src/main/java/java/beans/VetoableChangeSupport.java
     *
     * This file has been siginificantly modified for the target enviroment of a
     * browser but the Harmony layout and structure has been kept to try to help
     * make future re-syncs easier.
     */

    private transient Object sourceBean;

    private transient List/*<VetoableChangeListener>*/ allVetoableChangeListeners = new ArrayList/*<VetoableChangeListener>*/();

    private transient Map/*<String, List<VetoableChangeListener>>*/ selectedVetoableChangeListeners = new HashMap/*<String, List<VetoableChangeListener>>*/();

    public VetoableChangeSupport(Object sourceBean) {
        if (sourceBean == null) {
            throw new NullPointerException();
        }
        this.sourceBean = sourceBean;
    }

    public void fireVetoableChange(String propertyName, Object oldValue,
            Object newValue) throws PropertyVetoException {
        PropertyChangeEvent event = createPropertyChangeEvent(propertyName,
                oldValue, newValue);
        doFirePropertyChange(event);
    }

    public synchronized void removeVetoableChangeListener(String propertyName,
            VetoableChangeListener listener) {
        if ((propertyName != null) && (listener != null)) {
            List/*<VetoableChangeListener>*/ listeners =
                (List)selectedVetoableChangeListeners.get(propertyName);

            if (listeners != null) {
                listeners.remove(listener);
            }
        }
    }

    public synchronized void addVetoableChangeListener(String propertyName,
            VetoableChangeListener listener) {
        if (propertyName != null && listener != null) {
            List/*<VetoableChangeListener>*/ listeners =
                (List)selectedVetoableChangeListeners.get(propertyName);

            if (listeners == null) {
                listeners = new ArrayList/*<VetoableChangeListener>*/();
                selectedVetoableChangeListeners.put(propertyName, listeners);
            }

            listeners.add(listener);
        }
    }

    public synchronized VetoableChangeListener[] getVetoableChangeListeners(
            String propertyName) {
        List/*<VetoableChangeListener>*/ listeners = null;

        if (propertyName != null) {
            listeners = (List)selectedVetoableChangeListeners.get(propertyName);
        }

        return (listeners == null) ? new VetoableChangeListener[] {}
                : getAsVetoableChangeListenerArray(listeners);

    }

    public void fireVetoableChange(String propertyName, boolean oldValue,
            boolean newValue) throws PropertyVetoException {
        PropertyChangeEvent event = createPropertyChangeEvent(propertyName,
                oldValue, newValue);
        doFirePropertyChange(event);
    }

    public void fireVetoableChange(String propertyName, int oldValue,
            int newValue) throws PropertyVetoException {
        PropertyChangeEvent event = createPropertyChangeEvent(propertyName,
                oldValue, newValue);
        doFirePropertyChange(event);
    }

    public synchronized boolean hasListeners(String propertyName) {
        boolean result = !allVetoableChangeListeners.isEmpty();
        if (!result && propertyName != null) {
            List/*<VetoableChangeListener>*/ listeners =
                (List)selectedVetoableChangeListeners.get(propertyName);
            if (listeners != null) {
                result = !listeners.isEmpty();
            }
        }
        return result;
    }

    public synchronized void removeVetoableChangeListener(
            VetoableChangeListener listener) {
        if (listener != null) {
            Iterator/*<VetoableChangeListener>*/ iterator =
                allVetoableChangeListeners.iterator();
            while (iterator.hasNext()) {
                VetoableChangeListener pcl = (VetoableChangeListener)iterator.next();
                if (pcl == listener) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public synchronized void addVetoableChangeListener(
            VetoableChangeListener listener) {
        if (listener != null) {
            allVetoableChangeListeners.add(listener);
        }
    }

    public synchronized VetoableChangeListener[] getVetoableChangeListeners() {
        List/*<VetoableChangeListener>*/ result =
            new ArrayList/*<VetoableChangeListener>*/(allVetoableChangeListeners);

        Iterator/*<String>*/ keysIterator = selectedVetoableChangeListeners
                .keySet().iterator();
        while (keysIterator.hasNext()) {
            String propertyName = (String)keysIterator.next();

            List/*<VetoableChangeListener>*/ selectedListeners =
                (List)selectedVetoableChangeListeners.get(propertyName);
            if (selectedListeners != null) {
              for (Iterator it = selectedListeners.iterator(); it.hasNext();) {
                VetoableChangeListener listener = (VetoableChangeListener)it.next();
                result.add(new VetoableChangeListenerProxy(propertyName,
                    listener));
              }

            }

        }

        return getAsVetoableChangeListenerArray(result);
    }

    public void fireVetoableChange(PropertyChangeEvent event)
            throws PropertyVetoException {
        doFirePropertyChange(event);
    }

    private PropertyChangeEvent createPropertyChangeEvent(String propertyName,
            Object oldValue, Object newValue) {
        return new PropertyChangeEvent(sourceBean, propertyName, oldValue,
                newValue);
    }

    private PropertyChangeEvent createPropertyChangeEvent(String propertyName,
            boolean oldValue, boolean newValue) {
        return new PropertyChangeEvent(sourceBean, propertyName,
            Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
    }

    private PropertyChangeEvent createPropertyChangeEvent(String propertyName,
            int oldValue, int newValue) {
        return new PropertyChangeEvent(sourceBean, propertyName, new Integer(
                oldValue), new Integer(newValue));
    }

    private void doFirePropertyChange(PropertyChangeEvent event)
            throws PropertyVetoException {
        String propName = event.getPropertyName();
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && oldValue != null && newValue.equals(oldValue)) {
            return;
        }

        /* Take note of who we are going to notify (and potentially un-notify) */

        List allListeners = new ArrayList();
        // Listeners to all property change events
        allListeners.addAll(allVetoableChangeListeners);

        // Listens to a given property change
        List/*<VetoableChangeListener>*/ listeners =
            (List)selectedVetoableChangeListeners.get(event.getPropertyName());
        if (listeners != null) {
            allListeners.addAll(listeners);
        }

        try {
            Iterator iter = allListeners.iterator();
            while (iter.hasNext()) {
              VetoableChangeListener listener =
                  (VetoableChangeListener)iter.next();
              listener.vetoableChange(event);
            }
        } catch (PropertyVetoException pve) {
            // Tell them we have changed it back
            PropertyChangeEvent revertEvent = createPropertyChangeEvent(
                    propName, newValue, oldValue);
            Iterator iter = allListeners.iterator();
            while (iter.hasNext()) {
                VetoableChangeListener listener =
                    (VetoableChangeListener)iter.next();
                try {
                    listener.vetoableChange(revertEvent);
                } catch (PropertyVetoException ignored) {
                }
            }
            throw pve;
        }
    }

    private static VetoableChangeListener[] getAsVetoableChangeListenerArray(
            List/*<VetoableChangeListener>*/ listeners) {
        VetoableChangeListener[] arrayResult = new VetoableChangeListener[listeners.size()];

        Iterator/*<VetoableChangeListener>*/ iter = listeners.iterator();
        for (int i = 0; i < arrayResult.length; ++i) {
            arrayResult[i] = (VetoableChangeListener) iter.next();
        }

        return arrayResult;
    }
}
