/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import java.util.Map;

import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;

/**
 * This is a persistence mechanism for newer versions of WebKit based on the
 * HTML SQL storage:
 *
 * http://webkit.org/misc/DatabaseExample.html
 *
 * @author kebernet
 */
public class WindowContextPersisterHTML5 extends AbstractWindowContextPersister {

    @Override
    public int getByteLimit() {
        return 512000;
    }

    @Override
    public Map<String, String> getWindowContextData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeWindowContextData(Map<String, String> windowContextData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(WindowContextCallback listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    



}
