/*
 * StringLengthValidator.java
 *
 * Created on July 16, 2007, 5:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.validator;

import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.Validator;


/**
 *
 * @author cooper
 */
public class StringLengthValidator implements Validator {
    int max;
    int min;

    /** Creates a new instance of StringLengthValidator */
    public StringLengthValidator(int minCharacters, int maxCharacters) {
        this.min = minCharacters;
        this.max = maxCharacters;
    }

    public Object validate(Object value) throws ValidationException {
        if((value == null) || (value.toString().length() < min)) {
            throw new ValidationException("Value must be at least " + min +
                "and no more than " + max + " characters.",
                StringLengthValidator.class);
        }

        return value;
    }
}
