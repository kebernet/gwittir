/*
 * Contact.java
 *
 * Created on August 2, 2007, 10:16 PM
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

package com.totsp.gwittir.example.api;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 *
 * @author cooper
 */
@Entity
@NamedQueries( {
    @NamedQuery(
    name = "Contact.firstNameAscending",
            query = "SELECT c FROM Contact c ORDER BY c.firstName" ),
            @NamedQuery(
    name = "Contact.firstNameDescending",
            query = "SELECT c FROM Contact c ORDER BY c.firstName DESC" ),
            @NamedQuery(
    name = "Contact.lastNameAscending",
            query = "SELECT c FROM Contact c ORDER BY c.lastName" ),
            @NamedQuery(
    name = "Contact.lastNameDescending",
            query = "SELECT c FROM Contact c ORDER BY c.lastName DESC" )
} )
public class Contact {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    @Lob
    private String notes;
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    //@IndexColumn(name="ca_id")
    private List<Address> addresses;
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    //@IndexColumn(name="cp_id")
    private List<Phone> phoneNumbers;
    
    /** Creates a new instance of Contact */
    public Contact() {
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
    public List<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    public void setPhoneNumbers(List<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    
}
