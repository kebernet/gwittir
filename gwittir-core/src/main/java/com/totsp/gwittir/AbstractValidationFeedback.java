/*
 * AbstractValidationFeedback.java
 *
 * Created on July 16, 2007, 7:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.beans.Introspector;

import java.util.HashMap;


/**
 *
 * @author cooper
 */
public abstract class AbstractValidationFeedback implements ValidationFeedback {
    private HashMap mappings = new HashMap() /*<Class, Message>*/;

    public AbstractValidationFeedback() {
        super();
    }

    public AbstractValidationFeedback addMessage(
        Class validationExceptionClass, String message) {
        mappings.put(validationExceptionClass, message);

        return this;
    }

    protected String getMessage(ValidationException validationException) {
        Class clazz = validationException.getValidatorClass();
        String message =  (String) mappings.get(clazz);
        return message == null ? "Unknown error." : message;
    }
}
