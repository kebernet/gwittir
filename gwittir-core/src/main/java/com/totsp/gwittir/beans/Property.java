/*
 * Property.java
 *
 * Created on July 15, 2007, 1:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.beans;


/**
 *
 * @author cooper
 */
public class Property {
    private Class type;
    private Method accessMethod;
    private Method mutatorMethod;
    private String name;

    /** Creates a new instance of Property */
    public Property(String name, Class type, Method accessMethod,
        Method mutatorMethod) {
        this.name = name;
        this.accessMethod = accessMethod;
        this.mutatorMethod = mutatorMethod;
    }

    public Method getAccessMethod() {
        return accessMethod;
    }

    public Method getMutatorMethod() {
        return mutatorMethod;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public String toString() {
        return "Property[ name=" + name + " ]";
    }
}
