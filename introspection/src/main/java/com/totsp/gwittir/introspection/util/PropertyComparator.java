package com.totsp.gwittir.introspection.util;

import com.totsp.gwittir.introspection.Introspector;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: keber_000
 * Date: 5/6/13
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyComparator implements Comparator<Object> {

    private final String propertyName;
    private final boolean ascending;

    public PropertyComparator(String propertyName, boolean ascending) {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    public int compare(Object o1, Object o2) {
        if(o1 == null && o2 != null){
            return (!ascending ? -1 : 1) * -1;
        } else if(o1 != null && o2 == null){
            return (!ascending ? -1 : 1) * 1;
        } else {
            try {
                Comparable v1 = (Comparable) Introspector.INSTANCE.getDescriptor(o1).getProperty(propertyName).getAccessorMethod().invoke(o1, null);
                Comparable v2 = (Comparable) Introspector.INSTANCE.getDescriptor(o1).getProperty(propertyName).getAccessorMethod().invoke(o2, null);
                return (!ascending ? -1 : 1) * v1.compareTo(v2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
