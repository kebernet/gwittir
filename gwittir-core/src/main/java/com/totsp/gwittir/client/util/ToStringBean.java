package com.totsp.gwittir.client.util;

import com.google.gwt.core.client.GWT;

import com.totsp.gwittir.client.beans.BeanDescriptor;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Method;
import com.totsp.gwittir.client.beans.Property;

import java.io.Serializable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;


/**
 * Bean to introspect properties and provide toString() in GWT environ.
 * Inspired by the Rome bean utils -
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
public class ToStringBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object bean;
    private Stack stack;

    public ToStringBean(Object bean) {
        this.bean = bean;
        this.stack = new Stack();
    }

    public String toString() {
        String[] tsInfo = (String[]) ((stack.isEmpty()) ? null : stack.peek());
        String prefix;

        if (tsInfo == null) {
            String className = GWT.getTypeName(bean);
            prefix = className.substring(className.lastIndexOf(".") + 1);
        } else {
            prefix = tsInfo[0];
            tsInfo[1] = prefix;
        }

        return toString(prefix);
    }

    private String toString(String prefix) {
        StringBuffer sb = new StringBuffer(128);

        try {
            BeanDescriptor bd = Introspector.INSTANCE.getDescriptor(bean);
            Property[] properties = bd.getProperties();

            if (properties != null) {
                for (int i = 0; i < properties.length; i++) {
                    String pName = properties[i].getName();
                    Property p = bd.getProperty(pName);
                    Method m = p.getAccessorMethod();

                    if (m != null) { // ensure it has a getter method

                        Object value = m.invoke(bean, null);
                        printProperty(sb, prefix + "." + pName, value);
                    }
                }
            }
        } catch (Exception ex) {
            sb.append("\n\nEXCEPTION: unable to process " + prefix +
                ".toString(): " + ex.getMessage() + "\n");
        }

        return sb.toString();
    }

    private void printProperty(StringBuffer sb, String prefix, Object value) {
        if (value == null) {
            sb.append(prefix).append("=null\n");
        } else if (ArrayUtils.isArray(value)) {
            printArrayProperty(sb, prefix, value);
        } else if (value instanceof Map) {
            Map map = (Map) value;
            Iterator i = map.entrySet().iterator();

            if (i.hasNext()) {
                while (i.hasNext()) {
                    Map.Entry me = (Map.Entry) i.next();
                    String ePrefix = prefix + "[" + me.getKey() + "]";
                    Object eValue = me.getValue();

                    //NEW
                    String[] tsInfo = new String[2];
                    tsInfo[0] = ePrefix;
                    stack.push(tsInfo);

                    String s = (eValue != null) ? eValue.toString() : "null";
                    stack.pop();

                    if (tsInfo[1] == null) {
                        sb.append(ePrefix).append("=").append(s).append("\n");
                    } else {
                        sb.append(s);
                    }
                }
            } else {
                sb.append(prefix).append("=[]\n");
            }
        } else if (value instanceof Collection) {
            Collection collection = (Collection) value;
            Iterator i = collection.iterator();

            if (i.hasNext()) {
                int c = 0;

                while (i.hasNext()) {
                    String cPrefix = prefix + "[" + (c++) + "]";
                    Object cValue = i.next();

                    //NEW
                    String[] tsInfo = new String[2];
                    tsInfo[0] = cPrefix;
                    stack.push(tsInfo);

                    String s = (cValue != null) ? cValue.toString() : "null";
                    stack.pop();

                    if (tsInfo[1] == null) {
                        sb.append(cPrefix).append("=").append(s).append("\n");
                    } else {
                        sb.append(s);
                    }
                }
            } else {
                sb.append(prefix).append("=[]\n");
            }
        } else {
            String[] tsInfo = new String[2];
            tsInfo[0] = prefix;
            stack.push(tsInfo);

            String s = value.toString();
            stack.pop();

            if (tsInfo[1] == null) {
                sb.append(prefix).append("=").append(s).append("\n");
            } else {
                sb.append(s);
            }
        }
    }

    private void printArrayProperty(StringBuffer sb, String prefix, Object array) {
        int length = ArrayUtils.getArrayLength(array);

        for (int i = 0; i < length; i++) {
            Object obj = ArrayUtils.getArrayElement(array, i);
            printProperty(sb, prefix + "[" + i + "]", obj);
        }
    }
}
