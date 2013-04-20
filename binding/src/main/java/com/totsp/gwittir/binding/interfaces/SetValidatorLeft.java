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
public interface SetValidatorLeft {
    SetValidationFeedbackLeft validateLeftWith(Validator validator);
    SetValidationFeedbackLeft validateLeftWith(Validator... validators);
}
