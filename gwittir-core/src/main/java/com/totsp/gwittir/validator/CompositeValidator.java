/*
 * CompositeValidator.java
 *
 * Created on July 16, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.validator;

import com.totsp.gwittir.ValidationException;
import com.totsp.gwittir.Validator;

import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author cooper
 */
public class CompositeValidator implements Validator {
    private ArrayList validators = new ArrayList();

    /** Creates a new instance of CompositeValidator */
    public CompositeValidator() {
    }

    public CompositeValidator add(Validator v) {
        validators.add(v);

        return this;
    }

    public Object validate(Object value) throws ValidationException {
        Object retValue = value;

        for(Iterator it = validators.iterator(); it.hasNext();) {
            retValue = ((Validator) it.next()).validate(retValue);
        }

        return retValue;
    }
}
