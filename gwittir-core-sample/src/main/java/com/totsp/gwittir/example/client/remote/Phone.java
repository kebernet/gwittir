/*
 * Phone.java
 *
 * Created on August 2, 2007, 10:01 PM
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

/**
 * 
DOCUMENT ME!
 *
 * @author cooper
 */
public class Phone extends AbstractBindable implements HasType {
    private Integer id;
    private String number;
    private TypeLookup type;

/** Creates a new instance of Phone */
    public Phone() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public TypeLookup getType() {
        return type;
    }

    public void setType(TypeLookup type) {
        this.type = type;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Phone: id-" + this.id);
        sb.append(" number-" + this.number);
        sb.append(" type-" + ((this.type != null) ? this.type.name : "null"));

        return sb.toString();
    }
}
