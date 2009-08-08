/*
 * ToStringRenderer.java
 *
 * Created on April 12, 2007, 12:57 PM
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


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class ToStringRenderer<T> implements Renderer<T, String> {
    public static final ToStringRenderer<Object> INSTANCE = new ToStringRenderer<Object>();

    /** Creates a new instance of ToStringRenderer */
    private ToStringRenderer() {
        super();
    }

    public String render(Object o) {
        return (o == null) ? ""
                           : o.toString();
    }
}
