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
package com.totsp.gwittir.mvc.ui;

import java.io.Serializable;
import java.util.Comparator;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class SimpleComparator implements Comparator, Serializable {
    
    
    public static final SimpleComparator INSTANCE = new SimpleComparator();
    
    /** Creates a new instance of SimpleComparator */
    private  SimpleComparator() {
    }
    
    public int compare(Object o1, Object o2) {
        if( o1 instanceof Comparable && o2 instanceof Comparable ){
            return ((Comparable) o1).compareTo( o2 );
        }
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
