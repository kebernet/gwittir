/*
 * ContactsService.java
 *
 * Created on August 2, 2007, 8:18 PM
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

import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;

/**
 *
 * @author cooper
 */
public interface ContactsRemoteService extends RemoteService {
    /**
     * @gwt.typeArgs <com.totsp.gwittir.example.client.remote.Contact>
     */
    public List findContacts( int limit, int start, String orderBy, boolean ascending ) 
    throws ContactsRemoteException;
    
    public Contact saveContact( Contact contact ) throws ContactsRemoteException;
    
    /**
     * @gwt.typeArgs <com.totsp.gwittir.example.client.remote.StateLookup>
     */
    public List getStateLookups() throws ContactsRemoteException;
    
    /**
     * @gwt.typeArgs <com.totsp.gwittir.example.client.remote.TypeLookup>
     */
    public List getTypeLookups() throws ContactsRemoteException;
}
