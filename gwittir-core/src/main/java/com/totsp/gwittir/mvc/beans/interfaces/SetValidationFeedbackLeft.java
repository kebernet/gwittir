/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.beans.interfaces;

import com.totsp.gwittir.mvc.validator.ValidationFeedback;

/**
 *
 * @author kebernet
 */
public interface SetValidationFeedbackLeft {
    SetRight notifiedWithLeft( ValidationFeedback feedback );
    SetRight notifiedWithLeft( ValidationFeedback... feedbacks );
}
