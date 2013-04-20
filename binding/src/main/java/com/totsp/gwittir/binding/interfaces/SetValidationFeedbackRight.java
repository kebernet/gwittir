/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.binding.interfaces;

import com.totsp.gwittir.binding.validator.ValidationFeedback;

/**
 *
 * @author kebernet
 */
public interface SetValidationFeedbackRight {

    Finish notifiedWithRight(ValidationFeedback feedback);
    Finish notifiedWithRight(ValidationFeedback... feedbacks);
}
