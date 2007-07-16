/*
 * SimpleComparator.java
 *
 * Created on July 5, 2007, 6:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.ui;

import java.util.Comparator;


/**
 *
 * @author cooper
 */
public class SimpleComparator implements Comparator {
    /** Creates a new instance of SimpleComparator */
    public SimpleComparator() {
    }

    public int compare(Object o1, Object o2) {
        if((o1 == o2) || ((o1 != null) && (o2 != null) && o1.equals(o2))) {
            return 0;
        } else if((o1 != null) && (o2 != null)) {
            return o1.toString().compareTo(o2.toString());
        } else if((o1 != null) && (o2 == null)) {
            return +1;
        } else {
            return -1;
        }
    }
}
