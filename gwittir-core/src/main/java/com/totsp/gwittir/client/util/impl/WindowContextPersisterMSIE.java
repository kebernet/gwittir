/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;
import com.totsp.gwittir.client.util.WindowContextItem;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public class WindowContextPersisterMSIE extends AbstractWindowContextPersister {

    @Override
    public int getByteLimit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> getWindowContextData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeWindowContextData(Map<String, WindowContextItem> windowContextData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(WindowContextCallback listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
