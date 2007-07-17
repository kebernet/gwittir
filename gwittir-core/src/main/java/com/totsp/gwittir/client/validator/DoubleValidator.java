/*
 * DoubleValidator.java
 *
 * Created on July 16, 2007, 5:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.validator;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.Validator;

/**
 *
 * @author cooper
 */
public class DoubleValidator implements Validator {
    
    /** Creates a new instance of DoubleValidator */
    public DoubleValidator() {
    }

    public Object validate(Object value) throws ValidationException {
        if(value == null) {
            return value;
        }

        Double i;

        try {
            i = new Double(value.toString());
        } catch(NumberFormatException nfe) {
            throw new ValidationException("Must be an decimal value.",
                DoubleValidator.class);
        }

        return i;
    }
    
}
