/*
 * NotNullValidator.java
 *
 * Created on July 16, 2007, 5:16 PM
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
public class NotNullValidator implements Validator {
    /** Creates a new instance of NotNullValidator */
    public NotNullValidator() {
    }

    public Object validate(Object value) throws ValidationException {
        if(value == null) {
            throw new ValidationException("Value cannot be empty.",
                NotNullValidator.class);
        }

        return value;
    }
}
