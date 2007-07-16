/*
 * IntegerValidator.java
 *
 * Created on April 12, 2007, 5:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.validator;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.ValidationException;
import com.totsp.gwittir.Validator;


/**
 *
 * @author cooper
 */
public class IntegerValidator implements Validator {
    /** Creates a new instance of IntegerValidator */
    public IntegerValidator() {
    }

    public Object validate(Object value) throws ValidationException {
        GWT.log("VALIDATING " + value, null);

        if(value == null) {
            return value;
        }

        Integer i;

        try {
            i = new Integer(value.toString());
        } catch(NumberFormatException nfe) {
            GWT.log("Fail", null);
            throw new ValidationException("Must be an integer value.",
                IntegerValidator.class);
        }

        return i;
    }
}
