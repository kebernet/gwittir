/* This library is free software; you can redistribute it and/or
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

import com.totsp.gwittir.client.util.EqualsHashCodeBean;
import com.totsp.gwittir.client.util.ToStringBean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 *
DOCUMENT ME!
 *
 * @author ccollins
 */
public abstract class AbstractModelBean implements Bindable {
    protected final transient PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private final transient EqualsHashCodeBean equalsHash = new EqualsHashCodeBean(this);
    private final transient ToStringBean toString = new ToStringBean(this);

    public AbstractModelBean() {
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.changeSupport.getPropertyChangeListeners();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        this.changeSupport.addPropertyChangeListener(propertyName, l);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        this.changeSupport.addPropertyChangeListener(l);
    }

    @Override
    public boolean equals(Object obj) {
        return this.equalsHash.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.equalsHash.hashCode();
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        this.changeSupport.removePropertyChangeListener(propertyName, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        this.changeSupport.removePropertyChangeListener(l);
    }

    @Override
    public String toString() {
        return this.toString.toString();
    }
}
