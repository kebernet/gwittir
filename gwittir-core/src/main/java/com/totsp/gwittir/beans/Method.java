/*
 * Method.java
 *
 * Created on July 15, 2007, 1:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.beans;


/**
 *
 * @author cooper
 */
public interface Method {
    public String getName();

    public abstract Object invoke(Object target, Object[] args)
        throws Exception;
}
