/*
 * AbstractValidationFeedback.java
 *
 * Created on July 16, 2007, 7:43 PM
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.totsp.gwittir.client.validator;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.client.beans.Introspector;

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
