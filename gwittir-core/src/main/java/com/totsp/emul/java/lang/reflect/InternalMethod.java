/*
 * InternalMethod.java
 *
 * Created on May 17, 2007, 12:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.emul.java.lang.reflect;

/**
 *
 * @author rcooper
 */
public abstract class InternalMethod {
    private Class declaringClass;
    private String name;
    private int modifiers;
    private Class[] exceptionTypes;
    private Class[] parameterTypes;
    private Class returnType;
    private int hashCode;
    
    
    /** Creates a new instance of InternalMethod */
    public InternalMethod() {
    }

    public Class getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(Class declaringClass) {
        this.declaringClass = declaringClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public Class[] getExceptionTypes() {
        return exceptionTypes;
    }

    public void setExceptionTypes(Class[] exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public String toString() {
        String retValue;
        
        retValue = super.toString();
        return retValue;
    }

    public int hashCode() {
       return hashCode;
    }
    
    public abstract Object invoke( Object target, Object[] args );
    
}
