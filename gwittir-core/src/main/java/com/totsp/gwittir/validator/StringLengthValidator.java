/*
 * StringLengthValidator.java
 *
 * Created on July 16, 2007, 5:29 PM
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
public class StringLengthValidator implements Validator {
    int min;

    /** Creates a new instance of StringLengthValidator */
    public StringLengthValidator(int minCharacters) {
        this.min = minCharacters;
    }

    public Object validate(Object value) throws ValidationException {
        if((value == null) || (value.toString().length() < min)) {
            throw new ValidationException("Value must be at least " + min +
                " characters.", StringLengthValidator.class);
        }

        return value;
    }
}
