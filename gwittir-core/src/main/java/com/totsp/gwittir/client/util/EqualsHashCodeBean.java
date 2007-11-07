package com.totsp.gwittir.client.util;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.client.beans.BeanDescriptor;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Method;
import com.totsp.gwittir.client.beans.Property;

import java.io.Serializable;


/**
 * Bean to introspect properties and provide equals() and hashCode() in GWT
 * environ. Inspired by the Rome bean utils -
 * https://rome.dev.java.net/source/browse/rome/src/java/com/sun/syndication/feed/impl/.
 * Powered by the gwittir Introspection API.
 * 
 * Example:
 * 
 *  public String toString() {
 *       ToStringBean b = new ToStringBean(this);
 *
 *       return b.toString();
 *   }
 *
 *   public boolean equals(Object obj) {
 *       EqualsHashCodeBean b = new EqualsHashCodeBean(this);
 *
 *       return b.equals(obj);
 *   }
 * 
 * @see <code>com.totsp.gwittir.client.beans.AbstractModelBean</code>
 *
 * @author ccollins
 */
public class EqualsHashCodeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object bean;

    public EqualsHashCodeBean(Object bean) {
        this.bean = bean;
    }

    public boolean equals(Object obj) {
        return beanEquals(obj);
    }

    public boolean beanEquals(Object obj) {
        Object bean1 = bean;
        Object bean2 = obj;
        boolean eq;

        if (bean2 == null) {
            eq = false;
        } else if ((bean1 == null) && (bean2 == null)) {
            eq = true;
        } else if ((bean1 == null) || (bean2 == null)) {
            eq = false;
        } else if (!GWT.getTypeName(bean1).equals(GWT.getTypeName(bean2))) {
            eq = false;
        } else {
            eq = true;

            try {
                BeanDescriptor bd = Introspector.INSTANCE.getDescriptor(bean);
                Property[] properties = bd.getProperties();

                if (properties != null) {
                    for (int i = 0; eq && (i < properties.length); i++) {
                        Method pReadMethod = properties[i].getAccessorMethod();

                        if (pReadMethod != null) {
                            Object value1 = pReadMethod.invoke(bean1, null);
                            Object value2 = pReadMethod.invoke(bean2, null);
                            eq = doEquals(value1, value2);
                        }
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("Could not execute equals()", ex);
            }
        }

        return eq;
    }

    public int hashCode() {
        return beanHashCode();
    }

    public int beanHashCode() {
        return bean.toString().hashCode();
    }

    private boolean doEquals(Object obj1, Object obj2) {
        boolean eq = obj1 == obj2;

        if (!eq && (obj1 != null) && (obj2 != null)) {
            if (ArrayUtils.isArray(obj1) && ArrayUtils.isArray(obj2)) {
                eq = equalsArray(obj1, obj2);
            } else {
                eq = obj1.equals(obj2);
            }
        }

        return eq;
    }

    private boolean equalsArray(Object array1, Object array2) {
        boolean eq;
        int length1 = ArrayUtils.getArrayLength(array1);
        int length2 = ArrayUtils.getArrayLength(array2);

        if (length1 == length2) {
            eq = true;

            for (int i = 0; eq && (i < length1); i++) {
                Object e1 = ArrayUtils.getArrayElement(array1, i);
                Object e2 = ArrayUtils.getArrayElement(array2, i);
                eq = doEquals(e1, e2);
            }
        } else {
            eq = false;
        }

        return eq;
    }
}
