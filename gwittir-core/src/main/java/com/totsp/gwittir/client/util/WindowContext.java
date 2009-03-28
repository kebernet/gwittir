/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util;

import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public class WindowContext {

    private final Map<Class, WindowContextItem> items = new HashMap<Class, WindowContextItem>();
    private final WindowCloseListener wcl = new WindowCloseListener(){

        public String onWindowClosing() {
            return null;
        }

        public void onWindowClosed() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    };

    private WindowContext(){
        super();
    }

    public WindowContextItem get(Class<? extends WindowContextItem> clazz){
        return items.get(clazz);
    }

    public void put(Class<? extends WindowContextItem> clazz, WindowContextItem item){
        items.put(clazz, item);
    }

    
    public static interface WindowContextItem extends IsSerializable {

    }

    public static interface WindowContextListener {

        void onStored();

        void onError(Throwable t);

        void onChange(Class<? extends WindowContextItem> clazz, WindowContextItem oldValue, WindowContextItem newValue );
    }


}
