/*
 * BoundWidget.java
 *
 * Created on April 12, 2007, 12:42 PM
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
package com.totsp.gwittir.mvc.ui;

import com.google.gwt.user.client.ui.SourcesChangeEvents;

import com.totsp.gwittir.mvc.action.Action;

import com.totsp.gwittir.mvc.beans.SourcesPropertyChangeEvents;
import com.totsp.gwittir.introspection.Introspectable;
import java.util.Comparator;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
@Introspectable
public interface BoundWidget<T> extends SourcesChangeEvents, SourcesPropertyChangeEvents {
    public Action<BoundWidget<T>> getAction();

    public Comparator<BoundWidget<T>> getComparator();

    public Object getModel();

    public T getValue();

    public void setAction(Action<BoundWidget<T>> action);

    public void setComparator(Comparator<BoundWidget<T>> comparator);

    public void setModel(Object model);
    
    public void setValue(T value);
}
