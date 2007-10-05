/*
 * AbstractTableWidget.java
 *
 * Created on August 7, 2007, 8:07 PM
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

import com.totsp.gwittir.client.beans.Bindable;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Property;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public abstract class AbstractTableWidget extends AbstractBoundWidget {
    protected BoundWidgetTypeFactory factory;

    /** Creates a new instance of AbstractTableWidget */
    public AbstractTableWidget() {
    }

    protected BoundWidget createWidget(Binding parent, Field field,
        Bindable target) {
        final BoundWidget widget;
        Binding binding;

        if(field.getCellProvider() != null) {
            widget = field.getCellProvider().get();
        } else {
            final Property p = Introspector.INSTANCE.getDescriptor(target)
                                                    .getProperty(field
                    .getPropertyName());
            widget = this.factory.getWidgetProvider(field.getPropertyName(),
                    p.getType()).get();

            // TODO Figure out some way to make this read only.
        }

        binding = new Binding(widget, "value", field.getValidator(),
                field.getFeedback(), target, field.getPropertyName(), null, null);
        widget.setModel(this.getValue());

        if(field.getRenderer() != null) {
            widget.setRenderer(field.getRenderer());
        }

        if(field.getComparator() != null) {
            widget.setComparator(field.getComparator());
        }

        parent.getChildren().add(binding);

        return widget;
    }
}
