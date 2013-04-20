/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.binding.interfaces;


import com.totsp.gwittir.binding.validator.Validator;

/**
 *
 * @author kebernet
 */
public interface SetValidatorRight {
    SetValidationFeedbackRight validateRightWith(Validator validator);
    SetValidationFeedbackRight validateRightWith(Validator... validators);
}
