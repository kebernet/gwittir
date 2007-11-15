/*
 * MyClass.java
 *
 * Created on August 7, 2007, 8:24 PM
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

package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.beans.Bindable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class MyClass implements Bindable{
    
    
    private Integer someInteger;
    private String name;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private double price;
    private String homeTown;
    private String zipCode;
    private Date birthDate = new Date( 74, 9, 02 );
    private boolean active = true;
    
    private transient PropertyChangeSupport changes = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(l);
    }
    
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(propertyName, l);
    }
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.changes.getPropertyChangeListeners();
    }
    public void removePropertyChangeListener(PropertyChangeListener l) {
        this.changes.removePropertyChangeListener(l);
    }
    
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener l) {
        this.changes.removePropertyChangeListener(propertyName, l);
    }
    
    /** Creates a new instance of MyClass */
    public MyClass() {
    }

    public Integer getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(Integer someInteger) {
        Integer old = this.someInteger;
        this.someInteger = someInteger;
        this.changes.firePropertyChange( "someInteger", old, someInteger );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        this.changes.firePropertyChange("name", old, name);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        String old = this.emailAddress;
        this.emailAddress = emailAddress;
        this.changes.firePropertyChange("emailAddress", old, emailAddress );
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        Double old = new Double( this.price  );
        this.price = price;
        this.changes.firePropertyChange("price", old, new Double(price));
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        String old = this.homeTown;
        this.homeTown = homeTown;
        this.changes.firePropertyChange("homeTown", old, homeTown );
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        String old = this.zipCode;
        this.zipCode = zipCode;
        this.changes.firePropertyChange("zipCode", old, zipCode );
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

   
    
}
