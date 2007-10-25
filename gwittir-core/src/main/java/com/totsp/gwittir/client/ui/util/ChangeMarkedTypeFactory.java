/*
 * ChangeMarkedTypeFactory.java
 *
 * Created on October 25, 2007, 2:36 PM
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

import com.totsp.gwittir.client.fx.ui.ChangeMarkerListener;
import com.totsp.gwittir.client.ui.BoundWidget;

/**
 *
 * @author cooper
 */
public class ChangeMarkedTypeFactory extends BoundWidgetTypeFactory {
    
    
    
    public ChangeMarkedTypeFactory(){
        super(true);
    }

    public BoundWidgetProvider getWidgetProvider(Class type) {
        BoundWidgetProvider retValue;
        
        retValue = new BoundWidgetProviderWrapper(super.getWidgetProvider(type));
        return retValue;
    }

    public BoundWidgetProvider getWidgetProvider(String propertyName, Class type) {
        BoundWidgetProvider retValue;
        
        retValue = new BoundWidgetProviderWrapper(super.getWidgetProvider(propertyName, type));
        return retValue;
    }
    
    private static class BoundWidgetProviderWrapper implements BoundWidgetProvider{
        BoundWidgetProvider p;
        BoundWidgetProviderWrapper(BoundWidgetProvider p){
            this.p = p;
        }
        
        public BoundWidget get() {
            BoundWidget w = p.get();
            w.addPropertyChangeListener( new ChangeMarkerListener() );
            return w;
        }
        
    }
    
}

