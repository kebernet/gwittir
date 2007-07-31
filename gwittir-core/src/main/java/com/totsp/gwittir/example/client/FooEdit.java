/*
 * FooEdit.java
 *
 * Created on July 30, 2007, 4:43 PM
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

package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.ui.FlowPanel;

import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.Label;

/**
 *
 * @author cooper
 */
public class FooEdit extends AbstractBoundWidget {
    
    private Foo value = null;
    private FlowPanel p = new FlowPanel();
    Label intProperty = new Label();
    Label stringProperty = new Label();
    Label booleanProperty = new Label();
    
    /** Creates a new instance of FooEdit */
    public FooEdit() {
        p.add( new Label("Stirng Property: ") );
        p.add( stringProperty );
        p.add( new Label("Integer Property: ") );
        p.add( intProperty );
        p.add( new Label( "Boolean Property: "));
        p.add( booleanProperty );
        p.setHeight( "100px");
        super.initWidget( p );
        
    }
    
    public void setValue(Object value) {
        this.value = (Foo) value;
        
    }
    
    public Object getValue() {
        return value;
    }
    
    
    
}
