/*
 * ContactBrowserAction.java
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.table.BoundTable;
import com.totsp.gwittir.client.ui.table.Column;
import com.totsp.gwittir.client.ui.table.HasChunks;
import com.totsp.gwittir.client.ui.table.SortableDataProvider;
import com.totsp.gwittir.example.client.remote.Contact;
import com.totsp.gwittir.example.client.remote.Services;
import java.util.Collection;

/**
 *
 * @author cooper
 */
public class ContactBrowserAction implements BindingAction {
    
    
    Column[] tableCols = new Column[3];
    private boolean initted = false;
    private boolean loaded = false;
    
    /** Creates a new instance of ContactBrowserAction */
    public ContactBrowserAction() {
        tableCols[0] = new Column("lastName", "Last Name");
        tableCols[1] = new Column("firstName", "First Name");
        tableCols[2] = new Column("notes", "Notes");
        
        
        
    }
    
    public void bind(BoundWidget widget) {
        if( widget instanceof ContactBrowser ){
            
        }
        
    }
    
    public void unbind(BoundWidget widget) {
    }
    
    public void set(BoundWidget widget) {
        if( widget instanceof ContactBrowser && ! initted ){
            ContactBrowser browser = (ContactBrowser) widget;
            browser.contacts.setColumns(tableCols);
            browser.contacts.setDataProvider( new ContactDataProvider() );
            initted = true;
        }
        
        
    }
    
    public void execute(BoundWidget model) {
        if( model instanceof BoundTable ){
            BoundTable contacts = (BoundTable) model;
            Contact c = (Contact) contacts.getSelected().get(0);
            FlowController.call( contacts, "contactEdit", c);
        } if( model instanceof Button ){
            Contact c = new Contact();
            FlowController.call( (Button) model, "contactEdit", c);
        }
    }
    
    private class ContactDataProvider implements SortableDataProvider {
        public void getChunk(final HasChunks table, final int chunkNumber) {
            GWT.log( "Getting from "+ chunkNumber *100, null);
            Services.CONTACTS.findContacts( 100, chunkNumber * 100, "lastName", true, new AsyncCallback(){
                public void onSuccess(Object result) {
                    table.setChunk( (Collection) result );
                }
                
                public void onFailure(Throwable caught) {
                    Window.alert("Failure "+ caught);
                }
                
            });
        }
        
        public void sortOnProperty(final HasChunks table, final String propertyName, final boolean ascending) {
            Services.CONTACTS.findContacts( 100, 0, propertyName, ascending, new AsyncCallback(){
                public void onSuccess(Object result) {
                    table.init( (Collection) result, 2 );
                }
                
                public void onFailure(Throwable caught) {
                    Window.alert("Failure "+ caught);
                }
                
            });
            
        }
        
        public void init(final HasChunks table) {
            if( !loaded ){
                Services.CONTACTS.findContacts( 100, 0, "lastName", true, new AsyncCallback(){
                    public void onSuccess(Object result) {
                        table.init( (Collection) result, 2 );
                    }
                    
                    public void onFailure(Throwable caught) {
                        Window.alert("Failure "+ caught);
                    }
                    
                });
                loaded = true;
            }
        }
        
        public String[] getSortableProperties() {
            String[] props = { "firstName", "lastName" };
            return props;
        }
        
    }
    
}
