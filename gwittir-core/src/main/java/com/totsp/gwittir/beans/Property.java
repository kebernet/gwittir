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
    
    private String name;
    private Method accessMethod;
    private Method mutatorMethod;
    
    
    /** Creates a new instance of Property */
    public Property(String name, Method accessMethod, Method mutatorMethod ) {
        this.name = name;
        this.accessMethod = accessMethod;
        this.mutatorMethod = mutatorMethod;
    }

    public String getName() {
        return name;
    }

    public Method getAccessMethod() {
        return accessMethod;
    }

    public Method getMutatorMethod() {
        return mutatorMethod;
    }
    
    public String toString(){
        return "Property[ name="+name+" ]";
    }
    
}
