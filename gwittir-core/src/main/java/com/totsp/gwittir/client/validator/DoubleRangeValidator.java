/*
 * DoubleRangeValidator.java
 *
 * Created on July 16, 2007, 5:42 PM
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
public class DoubleRangeValidator implements Validator{
    
    private double min;
    private double max;
    private DoubleValidator dv = new DoubleValidator();
    
    public DoubleRangeValidator(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public Object validate(Object value) throws ValidationException {
        Double i = (Double) dv.validate(value);

        if(i == null) {
            return null;
        }

        if((i.intValue() <= max) && (i.intValue() >= min)) {
            return value;
        }

        throw new ValidationException("Must be a value between " + min +
            " and " + max, DoubleRangeValidator.class);
    }
    
}
