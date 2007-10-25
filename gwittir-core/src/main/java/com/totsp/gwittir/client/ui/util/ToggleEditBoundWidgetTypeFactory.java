/*
 * ToggleEditBoundWidgetTypeFactory.java
 *
 * Created on October 25, 2007, 1:59 PM
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

package com.totsp.gwittir.client.ui.util;

import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.client.ui.ToggleEditContainer;

/**
 *
 * @author cooper
 */
public class ToggleEditBoundWidgetTypeFactory extends BoundWidgetTypeFactory {
    
    public static final BoundWidgetProvider LABEL_TEXTBOX_PROVIDER = new BoundWidgetProvider(){
        public BoundWidget get() {
                return new ToggleEditContainer( new Label(), new TextBox() );
            }
    };
    
    /** Creates a new instance of ToggleEditBoundWidgetTypeFactory */
    public ToggleEditBoundWidgetTypeFactory() {
        super(false);
        this.add( Integer.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( int.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( Double.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( double.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( Long.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( long.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( Float.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( float.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( String.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( Character.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
        this.add( char.class, ToggleEditBoundWidgetTypeFactory.LABEL_TEXTBOX_PROVIDER );
    }

    public BoundWidgetProvider getWidgetProvider(Class type) {
        BoundWidgetProvider retValue;
        
        retValue = super.getWidgetProvider(type);
        return retValue;
    }

    public BoundWidgetProvider getWidgetProvider(String propertyName, Class type) {
        BoundWidgetProvider retValue;
        
        retValue = super.getWidgetProvider(propertyName, type);
        Logger.getAnonymousLogger().log(Level.SPAM, "Returning provider"+retValue, null);
        return retValue;
    }
    
}
