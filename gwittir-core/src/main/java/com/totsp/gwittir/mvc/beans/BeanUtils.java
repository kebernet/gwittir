/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.beans;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.introspection.Introspector;

/**
 *
 * @author robert.cooper
 */
public class BeanUtils {

    public static Object readDottedProperty(String propertyNotation, Object target){
        int firstDot = propertyNotation.indexOf(".");
        String subProperty = null;
        String propertyName = null;
        if( firstDot != -1 ){
            subProperty = propertyNotation.substring(firstDot+1, propertyNotation.length());
            propertyName = propertyNotation.substring(0, firstDot);
        } else {
            propertyName = propertyNotation;
        }
        try{
            Object value = Introspector.INSTANCE.getDescriptor(target).getProperty(propertyName).getAccessorMethod().invoke(target, null);
            if(subProperty != null){
                return readDottedProperty(subProperty, value);
            } else {
                return value;
            }
        } catch(Exception e){
            GWT.log("Exception reading dotted property.", e);
            return null;
        }
    }

}
