/*
 * Member.java
 *
 * Created on May 17, 2007, 12:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.emul.java.lang.reflect;

/**
 *
 * @author rcooper
 */
public interface Member {
    
    static int DECLARED = java.lang.reflect.Member.DECLARED;
    static int PUBLIC = java.lang.reflect.Member.PUBLIC;
    
    
    public Class getDeclaringClass();
    public int getModifiers();
    public String getName();
}
