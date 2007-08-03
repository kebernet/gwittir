/*
 * IntegerValidatorTest.java
 * JUnit based test
 *
 * Created on August 2, 2007, 4:01 PM
 */

package com.totsp.gwittir.client.validator;

import junit.framework.*;
import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.Validator;

/**
 *
 * @author cooper
 */
public class IntegerValidatorTest extends TestCase {
    
    public IntegerValidatorTest(String testName) {
        super(testName);
    }

    /**
     * Test of validate method, of class com.totsp.gwittir.client.validator.IntegerValidator.
     */
    public void testValidate() throws Exception {
        IntegerValidator.INSTANCE.validate( (String) null );
        try{
            IntegerValidator.INSTANCE.validate( "z" );
            this.assertTrue("ValidationException not thrown properly.", false);
        } catch( ValidationException e){
            this.assertEquals( IntegerValidator.class, e.getValidatorClass());
        }
        IntegerValidator.INSTANCE.validate( "3" );
        IntegerValidator.INSTANCE.validate( new Integer( Integer.MIN_VALUE ) );
    }
    
}
