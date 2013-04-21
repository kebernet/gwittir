/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.util.impl;

import com.totsp.gwittir.util.WindowContext;

import java.util.Map;

/**
 *
 * @author kebernet
 */
public interface WindowContextPersister {

    void init(WindowContext.WindowContextCallback listener);

    int getByteLimit();

    Map<String, String> getWindowContextData();

    void storeWindowContextData(Map<String, String> windowContextData);

}
