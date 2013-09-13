/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.introspection;

import com.google.gwt.core.shared.GWT;

/**
 *
 * @author kebernet
 */
public class IntrospectorFactory {


    private IntrospectorFactory(){

    };


    public static Introspector create(){
        if(GWT.isClient()){
            return GWT.create(Introspector.class);
        } else {
            return null;
        }
    }
}
