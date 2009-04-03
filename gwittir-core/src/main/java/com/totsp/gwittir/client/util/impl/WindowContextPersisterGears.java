/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import com.totsp.gwittir.client.util.WindowContextItem;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public class WindowContextPersisterGears extends WindowContextPersister {

    @Override
    public int getByteLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Map<String, String> getWindowContextData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeWindowContextData(Map<String, WindowContextItem> windowContextData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
