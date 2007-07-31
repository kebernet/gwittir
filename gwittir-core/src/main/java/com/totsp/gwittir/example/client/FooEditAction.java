/*
 * FooEditAction.java
 *
 * Created on July 30, 2007, 4:48 PM
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

import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.ui.BoundWidget;

/**
 *
 * @author cooper
 */
public class FooEditAction implements BindingAction {
    Binding binding = new Binding();
    
    /** Creates a new instance of FooEditAction */
    public FooEditAction() {
    }
    
    public void bind(BoundWidget widget) {
        binding.bind();
    }
    
    public void unbind(BoundWidget widget) {
        binding.unbind();
    }
    
    public void set(BoundWidget widget) {
        if( binding.getChildren().size() > 0 ){
            binding.unbind();
            binding.getChildren().clear();
        }
        FooEdit edit = (FooEdit) widget;
        Foo model = (Foo) widget.getModel();
        binding.getChildren().add( new Binding( edit.intProperty, "value", model, "intProperty") );
        binding.getChildren().add( new Binding( edit.stringProperty, "value", model, "stringProperty") );
        binding.getChildren().add( new Binding( edit.booleanProperty, "value", model, "booleanProperty"));
        binding.setLeft();
        
    }
    
    public void execute(BoundWidget model) {
    }
    
}
