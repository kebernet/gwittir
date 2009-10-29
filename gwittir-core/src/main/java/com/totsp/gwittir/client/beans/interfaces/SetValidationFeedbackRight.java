/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.beans.interfaces;

import com.totsp.gwittir.client.validator.ValidationFeedback;

/**
 *
 * @author kebernet
 */
public interface SetValidationFeedbackRight {

    Finish notifiedWithRight( ValidationFeedback feedback );
    Finish notifiedWithRight( ValidationFeedback... feedbacks );
}
