/*
 * CompositeValidator.java
 *
 * Created on July 16, 2007, 5:05 PM
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

import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.Validator;

import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
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
