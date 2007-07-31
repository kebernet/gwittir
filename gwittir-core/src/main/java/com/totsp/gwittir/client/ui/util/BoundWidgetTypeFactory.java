/*
 * BoundWidgetTypeFactory.java
 *
 * Created on July 27, 2007, 8:11 PM
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


import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Checkbox;
import com.totsp.gwittir.client.ui.TextBox;
import java.util.HashMap;

/**
 *
 * @author cooper
 */
public class BoundWidgetTypeFactory {
    HashMap registry = new HashMap();
    
    /** Creates a new instance of BoundWidgetTypeFactory */
    public BoundWidgetTypeFactory() {
        BoundWidgetProvider p = new BoundWidgetProvider(){
            public BoundWidget get() {
                return new Checkbox();
            }
            
        };
        registry.put( Boolean.class, p );
        registry.put( boolean.class, p );
        
        p = new BoundWidgetProvider(){
            public BoundWidget get() {
                return new TextBox();
            }
            
        };
        registry.put( String.class, p );
        registry.put( Integer.class, p );
        registry.put( int.class, p );
        registry.put( Long.class, p );
        registry.put( long.class, p );
        registry.put( Float.class, p );
        registry.put( float.class, p );
        registry.put( Double.class, p );
        registry.put( double.class, p );
       
    }
    
    public BoundWidgetProvider getWidgetProvider( Class type ){
        return (BoundWidgetProvider) registry.get( type );
    }
    
    public BoundWidgetProvider getWidgetProvider( String propertyName, Class type ){
        return registry.containsKey(propertyName) ? 
                (BoundWidgetProvider) registry.get( propertyName ) :
                (BoundWidgetProvider) registry.get( type );
    }
    
    public void add( Class type, BoundWidgetProvider provider ){
        registry.put( type, provider );
    }
    
    public void add( String propertyName, BoundWidgetProvider provider ){
        registry.put( propertyName, provider );
    }
    
    
    
    
    
    
}
