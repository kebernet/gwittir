/*
 * Copyright 2009 Robert "kebernet" Cooper
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
package com.totsp.gwittir.mvc.util.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.totsp.gwittir.mvc.log.Level;
import com.totsp.gwittir.mvc.log.Logger;
import com.totsp.gwittir.mvc.util.UnavailableException;
import com.totsp.gwittir.mvc.util.WindowContext.WindowContextCallback;
import com.totsp.gwittir.mvc.util.domstorage.DOMStorage;
import com.totsp.gwittir.mvc.util.domstorage.Storage;

/**
 *
 * @author kebernet
 */
public class WindowContextPersisterFirefox extends AbstractWindowContextPersister {

	private Storage local;
	
    @Override
    public int getByteLimit() {
        return 128000;
    }

    @Override
    public Map<String, String> getWindowContextData() {
        Map<String, String> results = new HashMap<String, String>();
        for( int i=0; i < local.length(); i++ ){
        	String key = local.key(i);
        	try{
        		String value= local.get(key);
        		results.put( key, value);
        	} catch(Exception e){
        		Logger.getAnonymousLogger().log(Level.WARN, "Exception reading key"+key, null);
        	}
        }
        return results;
    }

    @Override
    public void storeWindowContextData(Map<String, String> windowContextData) {
        for(Entry<String, String> entry : windowContextData.entrySet() ){
        	local.set(entry.getKey(), entry.getValue() );
        }
    }

    @Override
    public void init(final WindowContextCallback listener) {
    	try {
			local = DOMStorage.getLocal();
			DeferredCommand.addCommand( new Command(){
				public void execute() {
					listener.onInitialized();
				}
			});
			
		} catch (UnavailableException e) {
			Logger.getAnonymousLogger().log(Level.ERROR, "Unable to get DOMStorage session", e);
		}
    }

}
