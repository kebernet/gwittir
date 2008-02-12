/*
 * Address.java
 *
 * Created on August 2, 2007, 8:31 PM
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
 * @author cooper
 */
public class Address extends AbstractBindable implements HasType {
    private Integer id;
    private TypeLookup type;
    private String address1;
    private String address2;
    private String city;
    private StateLookup state;
    private String zip;
    
    /** Creates a new instance of Address */
    public Address() {
    }

    public TypeLookup getType() {
        return type;
    }

    public void setType(TypeLookup type) {
        TypeLookup old = this.type;
        this.type = type;
        this.changes.firePropertyChange("type", old, type);
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        String old = this.address1;
        this.address1 = address1;
        this.changes.firePropertyChange("address1", old, address1);
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        String old = this.address2;
        this.address2 = address2;
        this.changes.firePropertyChange("address2", old, address2);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        String old = this.city;
        this.city = city;
        this.changes.firePropertyChange("city", old, city);
    }

    public StateLookup getState() {
        return state;
    }

    public void setState(StateLookup state) {
        StateLookup old = this.state;
        this.state = state;
        this.changes.firePropertyChange("state", old, state);
    }

    public String getZip() {
        return zip;
    } 

    public void setZip(String zip) {
        String old = this.zip;
        this.zip = zip;
        this.changes.firePropertyChange("zip", old, zip);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer old = this.id;
        this.id = id;
        this.changes.firePropertyChange("id", old, id);
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Address: id-" + this.id);
        sb.append(" type-" + (this.type != null ? this.type.name : "null"));
        sb.append(" address1-" + this.address1);
        sb.append(" address2-" + this.address2);
        sb.append(" city-" + this.city);
        sb.append(" state-" + (this.state != null ? this.state.code : "null"));
        sb.append(" zip-" + this.zip);
        return sb.toString();    
    }
    
    public static void main(String[] args)
    {
        Address a = new Address();
        a.setCity("city");
        System.out.println("address - " + a);
    }
}
