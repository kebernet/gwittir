/*
 * TypeSelectorProvider.java
 *
 * Created on August 6, 2007, 8:12 PM
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
package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.ListBox;
import com.totsp.gwittir.client.ui.Renderer;
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;
import com.totsp.gwittir.example.client.remote.Services;
import com.totsp.gwittir.example.client.remote.TypeLookup;

import java.util.Comparator;
import java.util.List;


/**
 * 
DOCUMENT ME!
 *
 * @author cooper
 */
public class TypeSelectorProvider implements BoundWidgetProvider {
    public static final TypeSelectorProvider INSTANCE = new TypeSelectorProvider();

/** Creates a new instance of TypeSelectorProvider */
    private TypeSelectorProvider() {
    }

    public BoundWidget get() {
        final ListBox box = new ListBox();
        box.setRenderer(new Renderer() {
                public Object render(Object o) {
                    return ((TypeLookup) o).name;
                }
            });
        box.setComparator(new Comparator() {
                public int compare(Object o, Object c) {
                    return ((TypeLookup) o).id.compareTo(((TypeLookup) c).id);
                }
            });
        box.setMultipleSelect(false);

        Services.CONTACTS.getTypeLookups(new AsyncCallback() {
                public void onSuccess(Object result) {
                    box.setOptions((List) result);
                }

                public void onFailure(Throwable caught) {
                    Window.alert("Error getting types - " +
                        caught.getMessage());

                    return;
                }
            });

        return box;
    }
}
