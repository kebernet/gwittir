/*
 * PopupValidationFeedback.java
 *
 * Created on July 16, 2007, 7:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author cooper
 */
public class PopupValidationFeedback extends AbstractValidationFeedback {
    
    /** Creates a new instance of PopupValidationFeedback */
    public PopupValidationFeedback() {
        
    }

    public void handleException(Object source, ValidationException exception) {
        Widget w = (Widget) source;
        PopupPanel p = new PopupPanel(true);
        p.setStyleName( "gwittir-ValidationPopup");
        p.setWidget( new Label( this.getMessage( exception ) ) );
        p.setPopupPosition( w.getAbsoluteLeft(), w.getAbsoluteTop() + w.getOffsetHeight() );
        p.show();
        
    }
    
}
