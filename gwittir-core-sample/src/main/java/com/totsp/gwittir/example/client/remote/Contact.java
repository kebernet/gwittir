/*
 * Contact.java
 *
 * Created on August 2, 2007, 8:19 PM
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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cooper
 */
public class Contact extends AbstractBindable{
    
    private Integer id;
    private String firstName;
    private String lastName;
    private String notes;
    
    /**
     * @gwt.typeArgs <com.totsp.gwittir.example.client.remote.Address>
     */
    private List addresses = new ArrayList();
    /**
     * @gwt.typeArgs <com.totsp.gwittir.example.client.remote.Phone>
     */
    private List phoneNumbers = new ArrayList();    
    
    
    /** Creates a new instance of Contact */
    public Contact() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer old = this.id;
        this.id = id;
        this.changes.firePropertyChange("id", old, id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String old = this.firstName;
        this.firstName = firstName;
        this.changes.firePropertyChange("firstName", old, firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String old = this.lastName;
        this.lastName = lastName;
        this.changes.firePropertyChange("lastName", old, lastName);
    }

    public List getAddresses() {
        return addresses;
    }

    public void setAddresses(List addresses) {
        List old = this.addresses;
        this.addresses = new ArrayList(addresses);
        this.changes.firePropertyChange("addresses", old, this.addresses);
    }

    public List getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List phoneNumbers) {
        List old = this.phoneNumbers;
        this.phoneNumbers = new ArrayList(phoneNumbers);
        this.changes.firePropertyChange("phoneNumbers", old, this.phoneNumbers );
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Contact: id-" + this.id);
        sb.append(" firstName-" + this.firstName);
        sb.append(" lastName-" + this.lastName);
        sb.append(" notes-" + this.notes);
        return sb.toString();    
    }
    
}
