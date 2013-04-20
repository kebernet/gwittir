/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.util.impl;

import java.util.Map;

import com.totsp.gwittir.mvc.util.WindowContext.WindowContextCallback;

/**
 *
 * @author kebernet
 */
public interface WindowContextPersister {

    void init(WindowContextCallback listener);

    int getByteLimit();

    Map<String, String> getWindowContextData();

    void storeWindowContextData(Map<String, String> windowContextData);

}
