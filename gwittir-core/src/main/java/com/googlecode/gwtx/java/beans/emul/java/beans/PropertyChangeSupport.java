/*
 * Copyright 2007 Sandy McArthur, Jr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/*
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
import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * This is a utility class that can be used by beans that support bound
 * properties.
 *
 * <p>
 * Note: The emulated version differs from the JDK version in that it is
 * <em>not</em> Serializable.
 * </p>
 */
public class PropertyChangeSupport {
    /*
     * This file is based on code from the Apache Harmony Project.
     * http://svn.apache.org/repos/asf/harmony/enhanced/classlib/trunk/modules/beans/src/main/java/java/beans/PropertyChangeSupport.java
     *
     * This file has been siginificantly modified for the target enviroment of a
     * browser but the Harmony layout and structure has been kept to try to help
     * make future re-syncs easier.
     */

    private transient Object sourceBean;

    private transient List/*<PropertyChangeListener>*/ allPropertiesChangeListeners =
            new ArrayList/*<PropertyChangeListener>*/();

    private transient Map/*<String, List<PropertyChangeListener>>*/
            selectedPropertiesChangeListeners =
            new HashMap/*<String, List<PropertyChangeListener>>*/();

    public PropertyChangeSupport(final Object sourceBean) {
        if (sourceBean == null) {
            throw new NullPointerException();
        }
        this.sourceBean = sourceBean;
    }

    public void firePropertyChange(final String propertyName, final Object oldValue,
            final Object newValue) {
        final PropertyChangeEvent event = createPropertyChangeEvent(propertyName,
                oldValue, newValue);
        doFirePropertyChange(event);
    }

    public void fireIndexedPropertyChange(String propertyName, int index,
            Object oldValue, Object newValue) {

        // nulls and equals check done in doFire...
        doFirePropertyChange(new IndexedPropertyChangeEvent(sourceBean,
                propertyName, oldValue, newValue, index));
    }

    public synchronized void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        if ((propertyName != null) && (listener != null)) {
            List/*<PropertyChangeListener>*/ listeners =
                (List)selectedPropertiesChangeListeners.get(propertyName);

            if (listeners != null) {
                listeners.remove(listener);
            }
        }
    }

    public synchronized void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        if ((listener != null) && (propertyName != null)) {
            List/*<PropertyChangeListener>*/ listeners =
                (List)selectedPropertiesChangeListeners.get(propertyName);

            if (listeners == null) {
                listeners = new ArrayList/*<PropertyChangeListener>*/();
                selectedPropertiesChangeListeners.put(propertyName, listeners);
            }

            // RI compatibility
            if (listener instanceof PropertyChangeListenerProxy) {
                PropertyChangeListenerProxy proxy =
                        (PropertyChangeListenerProxy) listener;

                listeners.add(new PropertyChangeListenerProxy(
                        proxy.getPropertyName(),
                        (PropertyChangeListener) proxy.getListener()));
            } else {
                listeners.add(listener);
            }
        }
    }

    public synchronized PropertyChangeListener[] getPropertyChangeListeners(
            String propertyName) {
        List/*<PropertyChangeListener>*/ listeners = null;

        if (propertyName != null) {
            listeners = (List)selectedPropertiesChangeListeners.get(propertyName);
        }

        if (listeners == null) {
            return new PropertyChangeListener[] {};
        } else {
          PropertyChangeListener[] changeListeners =
              new PropertyChangeListener[listeners.size()];
          Iterator iter = listeners.iterator();
          for (int i=0; i < changeListeners.length; i++) {
              changeListeners[i] = (PropertyChangeListener)iter.next();
          }
          return changeListeners;
        }
    }

    public void firePropertyChange(String propertyName, boolean oldValue,
            boolean newValue) {
        PropertyChangeEvent event = createPropertyChangeEvent(propertyName,
                oldValue, newValue);
        doFirePropertyChange(event);
    }

    public void fireIndexedPropertyChange(String propertyName, int index,
            boolean oldValue, boolean newValue) {

        if (oldValue != newValue) {
            fireIndexedPropertyChange(propertyName, index, Boolean
                    .valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }

    public void firePropertyChange(String propertyName, int oldValue,
            int newValue) {
        PropertyChangeEvent event = createPropertyChangeEvent(propertyName,
                oldValue, newValue);
        doFirePropertyChange(event);
    }

    public void fireIndexedPropertyChange(String propertyName, int index,
            int oldValue, int newValue) {

        if (oldValue != newValue) {
            fireIndexedPropertyChange(propertyName, index,
                    new Integer(oldValue), new Integer(newValue));
        }
    }

    public synchronized boolean hasListeners(String propertyName) {
        boolean result = allPropertiesChangeListeners.size() > 0;
        if (!result && (propertyName != null)) {
            List/*<PropertyChangeListener>*/ listeners =
                (List)selectedPropertiesChangeListeners.get(propertyName);
            if (listeners != null) {
                result = listeners.size() > 0;
            }
        }
        return result;
    }

    public synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        if (listener != null) {
            if (listener instanceof PropertyChangeListenerProxy) {
                String name = ((PropertyChangeListenerProxy) listener)
                        .getPropertyName();
                PropertyChangeListener lst = (PropertyChangeListener)
                        ((PropertyChangeListenerProxy) listener).getListener();

                removePropertyChangeListener(name, lst);
            } else {
                allPropertiesChangeListeners.remove(listener);
            }
        }
    }

    public synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        if (listener != null) {
            if (listener instanceof PropertyChangeListenerProxy) {
                String name = ((PropertyChangeListenerProxy) listener)
                        .getPropertyName();
                PropertyChangeListener lst = (PropertyChangeListener)
                        ((PropertyChangeListenerProxy) listener).getListener();
                addPropertyChangeListener(name, lst);
            } else {
                allPropertiesChangeListeners.add(listener);
            }
        }
    }

    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        ArrayList/*<PropertyChangeListener>*/ result =
                new ArrayList/*<PropertyChangeListener>*/(
                        allPropertiesChangeListeners);

      for (Iterator it = selectedPropertiesChangeListeners.keySet().iterator(); it.hasNext();)
      {
        String propertyName = (String)it.next();
        List/*<PropertyChangeListener>*/ selectedListeners =
            (List)selectedPropertiesChangeListeners.get(propertyName);

        if (selectedListeners != null) {
          for (Iterator it1 = selectedListeners.iterator(); it1.hasNext();) {
            PropertyChangeListener listener = (PropertyChangeListener)it1.next();
            result.add(new PropertyChangeListenerProxy(propertyName,
                listener));
          }
        }
      }

      PropertyChangeListener[] changeListeners = new PropertyChangeListener[result.size()];
      Iterator iter = result.iterator();
      for (int i=0; i < changeListeners.length; i++) {
          changeListeners[i] = (PropertyChangeListener)iter.next();
      }
      return changeListeners;
    }

    public void firePropertyChange(PropertyChangeEvent event) {
        doFirePropertyChange(event);
    }

    private PropertyChangeEvent createPropertyChangeEvent(String propertyName,
            Object oldValue, Object newValue) {
        return new PropertyChangeEvent(sourceBean, propertyName, oldValue,
                newValue);
    }

    private PropertyChangeEvent createPropertyChangeEvent(String propertyName,
            boolean oldValue, boolean newValue) {
        return new PropertyChangeEvent(sourceBean, propertyName, Boolean.valueOf(oldValue),
                Boolean.valueOf(newValue));
    }

    private PropertyChangeEvent createPropertyChangeEvent(String propertyName,
            int oldValue, int newValue) {
        return new PropertyChangeEvent(sourceBean, propertyName, new Integer(oldValue),
                new Integer(newValue));
    }

    private void doFirePropertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if ((newValue != null) && (oldValue != null)
                && newValue.equals(oldValue)) {
            return;
        }

        /*
         * Copy the listeners collections so they can be modified while we fire
         * events.
         */
        List allListeners = new ArrayList();

        // Listeners to all property change events
        allListeners.addAll(allPropertiesChangeListeners);

        // Listens to a given property change
        List/*<PropertyChangeListener>*/ listeners =
            (List)selectedPropertiesChangeListeners.get(propertyName);
        if (listeners != null) {
            allListeners.addAll(listeners);
        }

        // Fire the listeners
        for (Iterator iter = allListeners.iterator(); iter.hasNext();) {
            PropertyChangeListener listener = (PropertyChangeListener)iter.next();
            listener.propertyChange(event);
        }
    }
}
