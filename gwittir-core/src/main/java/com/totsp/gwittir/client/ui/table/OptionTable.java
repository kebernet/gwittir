/*
 * OptionTable.java
 *
 * Created on July 31, 2007, 1:21 PM
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
package com.totsp.gwittir.client.ui.table;

import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This table is a simple extension of the BoundTable, but it changes the
 * names of the "selected" and "value" properties to "value" and "options"
 * respectively. This is inteded to be used as a ListBox-style picker widget.
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class OptionTable extends BoundTable {
    private final PropertyChangeSupport myChanges = new PropertyChangeSupport(this);
    private final PropertyChangeListener globalListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName()
                           .equals("selected")) {
                    PropertyChangeEvent masked = new PropertyChangeEvent(
                            this, "value", evt.getOldValue(), evt.getNewValue());
                    myChanges.firePropertyChange(masked);
                } else if (evt.getPropertyName()
                                  .equals("value")) {
                    PropertyChangeEvent masked = new PropertyChangeEvent(
                            this, "options", evt.getOldValue(), evt.getNewValue());
                    myChanges.firePropertyChange(masked);
                } else {
                    myChanges.firePropertyChange(evt);
                }
            }
        };

    /** Creates a new instance of OptionTable */
    public OptionTable() {
        super();
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of Bound table with the indicated options value.
     * @param masks int value containing the sum of the *_MASK options for the table.
     */
    public OptionTable(int masks) {
        super(masks);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of Bound table with the indicated options value.
     * @param typeFactory A BoundWidget type factory used to create the widgets that appear in the table.
     * @param masks int value containing the sum of the *_MASK options for the table.
     */
    public OptionTable(int masks, BoundWidgetTypeFactory typeFactory) {
        super(masks, typeFactory);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of a table using a Collection as a data set.
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     * @param options A collection containing Bindable objects to render in the table.
     */
    public OptionTable(int masks, Field[] cols, Collection options) {
        super(masks, cols, options);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of a table using a Collection as a data set.
     * @param typeFactory A BoundWidget type factory used to create the widgets that appear in the table.
     * @param options The options to appear in the table.
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     */
    public OptionTable(int masks, BoundWidgetTypeFactory typeFactory, Field[] cols, Collection options) {
        super(masks, typeFactory, cols, options);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of a table using a Collection as a data set.
     *
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     */
    public OptionTable(int masks, Field[] cols) {
        super(masks, cols);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of a table using a Collection as a data set.
     * @param typeFactory A BoundWidget type factory used to create the widgets that appear in the table.
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     */
    public OptionTable(int masks, BoundWidgetTypeFactory typeFactory, Field[] cols) {
        super(masks, typeFactory, cols);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of BoundTable
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     * @param provider Instance of DataProvider to get chunked data from.
     */
    public OptionTable(int masks, Field[] cols, DataProvider provider) {
        super(masks, cols, provider);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    /**
     * Creates a new instance of BoundTable
     * @param typeFactory A BoundWidget type factory used to create the widgets that appear in the table.
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     * @param provider Instance of DataProvider to get chunked data from.
     */
    public OptionTable(int masks, BoundWidgetTypeFactory typeFactory, Field[] cols, DataProvider provider) {
        super(masks, typeFactory, cols, provider);
        super.setStyleName("gwittir-OptionTable");
        super.addPropertyChangeListener(globalListener);
    }

    public void setOptions(Collection options) {
        super.setValue(options);
    }

    public Collection getOptions() {
        return (Collection) super.getValue();
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.myChanges.getPropertyChangeListeners();
    }

    public void setValue(Object value) {
        if (value instanceof List) {
            super.setSelected((List) value);
        } else if (value instanceof Collection) {
            super.setSelected(new ArrayList((Collection) value));
        }
    }

    public Object getValue() {
        return super.getSelected();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        this.myChanges.addPropertyChangeListener(propertyName, l);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        this.myChanges.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        this.myChanges.removePropertyChangeListener(propertyName, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        this.myChanges.removePropertyChangeListener(l);
    }
}
