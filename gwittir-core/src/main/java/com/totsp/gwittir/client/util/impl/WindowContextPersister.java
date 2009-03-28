/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import com.totsp.gwittir.client.util.WindowContext.WindowContextItem;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public abstract class WindowContextPersister {

    public abstract int getByteLimit();

    public abstract Map<Class<? extends WindowContextItem>, WindowContextItem> getWindowContextData();

    public abstract void storeWindowContextData(Map<Class<? extends WindowContextItem>, WindowContextItem> windowContextData);

}
