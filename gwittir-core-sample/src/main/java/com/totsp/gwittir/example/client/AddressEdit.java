/*
 * AddressEdit.java
 *
 * Created on August 5, 2007, 10:03 PM
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

import com.google.gwt.user.client.ui.FlexTable;

import com.totsp.gwittir.client.fx.MutationStrategy;
import com.totsp.gwittir.client.fx.PropertyAnimator;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.ListBox;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.example.client.remote.Services;
import com.totsp.gwittir.example.client.remote.StateLookup;
import java.util.Comparator;


/**
 *
 * @author cooper
 */
public class AddressEdit extends AbstractBoundWidget {
    FlexTable base = new FlexTable();
    ListBox state = new ListBox();
    ListBox type = (ListBox) TypeSelectorProvider.INSTANCE.get();
    TextBox address1 = new TextBox();
    TextBox address2 = new TextBox();
    TextBox city = new TextBox();
    TextBox zip = new TextBox();
    
    /** Creates a new instance of AddressEdit */
    public AddressEdit() {
        super();
        state.setOptions( Services.FREEZER.stateLookups() );
        state.setComparator( new Comparator(){
            public int compare(Object o, Object c){
                return ((StateLookup)o).id.compareTo( ((StateLookup)c).id );
            }
        });
        type.setOptions(Services.FREEZER.typeLookups()) ;
        base.setStyleName("example-AddressEdit");
        super.initWidget(base);
        base.setWidget(0, 0, address1);
        base.getFlexCellFormatter().setColSpan(0, 0, 3);
        base.setWidget(1, 0, address2);
        base.getFlexCellFormatter().setColSpan(1, 0, 3);
        base.setWidget(2, 0, city);
        base.setWidget(2, 1, state);
        base.setWidget(2, 2, zip);
        base.setWidget(3, 0, type);
        base.getFlexCellFormatter().setColSpan(3, 0, 3);
        this.setHeight("0px");
        
    }
    
    public Object getValue() {
        return this.getModel();
    }
    
    public void onAttach() {
        super.onAttach();
        
        PropertyAnimator anim = new PropertyAnimator(this, "height", "0px",
                "125px", MutationStrategy.UNITS_SINOIDAL, 750);
        anim.start();
    }
    
    public void setValue(Object value) {
        this.setModel(value);
    }
}
