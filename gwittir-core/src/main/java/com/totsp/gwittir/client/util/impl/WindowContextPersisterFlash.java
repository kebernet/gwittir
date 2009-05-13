/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;
import com.totsp.gwittir.client.util.flashstorage.FlashStorage;
import com.totsp.gwittir.client.util.flashstorage.StartupCallback;

/**
 *
 * @author kebernet
 */
public class WindowContextPersisterFlash extends AbstractWindowContextPersister {

	private FlashStorage storage = null;
	@Override
	public int getByteLimit() {
		return 1048576;
	}

	@Override
	public Map<String, String> getWindowContextData() {
		return storage.getLocal("gwittir-windowcontext");
	}

	@Override
	public void init(final WindowContextCallback listener) {
		if(storage == null ){
			return;
		}
		FlashStorage.initialize(new StartupCallback(){

			public void onStart() {
				storage = FlashStorage.getInstance();
				GWT.log("FlashStorage initialized.", null);
				listener.onInitialized();
			}
			
		});
		
	}

	@Override
	public void storeWindowContextData(Map<String, String> windowContextData) {
		storage.setLocal("gwittir-windowcontext", windowContextData);
		storage.flushAll();
	}

	/*
	 * lashStorage.initialize(new StartupCallback(){

			public void onStart() {
				try{
				FlashStorage storage = FlashStorage.getInstance();
				Map<String, String> vals = storage.getLocal("gwittir");
				Window.alert(vals.get("test"));
				vals.put("test", "test");
				storage.setLocal("gwittir", vals);
				storage.flushAll();
				} catch(Exception e){
					GWT.log(null, e);
				}
			}
        	
        });
	 */
	
	
}
