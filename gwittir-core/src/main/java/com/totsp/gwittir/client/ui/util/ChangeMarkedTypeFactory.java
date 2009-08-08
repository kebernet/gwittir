/*
 * ChangeMarkedTypeFactory.java
 *
 * Created on October 25, 2007, 2:36 PM
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
package com.totsp.gwittir.client.ui.util;

import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.ui.BoundWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author cooper
 */
public class ChangeMarkedTypeFactory extends BoundWidgetTypeFactory {
    HashMap widgetLookup = new HashMap(); /*<Bindable, ArrayList<ChangeMarkListener>>*/
    private boolean marking = false;

    public ChangeMarkedTypeFactory() {
        super(true);
    }

    public void setMarking(boolean marking) {
        this.marking = marking;
    }

    public boolean isMarking() {
        return marking;
    }

    public BoundWidgetProvider getWidgetProvider(Class type) {
        BoundWidgetProvider retValue;

        retValue = new BoundWidgetProviderWrapper(super.getWidgetProvider(type), this);

        return retValue;
    }

    public BoundWidgetProvider getWidgetProvider(String propertyName, Class type) {
        BoundWidgetProvider retValue;

        retValue = new BoundWidgetProviderWrapper(super.getWidgetProvider(propertyName, type), this);

        return retValue;
    }

    public boolean hasBeenEdited(Object o) {
        List widgets = (List) this.widgetLookup.get(o);

        return !((widgets == null) || (widgets.size() == 0));
    }

    public void reset(Object o) {
        List widgets = (List) this.widgetLookup.get(o);

        if ((widgets == null) || (widgets.size() == 0)) {
            return;
        }

        for (Iterator it = new ArrayList(widgets).iterator(); it.hasNext();) {
            Widget widget = (Widget) it.next();
            ChangeMarkerListener.hideMarker(widget);
            widgets.remove(widget);
        }
    }

    private static class BoundWidgetProviderWrapper implements BoundWidgetProvider {
        BoundWidgetProvider p;
        ChangeMarkedTypeFactory factory;

        BoundWidgetProviderWrapper(BoundWidgetProvider p, ChangeMarkedTypeFactory factory) {
            this.p = p;
            this.factory = factory;
        }

        public BoundWidget get() {
            BoundWidget w = p.get();
            w.addPropertyChangeListener(new ChangeMarkerListener(this.factory));

            return w;
        }
    }
}
