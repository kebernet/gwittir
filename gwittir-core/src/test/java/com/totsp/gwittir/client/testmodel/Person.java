package com.totsp.gwittir.client.testmodel;

import com.totsp.gwittir.client.beans.AbstractModelBean;


public class Person extends AbstractModelBean {
    private String firstName;
    private String lastName;
    private int age;
    private Person[] children;

    public static final String PROP_CHILDREN = "children";

    /**
     * Get the value of children
     *
     * @return the value of children
     */
    public Person[] getChildren() {
        return this.children;
    }

    /**
     * Set the value of children
     *
     * @param newchildren new value of children
     */
    public void setChildren(Person[] newchildren) {
        Person[] oldchildren = children;
        this.children = newchildren;
        this.changeSupport.firePropertyChange(PROP_CHILDREN, oldchildren, newchildren);
    }

    private Person spouse;

    public Person() {
    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String old = this.firstName;
        this.firstName = firstName;
        this.changeSupport.firePropertyChange("firstName", old, firstName );
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String old = this.lastName;
        this.lastName = lastName;
        this.changeSupport.firePropertyChange("lastName", old, lastName );
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        int old = this.age;
        this.age = age;
        this.changeSupport.firePropertyChange("age", old, age);
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        Person old = this.spouse;
        this.spouse = spouse;
        this.changeSupport.firePropertyChange("spouse", old, spouse );
    }
    
    public String toString(){ return this.firstName; }
}
