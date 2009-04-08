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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.totsp.gwittir.client.util.impl.WindowContextPersister;

/**
 * 
 * @author kebernet
 */
public class WindowContext {

	public static final WindowContext INSTANCE = new WindowContext();
	private final Map<String, String> data = new HashMap<String, String>();
	private final WindowContextPersister persister = (WindowContextPersister) GWT
			.create(WindowContextPersister.class);
	private final WindowCloseListener wcl = new WindowCloseListener() {
		public String onWindowClosing() {
			flush();
			return null;
		}

		public void onWindowClosed() {

		}
	};

	private WindowContext() {
		super();
		Window.addWindowCloseListener(wcl);
	}

	public void flush() {
		persister.storeWindowContextData(data);
	}

	public void initialize(final WindowContextCallback callback) {
		WindowContextCallback internalCallback = new WindowContextCallback() {

			public void onInitialized() {
				data.putAll(persister.getWindowContextData());
				callback.onInitialized();
			}

		};
		persister.init(internalCallback);
	}

	public String get(String key) {
		return data.get(key);
	}

	public void put(String key, String item) {
		data.put(key, item);
	}

	public static interface WindowContextCallback {

		void onInitialized();
	}

}
