/*
 * TypeLookup.java
 *
 * Created on August 2, 2007, 8:27 PM
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
package com.totsp.gwittir.example.client.remote;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * 
DOCUMENT ME!
 *
 * @author cooper
 */
public class TypeLookup implements IsSerializable {
    public Integer id;
    public String name;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TypeLookup: id-" + this.id);
        sb.append(" name-" + this.name);

        return sb.toString();
    }
}
