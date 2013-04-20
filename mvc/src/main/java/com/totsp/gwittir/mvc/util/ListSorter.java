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
package com.totsp.gwittir.mvc.util;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.introspection.Introspector;
import com.totsp.gwittir.mvc.beans.Property;

import java.util.HashMap;
import java.util.List;


/** A utility class for sorting lists of Introspectables.
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
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
        
        Property p = null;
        HashMap<Class, Property> cache = new HashMap<Class, Property>();

        for(int i = 0; i < (list.size() - 1); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                Object o1 = list.get(i);
                Class o1Class = INTRO.resolveClass(o1);
                if(currentClass != o1Class) {
                    p = cache.get(o1Class);

                    if(p == null) {
                        p = INTRO.getDescriptor(o1).getProperty(propertyName);
                        cache.put( o1Class, p);
                    }
                    currentClass = o1Class;
                }

                Comparable oc1 = (Comparable) p.getAccessorMethod()
                                               .invoke(o1, null);

                Object o2 = list.get(j);
                Class o2Class = INTRO.resolveClass(o2);
                if(currentClass != o2Class) {
                    p = cache.get(o2Class);

                    if(p == null) {
                        p = INTRO.getDescriptor(o2).getProperty(propertyName);
                        cache.put( o2Class, p);
                    }
                    currentClass = o2Class;
                }

                Comparable oc2 = (Comparable) p.getAccessorMethod()
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
