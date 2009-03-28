/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

/**
 *
 * @author kebernet
 */
public abstract class WindowContextPersister {

    public abstract int getByteLimit();

    public abstract String getWindowContextData();

    public abstract void storeWindowContextData(String windowContextData);

}
