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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;

import com.totsp.gwittir.client.util.impl.WindowContextPersister;
import com.totsp.gwittir.client.util.impl.WindowContextSerializers;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author kebernet
 */
public class WindowContext {

    public static final WindowContextSerializers SERIALIZERS = (WindowContextSerializers) GWT.create(WindowContextSerializers.class);


    public static final WindowContext INSTANCE = new WindowContext();
    private final Map<String, Class<?extends WindowContextItem>> classes = new HashMap<String, Class<?extends WindowContextItem>>();
    private final Map<String, WindowContextItem> items = new HashMap<String, WindowContextItem>();
    private final Map<String, String> serializedItems = new HashMap<String, String>();
    private final WindowContextPersister persister = (WindowContextPersister) GWT.create(WindowContextPersister.class);
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

    public void flush(){
        persister.storeWindowContextData(items);
    }

    public void initialize(final WindowContextCallback callback){
        WindowContextCallback internalCallback =new WindowContextCallback(){

            public void onInitialized() {
                serializedItems.putAll( persister.getWindowContextData() );
                callback.onInitialized();
            }

        };
        persister.init(internalCallback);
    }

    public WindowContextItem get(Class<?extends WindowContextItem> clazz,
        String key) {
        WindowContextItem item = items.get(key);

        if (item == null) {
            String raw = serializedItems.get(key);

            if (raw == null) {
                return null;
            }

            SerializationStreamFactory factory = SERIALIZERS.getFactory(clazz);
            Window.alert("RAW DATA:"+raw);
            try {
                item = (WindowContextItem) factory.createStreamReader(raw)
                                                  .readObject();
            } catch (SerializationException ex) {
                Window.alert(""+ex);
                return null;
            }

            items.put(key, item);
        }

        return item;
    }

    public void put(String key, WindowContextItem item) {
        items.put(key, item);
    }

    public static interface WindowContextCallback {

        void onInitialized();
    }

}
