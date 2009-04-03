/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.impl;

import com.google.gwt.user.client.rpc.SerializationStreamFactory;

/**
 *
 * @author kebernet
 */
public interface WindowContextSerializers {

    public abstract SerializationStreamFactory getFactory(Object object);
    public abstract SerializationStreamFactory getFactory(Class clazz);

}
