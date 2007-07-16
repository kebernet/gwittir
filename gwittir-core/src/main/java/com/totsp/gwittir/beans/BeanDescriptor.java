/*
 * BeanDescriptor.java
 *
 * Created on July 15, 2007, 1:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.beans;


/**
 *
 * @author cooper
 */
public interface BeanDescriptor {
    public Property[] getProperties();

    public Property getProperty(String name);
}
