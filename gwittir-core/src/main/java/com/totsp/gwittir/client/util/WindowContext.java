/*
 * Created on August 3, 2007, 3:40 PM
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
package com.totsp.gwittir.client.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;
import com.totsp.gwittir.client.util.impl.WindowContextPersister;
import com.totsp.gwittir.client.util.impl.WindowContextPersisterMacShell;

/**
 * The WindowContext class is a front end for abstract client storage, allowing for the storage of
 * key/value pairs that persist on the client between browser sessions.
 * 
 * It is built on top of the other persistence systems in the client.util package and fails down (with
 * availablility from Gears to MSIE UserData, to FireFox/Opera DOMStorage (and in the future to Flash LSO).
 * 
 * @author <a href="cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class WindowContext {

	/** 
	 * The static instance of the WindowContext object.
	 */
	private static final Logger LOG = Logger.getLogger(WindowContext.class.getName() );
	public static final WindowContext INSTANCE = new WindowContext();
	private final Map<String, String> data = new HashMap<String, String>();
	private WindowContextPersister persister = (WindowContextPersister) GWT
			.create(WindowContextPersister.class);
	private boolean initialized = false;
	private int flushed = 0;
	private final WindowCloseListener wcl = new WindowCloseListener() {
		public String onWindowClosing() {
			flush();
			flushed = 1;
			return null;
		}

		public void onWindowClosed() {

		}
	};

	private WindowContext() {
		super();
		Window.addWindowCloseListener(wcl);
		Window.setStatus(this.instrumentModuleFrame());
				
	}

	private native String instrumentModuleFrame() /*-{
		var instance = this;
		if($wnd != window){
			window.onunload = function(){
				try{
					//alert("flush");
					instance.@com.totsp.gwittir.client.util.WindowContext::flush()();
				} catch(e) {
					alert(e);
				}
			}
			
		}
		return $wnd.__gwittir_storage;
	}-*/;
	
	/** 
	 * Immediately writes the values in the context to whatever the persistent store is. This
	 * will happen automatically during onWindowClosing(); Final storage may or may not be synchronous
	 * to the call of the flush() method.
	 */
	public void flush() {
		if(flushed != 1){
			persister.storeWindowContextData(data);
			LOG.log(Level.INFO, "Flushed "+data.size()+" items.", null);
		}
	}

	/**
	 * Initializes the WindowContext. Takes in a call back that will be notified when the
	 * initialization procedure is complete. This will *always* take place off the execution 
	 * context of the call to initialize, whether the individual persistence mechanism is asynchronous
	 * or not.
	 * 
	 * Calls to initialize after the first will be ignored.
	 * 
	 * @param callback WindowContextCallback to notify when the WindowContext has been initialized. 
	 */
	public void initialize(final WindowContextCallback callback) {
		if(!GWT.isScript() && getUserAgent().indexOf("WebKit") != -1){
			this.persister = new WindowContextPersisterMacShell();
			LOG.log(Level.WARN, "***You are in hosted mode on Safari. Defaulting to the WindowName persister because HTML5 won't work in hosted mode!***", null);
		}
		LOG.log( Level.INFO, "Got persister :"+persister.getClass(), null);
		WindowContextCallback internalCallback = new WindowContextCallback() {

			public void onInitialized() {
				data.putAll(persister.getWindowContextData());
				initialized = true;
				LOG.log(Level.DEBUG, "Initialized.", null);
				callback.onInitialized();
			}

		};
		persister.init(internalCallback);
	}

	/**
	 * Gets the value of a key
	 * @param key The key value to lookup
	 * @return a value or null
	 */
	public String get(String key) {
		return data.get(key);
	}

	/**
	 * Inserts a new value into the dataset
	 * @param key the key to insert. Keys should contain ONLY alphanumeric characters.
	 * @param item the new value to insert.
	 */
	public void put(String key, String item) {
		assert key.matches("[a-zA-Z0-9]*") : "Illegal character in the key";
		LOG.log(Level.DEBUG, "set ["+key+":"+item+"]", null);
		data.put(key, item);
	}

	public Set<String> keySet(){
		return data.keySet();
	}
	
	/**
	 * An interface to implement to receive notification of when the WindowContext has been initialized.
	 * 
	 */
	public static interface WindowContextCallback {

		/**
		 * Called asynchronously when the WindowContext is ready for use.
		 */
		void onInitialized();
	}
	
	public static native String getUserAgent()
	/*-{return navigator.userAgent; }-*/;

	
	public boolean isInitialized(){
		return this.initialized;
	}
}
