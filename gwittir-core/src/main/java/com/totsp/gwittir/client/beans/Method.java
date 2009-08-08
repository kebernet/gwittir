/*
 * Method.java
 *
 * Created on July 15, 2007, 1:42 PM
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


/** A Gwittir ananlogue to java.lang.reflect.Method.
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface Method {
    /**
     * Returns the name of the method
     * @return String value name of the method
     */
    public String getName();

    /**
     * Ivokes the method.
     * @param target The target object on which to invoke the method
     * @param args Array of arguments to pass to the method, or null for no arguments.
     * @throws java.lang.Exception Any exception thrown by the method.
     * @return The return value for the method. This will always be null for void methods.
     */
    public abstract Object invoke(Object target, Object[] args)
        throws Exception;
}
