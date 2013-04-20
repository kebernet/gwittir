/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.introspection;

import com.google.gwt.core.shared.GWT;
import com.totsp.gwittir.rebind.introspection.JVMIntrospector;

/**
 *
 * @author kebernet
 */
public class IntrospectorFactory {


    private IntrospectorFactory(){

    };


    public static Introspector create(){
        if(GWT.isScript()){
            //GWT.log("Using generated introspector.", null);
            return GWT.create(Introspector.class);
        } else {
            //System.out.println("Using JVMIntrospector");
            return new JVMIntrospector();
        }
    }
}
