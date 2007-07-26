/*
 * PopupValidationFeedback.java
 *
 * Created on July 16, 2007, 7:29 PM
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

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;

/**
 *
 * @author cooper
 */
public class PopupValidationFeedback extends AbstractValidationFeedback {
    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM =4;
    final HashMap popups = new HashMap();
    private int position;

    /** Creates a new instance of PopupValidationFeedback */
    public PopupValidationFeedback(int position) {
        this.position = position;
    }

    public void handleException(Object source, ValidationException exception) {
        Widget w = (Widget) source;
        PopupPanel p = new PopupPanel(false);
        popups.put( source, p );
        p.setStyleName("gwittir-ValidationPopup");
        p.setWidget(new Label(this.getMessage(exception)));
        p.setPopupPosition( -5000, -5000 );
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

    public void resolve(Object source) {
        PopupPanel p = (PopupPanel) popups.get( source );
        p.hide();
        popups.remove( source );
    }

    
}
