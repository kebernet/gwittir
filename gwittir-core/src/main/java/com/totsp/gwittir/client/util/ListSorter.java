/*
 * ListSorter.java
 *
 * Created on July 28, 2007, 5:24 PM
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
package com.totsp.gwittir.client.util;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Property;

import java.util.HashMap;
import java.util.List;


/**
 *
 * @author cooper
 */
public class ListSorter {
    private static final Introspector INTRO = (Introspector) GWT.create(Introspector.class);

    /** Creates a new instance of ListSorter */
    private ListSorter() {
    }

    /**
     * Performs a selection sort on a List of Introspectables
     */
    public static void sortOnProperty(List list, String propertyName,
        boolean ascending) throws Exception {
        Class currentClass = null;
        ;

        Property p = null;
        HashMap cache = new HashMap();

        for(int i = 0; i < (list.size() - 1); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                Object o1 = list.get(i);

                if(currentClass != INTRO.resolveClass(o1)) {
                    p = (Property) cache.get(INTRO.resolveClass(o1));

                    if(p == null) {
                        p = INTRO.getDescriptor(o1).getProperty(propertyName);
                    }
                }

                Comparable oc1 = (Comparable) p.getAccessMethod()
                                               .invoke(o1, null);

                Object o2 = list.get(j);

                if(currentClass != INTRO.resolveClass(o2)) {
                    p = (Property) cache.get(INTRO.resolveClass(o2));

                    if(p == null) {
                        p = INTRO.getDescriptor(o2).getProperty(propertyName);
                    }
                }

                Comparable oc2 = (Comparable) p.getAccessMethod()
                                               .invoke(o2, null);

                if(ascending) {
                    if((oc1 != oc2)
                            && (
                                (oc2 == null)
                                || (
                                    (oc1 != null) && (oc2 != null)
                                    && (oc2.compareTo(oc1) < 0)
                                )
                            )) { //swap
                        list.set(i, o2);
                        list.set(j, o1);
                    }
                } else {
                    if((oc1 != oc2)
                            && (
                                (oc1 == null)
                                || (
                                    (oc1 != null) && (oc2 != null)
                                    && (oc1.compareTo(oc2) < 0)
                                )
                            )) { //swap
                        list.set(i, o2);
                        list.set(j, o1);
                    }
                }
            }
        }
    }
}
