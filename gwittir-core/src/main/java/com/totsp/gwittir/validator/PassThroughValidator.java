/*
 * PassThroughValidator.java
 *
 * Created on April 12, 2007, 6:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.validator;

import com.totsp.gwittir.ValidationException;
import com.totsp.gwittir.Validator;

/**
 *
 * @author cooper
 */
public class PassThroughValidator implements Validator {
    
    /** Creates a new instance of PassThroughValidator */
    public PassThroughValidator() {
    }

    public Object validate(Object value) throws ValidationException {
        return value;
    }
    
    
}
