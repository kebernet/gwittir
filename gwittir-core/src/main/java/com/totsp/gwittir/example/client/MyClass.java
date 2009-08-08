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

import com.google.gwt.user.client.rpc.IsSerializable;

import com.totsp.gwittir.client.beans.AbstractModelBean;

import java.util.Date;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet"
 *         Cooper</a>
 */
public class MyClass extends AbstractModelBean implements IsSerializable {
    private Date birthDate = new Date(74, 9, 02);
    private Integer someInteger;
    private String emailAddress;
    private String firstName;
    private String homeTown;
    private String lastName;
    private String name;
    private String zipCode;
    private boolean active = true;
    private double price;

    /** Creates a new instance of MyClass */
    public MyClass() {
    }

    public void setActive(boolean active) {
        boolean old = this.active;
        this.active = active;
        this.changeSupport.firePropertyChange("active", old, active);
    }

    public boolean isActive() {
        return active;
    }

    public void setBirthDate(Date birthDate) {
        Date old = this.birthDate;
        this.birthDate = birthDate;
        this.changeSupport.firePropertyChange("birthDate", old, birthDate);
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setEmailAddress(String emailAddress) {
        String old = this.emailAddress;
        this.emailAddress = emailAddress;
        this.changeSupport.firePropertyChange("emailAddress", old, emailAddress);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setFirstName(String firstName) {
        String old = this.firstName;
        this.firstName = firstName;
        this.changeSupport.firePropertyChange("firstName", old, firstName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setHomeTown(String homeTown) {
        String old = this.homeTown;
        this.homeTown = homeTown;
        this.changeSupport.firePropertyChange("homeTown", old, homeTown);
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setLastName(String lastName) {
        String old = this.lastName;
        this.lastName = lastName;
        this.changeSupport.firePropertyChange("lastName", old, lastName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        this.changeSupport.firePropertyChange("name", old, name);
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        Double old = new Double(this.price);
        this.price = price;
        this.changeSupport.firePropertyChange("price", old, new Double(price));
    }

    public double getPrice() {
        return price;
    }

    public void setSomeInteger(Integer someInteger) {
        Integer old = this.someInteger;
        this.someInteger = someInteger;
        this.changeSupport.firePropertyChange("someInteger", old, someInteger);
    }

    public Integer getSomeInteger() {
        return someInteger;
    }

    public void setZipCode(String zipCode) {
        String old = this.zipCode;
        this.zipCode = zipCode;
        this.changeSupport.firePropertyChange("zipCode", old, zipCode);
    }

    public String getZipCode() {
        return zipCode;
    }
}
