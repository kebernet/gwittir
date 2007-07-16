/*
 * Introspector.java
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
public interface Introspector {
    
    public BeanDescriptor getDescriptor( Class clazz );
    public BeanDescriptor getDescriptor( Object object );
    public Class resolveClass( Object instance );
}
