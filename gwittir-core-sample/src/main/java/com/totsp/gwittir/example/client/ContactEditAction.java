/*
 * ContactEditAction.java
 *
 * Created on August 5, 2007, 8:09 PM
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

import java.util.ArrayList;

import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.example.client.remote.Address;
import com.totsp.gwittir.example.client.remote.Contact;
import com.totsp.gwittir.example.client.remote.Phone;
import com.totsp.gwittir.example.client.remote.StateLookup;
import com.totsp.gwittir.example.client.remote.TypeLookup;

/**
 *
 * @author cooper
 */
public class ContactEditAction implements BindingAction
{

    private Binding binding = new Binding();

    /** Creates a new instance of ContactEditAction */
    public ContactEditAction()
    {
    }

    public void bind(BoundWidget widget)
    {
        binding.bind();
    }

    public void unbind(BoundWidget widget)
    {
        binding.unbind();
        binding.getChildren().clear();
    }

    public void set(BoundWidget widget)
    {
        final Contact c = (Contact) widget.getModel();
        final ContactEdit e = (ContactEdit) widget;
        binding.getChildren().add(new Binding(e.firstName, "value", c, "firstName"));
        binding.getChildren().add(new Binding(e.lastName, "value", c, "lastName"));
        binding.getChildren().add(new Binding(e.notes, "value", c, "notes"));
        binding.getChildren().add(new Binding(e.addresses, "value", c, "addresses"));
        binding.setLeft();

        e.newAddress.setAction(new Action() {

            public void execute(BoundWidget w)
            {
                final Address newAddress = new Address();

                TypeLookup type = new TypeLookup();
                type.id = new Integer(1);
                type.name = "home";
                StateLookup state = new StateLookup();
                state.id = new Integer(2);
                state.code = "AL";
                state.name = "Alabama";
                newAddress.setType(type);
                newAddress.setState(state);

                ArrayList list = new ArrayList();
                list.add(newAddress);
                System.out.println("newAddress - " + newAddress);
                e.addresses.add(newAddress);                
                //setSelected(list); // TODO setSelected causes errors, why are we using it here, and why does it err? 
            }
        });

        e.newPhone.setAction(new Action() {

            public void execute(BoundWidget w)
            {
                final Phone phone = new Phone();
                TypeLookup type = new TypeLookup();
                type.id = new Integer(1);
                type.name = "home";
                phone.setType(type);
                e.phoneNumbers.add(phone);
            }
        });
    }

    public void execute(BoundWidget model)
    {
    }
}
