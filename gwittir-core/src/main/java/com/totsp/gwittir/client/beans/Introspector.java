/*
 * Introspector.java
 *
 * Created on July 15, 2007, 1:44 PM
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
package com.totsp.gwittir.client.beans;

import com.google.gwt.core.client.GWT;


/**
 * The Introspector allows property inspection of Bean-Type classes marked with
 * the Introspectable interface.
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface Introspector {
    /**
     * The Singleton instance of the Introspector you should use from your classes.
     */
    public static final Introspector INSTANCE = (Introspector) GWT.create(Introspector.class);

    /**
     * Gets the BeanDescriptor for an object.
     * @param object Object to introspect.
     * @return BeanDescriptor for the object.
     */
    public BeanDescriptor getDescriptor(Object object);

    /**
     * Resolves an object to its most specific Introspectable type.
     * @param instance Instance of an object
     * @return Class literal for the most specific Introspectable type.
     */
    public Class resolveClass(Object instance);
}
