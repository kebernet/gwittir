/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import com.totsp.gwittir.client.util.WindowContext.WindowContextItem;
import java.util.Map;

/**
 * This is a persistence mechanism for newer versions of WebKit based on the
 * HTML SQL storage:
 *
 * http://webkit.org/misc/DatabaseExample.html
 *
 * @author kebernet
 */
public class WindowContextPersisterHTML5 extends WindowContextPersister {

    @Override
    public int getByteLimit() {
        return 512000;
    }

    @Override
    public Map<Class<? extends WindowContextItem>, WindowContextItem> getWindowContextData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeWindowContextData(Map<Class<? extends WindowContextItem>, WindowContextItem> windowContextData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
