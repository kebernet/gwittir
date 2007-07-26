/*
 * StyleValidationFeedback.java
 *
 * Created on July 26, 2007, 12:38 PM
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.UIObject;

/**
 *
 * @author cooper
 */
public class StyleValidationFeedback extends AbstractValidationFeedback {
    
    private String previousStyle;
    private String styleName;
    private UIObject object;
    /** Creates a new instance of StyleValidationFeedback */
    public StyleValidationFeedback(String styleName) {
        this.styleName = styleName;
    }
    
    public void handleException(Object source, ValidationException exception) {
        this.object = (UIObject) source;
        this.previousStyle =  this.object.getStyleName() == null ||
                this.object.getStyleName().length() == 0 ?
                    "default" :
                    this.object.getStyleName();
        GWT.log( "Previous style: "+ this.previousStyle, null );
        this.object.setStyleName( this.styleName );
        GWT.log( "Current style: "+ this.object.getStyleName(), null );
    }
    
    public void resolve() {
        this.object.setStyleName( previousStyle );
    }
    
    
    
}
