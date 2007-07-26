/*
 * PanelValidationFeedback.java
 *
 * Created on July 26, 2007, 11:48 AM
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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.flow.BoundWidgetProvider;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Label;

/**
 *
 * @author cooper
 */
public class PanelValidationFeedback extends AbstractValidationFeedback {
    
    private Panel panel;
    private BoundWidget widget;
    private BoundWidgetProvider provider;
    
    /** Creates a new instance of PanelValidationFeedback */
    public PanelValidationFeedback(Panel panel) {
        this.panel = panel;
        Label l = new Label();
        l.setStyleName( "gwittir-ValidationPanel");
        this.widget = l;
        
    }
    
    public PanelValidationFeedback(Panel panel, BoundWidgetProvider provider){
        this.panel = panel;
        this.provider = provider;
    }
    
    public void handleException(Object source, ValidationException exception) {
        if( this.provider != null ){
            if( this.widget != null ){
                panel.remove( (Widget) this.widget );
            }
            this.widget = provider.get();
        }
        this.panel.add( (Widget) this.widget );
        this.widget.setValue( this.getMessage( exception ) );
    }
    
    public void resolve() {
        this.panel.remove( (Widget) this.widget );
    }
    
    
    
}
