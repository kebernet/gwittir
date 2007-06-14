/*
 * MethodDescriptor.java
 *
 * Created on May 17, 2007, 1:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.emul.java.beans;

import java.lang.reflect.Method;

/**
 *
 * @author rcooper
 */
public class MethodDescriptor extends FeatureDescriptor{
    private Method method;
    private ParameterDescriptor[] parameterDescriptors = null;
    /** Creates a new instance of MethodDescriptor */
    public MethodDescriptor(Method method) {
        this.method = method;
    }
    public MethodDescriptor(Method method, ParameterDescriptor[] parameterDescriptors ){
        this.method = method;
        this.parameterDescriptors = parameterDescriptors;
    }

    public Method getMethod() {
        return method;
    }

    public ParameterDescriptor[] getParameterDescriptors() {
        return parameterDescriptors;
    }
    
    
}
