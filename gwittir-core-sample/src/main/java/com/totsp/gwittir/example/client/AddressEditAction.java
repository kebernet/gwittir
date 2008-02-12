/*
 * AddressEditAction.java
 *
 * Created on August 5, 2007, 10:06 PM
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

import java.util.Comparator;

import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Renderer;
import com.totsp.gwittir.example.client.remote.Address;
import com.totsp.gwittir.example.client.remote.StateLookup;

/**
 *
 * @author cooper
 */
public class AddressEditAction implements BindingAction {
    
    private Binding binding = new Binding();
    /** Creates a new instance of AddressEditAction */
    public AddressEditAction() {
    }

    public void unbind(BoundWidget widget) {
        binding.unbind();
        binding.getChildren().clear();
    }

    public void bind(BoundWidget widget) {
        binding.bind();
    }

    public void set(BoundWidget widget) {
        Address a = (Address) widget.getModel();
        AddressEdit e = (AddressEdit) widget;
        
        binding.getChildren().add( new Binding(e.address1, "value", a, "address1" ));
        binding.getChildren().add( new Binding(e.address2, "value", a, "address2" ));
        binding.getChildren().add( new Binding(e.city, "value", a, "city" ));
        binding.getChildren().add( new Binding(e.state, "value",  a, "state" ));
        binding.getChildren().add( new Binding(e.zip, "value", a, "zip" ));
        binding.getChildren().add( new Binding(e.type, "value",  a, "type" ));
        
        e.state.setRenderer( new Renderer(){
            public Object render(Object o) {
                return ((StateLookup) o).name;
            }
            
        });
        e.state.setComparator( new Comparator(){
            public int compare(Object o, Object c ) {
                return ((StateLookup) o).id.compareTo( ((StateLookup) c).id );
            }
            
        });
        binding.setLeft();
    }

    public void execute(BoundWidget model) {
    }

   
}
