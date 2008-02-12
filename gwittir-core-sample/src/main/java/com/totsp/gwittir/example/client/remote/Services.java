/*
 * Services.java
 *
 * Created on August 5, 2007, 4:20 PM
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

package com.totsp.gwittir.example.client.remote;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author cooper
 */
public class Services {
    public static final ContactsRemoteServiceAsync CONTACTS = (ContactsRemoteServiceAsync) GWT.create( ContactsRemoteService.class );
    static {
       ((ServiceDefTarget) CONTACTS).setServiceEntryPoint(GWT.getModuleBaseURL()+"/ContactsRemoteService");
    }
    ///public static final LookupFreezer FREEZER = (LookupFreezer) GWT.create(LookupFreezer.class);
    
    
}
