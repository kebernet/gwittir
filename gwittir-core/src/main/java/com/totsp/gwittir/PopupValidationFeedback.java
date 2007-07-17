/*
 * PopupValidationFeedback.java
 *
 * Created on July 16, 2007, 7:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.Widget;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 * @author cooper
 */
public class PopupValidationFeedback extends AbstractValidationFeedback {
    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM =4;
    final PopupPanel p = new PopupPanel(false);
    private int position;

    /** Creates a new instance of PopupValidationFeedback */
    public PopupValidationFeedback(int position) {
        this.position = position;
    }

    public void handleException(Object source, ValidationException exception) {
        Widget w = (Widget) source;
        p.setStyleName("gwittir-ValidationPopup");
        p.setWidget(new Label(this.getMessage(exception)));
        p.show();
        if(this.position == BOTTOM) {
            p.setPopupPosition(w.getAbsoluteLeft(),
                w.getAbsoluteTop() + w.getOffsetHeight());
        } else if(this.position == RIGHT) {
            p.setPopupPosition(w.getAbsoluteLeft() + w.getOffsetWidth(),
                w.getAbsoluteTop());
        } else if(this.position == LEFT) {
            p.setPopupPosition(w.getAbsoluteLeft() - p.getOffsetWidth(),
                w.getAbsoluteTop());
        } else if(this.position == TOP) {
            p.setPopupPosition(w.getAbsoluteLeft(),
                w.getAbsoluteTop() - p.getOffsetHeight());
        }
        

        
    }

    public void resolve() {
        p.hide();
    }

    
}
