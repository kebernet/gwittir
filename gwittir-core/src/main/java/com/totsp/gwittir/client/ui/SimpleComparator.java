/*
 * SimpleComparator.java
 *
 * Created on July 5, 2007, 6:17 PM
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
package com.totsp.gwittir.client.ui;

import java.io.Serializable;
import java.util.Comparator;


/**
 *
 * @author cooper
 */
public class SimpleComparator implements Comparator, Serializable {
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
