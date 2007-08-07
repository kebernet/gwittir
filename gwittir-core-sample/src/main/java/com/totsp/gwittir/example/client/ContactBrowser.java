/*
 * ContactBrowser.java
 *
 * Created on August 5, 2007, 4:04 PM
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

import com.google.gwt.user.client.ui.VerticalPanel;
import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.table.BoundTable;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;

/**
 *
 * @author cooper
 */
public class ContactBrowser extends AbstractBoundWidget{
    
    private Object value;
    VerticalPanel p = new VerticalPanel();
    BoundWidgetTypeFactory factory = new BoundWidgetTypeFactory();
    BoundTable contacts = new BoundTable( BoundTable.SCROLL_MASK +
            BoundTable.HEADER_MASK +
            BoundTable.NO_SELECT_CELL_MASK +
            BoundTable.NO_SELECT_COL_MASK +
            BoundTable.SORT_MASK, factory );
    Button newContact = new Button("New Contact");
    Label l = new Label( "Browse Contacts");
    
    /** Creates a new instance of ContactBrowser */
    public ContactBrowser() {
        factory.add( String.class, BoundWidgetTypeFactory.LABEL_PROVIDER );
        contacts.setSize("500px", "400px");
        super.initWidget( p );
        p.add( l );
        p.add( contacts );
        p.add( newContact );
        
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public void setAction(Action action ){
        super.setAction( action );
        contacts.setAction( action );
        newContact.setAction( action );
    }
    
}
