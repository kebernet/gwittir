/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.util.impl;

import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;

import java.util.Map;


/**
 *
 * @author kebernet
 */
public interface WindowContextPersister {
    int getByteLimit();

    Map<String, String> getWindowContextData();

    void init(WindowContextCallback listener);

    void storeWindowContextData(Map<String, String> windowContextData);
}
