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
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Label;
import java.util.HashMap;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class PanelValidationFeedback extends AbstractValidationFeedback {
    
    private Panel panel;
    private BoundWidgetProvider provider;
    private HashMap widgets = new HashMap();
    
    /** Creates a new instance of PanelValidationFeedback */
    public PanelValidationFeedback(Panel panel) {
        this.panel = panel;
        
        
    }
    
    public PanelValidationFeedback(Panel panel, BoundWidgetProvider provider){
        this.panel = panel;
        this.provider = provider;
    }
    
    public void handleException(Object source, ValidationException exception) {
        BoundWidget widget = (BoundWidget) this.widgets.get( source );
        if( this.provider != null ){
            if( widget != null ){
                panel.remove( (Widget) widget );
            }
            widget = provider.get();
        } else {
            Label l = new Label();
            l.setStyleName( "gwittir-ValidationPanel");
            widget = l;
        }
        this.panel.add( (Widget) widget );
        widget.setValue( this.getMessage( exception ) );
        this.widgets.put( source, widget );
    }
    
    public void resolve(Object source) {
        Widget widget = (Widget) this.widgets.get(source);
        if( widget != null ){
            this.panel.remove( (Widget) widget );
        }
        this.widgets.remove( source );
    }
    
    
    
}
