/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import com.google.gwt.user.client.rpc.SerializationException;
import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;
import com.totsp.gwittir.client.util.WindowContextItem;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public interface WindowContextPersister {

    void init(WindowContextCallback listener);

    int getByteLimit();

    Map<String, String> getWindowContextData();

    void storeWindowContextData(Map<String, WindowContextItem> windowContextData);

}
