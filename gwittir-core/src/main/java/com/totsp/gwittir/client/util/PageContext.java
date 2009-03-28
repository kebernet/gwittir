/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util;

/**
 *
 * @author kebernet
 */
public class PageContext {



    public static interface PageContextListener {

        void onChange(Class clazz, Object oldValue, Object newValue );
    }


}
