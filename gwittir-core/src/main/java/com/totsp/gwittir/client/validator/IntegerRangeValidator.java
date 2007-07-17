/*
 * IntegerRangeValidator.java
 *
 * Created on July 16, 2007, 5:04 PM
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
public class IntegerRangeValidator implements Validator {
    private IntegerValidator iv = new IntegerValidator();
    private int max;
    private int min;

    /** Creates a new instance of IntegerRangeValidator */
    public IntegerRangeValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Object validate(Object value) throws ValidationException {
        Integer i = (Integer) iv.validate(value);

        if(i == null) {
            return null;
        }

        if((i.intValue() <= max) && (i.intValue() >= min)) {
            return value;
        }

        throw new ValidationException("Must be a value between " + min +
            " and " + max, IntegerRangeValidator.class);
    }
}
