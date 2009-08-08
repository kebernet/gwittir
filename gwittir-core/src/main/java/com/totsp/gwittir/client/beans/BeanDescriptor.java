/*
 * BeanDescriptor.java
 *
 * Created on July 15, 2007, 1:55 PM
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


/** A container for the properties of a Bean-type class.
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface BeanDescriptor {
    /**
     * Returns the Property instances associated with the class.
     * @return Array of Properties for the class.
     */
    Property[] getProperties();

    /**
     * Gets a property instance by name, or null if unavailable.
     * @param name Name of the property to get.
     * @return Property instance or null
     */
    Property getProperty(String name);
}
