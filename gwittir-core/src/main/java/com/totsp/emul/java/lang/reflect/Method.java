/*
 * Method.java
 *
 * Created on May 17, 2007, 12:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.emul.java.lang.reflect;

/**
 *
 * @author rcooper
 */
public final class Method {
    private InternalMethod internal;
    Method( InternalMethod internal ){
        this.internal = internal;
    }
    public Class getDeclaringClass() {
        return internal.getDeclaringClass();
    }
    
    
    public String getName() {
        return internal.getName();
    }
    
    public int getModifiers() {
        return internal.getModifiers();
    }
    
    public Class[] getExceptionTypes() {
        return internal.getExceptionTypes();
    }
    
    
    public Class[] getParameterTypes() {
        return internal.getParameterTypes();
    }
    
    
    public Class getReturnType() {
        return internal.getReturnType();
    }
    
    
    public String toString() {
        return internal.toString();
    }
    
    public int hashCode() {
        return internal.hashCode();
    }
    
    public Object invoke( Object target, Object[] args ){
        return internal.invoke(target, args);
    }
}
