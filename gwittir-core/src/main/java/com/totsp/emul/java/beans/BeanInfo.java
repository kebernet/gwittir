/*
 * BeanInfo.java
 *
 * Created on May 17, 2007, 1:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.emul.java.beans;

/**
 *
 * @author rcooper
 */
public interface BeanInfo {
    static int ICON_COLOR_16x16 = 0;
    static int ICON_COLOR_32x32 = 1;
    static int ICON_MONO_16x16 = 2;
    static int ICON_MONO_32x32 = 3;
    
    public BeanInfo[] getAdditionalBeanInfo();
    public BeanDescriptor getBeanDescriptor();
    public int getDefaultEventIndex();
    public int getDefaultPropertyIndex();
    //public EventDescriptor[] getEventDescriptors();
    //public Image getIcon();
    public MethodDescriptor[] getMethodDescriptors();
    public PropertyDescriptor[] getPropertyDescriptors();
    
}
