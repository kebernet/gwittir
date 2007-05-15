/*
 * Validator.java
 *
 * Created on April 12, 2007, 5:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir;

/**
 *
 * @author cooper
 */
public interface Validator {
    
    public Object validate( Object value ) throws ValidationException;
    
}
