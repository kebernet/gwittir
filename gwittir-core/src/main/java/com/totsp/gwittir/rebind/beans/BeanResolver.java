/*
 * BeanResolver.java
 *
 * Created on July 21, 2007, 4:47 PM
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.totsp.gwittir.rebind.beans;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;

import com.totsp.gwittir.client.beans.annotations.Omit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class BeanResolver {
    private HashMap<String, Property> properties = new HashMap<String, Property>(); /*<String name, Property>*/
    private HashSet<MethodWrapper> methodSet = new HashSet<MethodWrapper>(); /*<MethodWrapper>*/
    private JClassType type;
    private TreeLogger logger;
    private String[] filterProperties;

    public BeanResolver(TreeLogger logger, JClassType type, String[] filterProperties) {
        this(logger, type);
        this.filterProperties = filterProperties;
    }

    /** Creates a new instance of BeanResolver */
    public BeanResolver(TreeLogger logger, JClassType type) {
        this.type = type;
        this.logger = logger;
        this.logger = logger.branch(TreeLogger.DEBUG, "Inspecting type: " + type.getQualifiedSourceName(), null);
        buildMethods(type);
        examineGetters();
        examineSetters();
        logger.log(TreeLogger.DEBUG, "" + methodSet.size(), null);

        for (Iterator it = properties.keySet()
                                     .iterator(); it.hasNext();) {
            logger.log(TreeLogger.DEBUG, (String) it.next(), null);
        }

        for (Iterator it = methodSet.iterator(); it.hasNext();) {
            MethodWrapper w = (MethodWrapper) it.next();
            logger.log(TreeLogger.DEBUG, w.getDeclaringType().getQualifiedSourceName() + " " + w.toString(), null);
        }
    }

    public JClassType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BeanResolver) {
            return ((BeanResolver) object).getType()
                    .getQualifiedSourceName()
                    .equals(this.getType().getQualifiedSourceName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getType()
                   .getQualifiedSourceName()
                   .hashCode();
    }

    @Override
    public String toString() {
        return this.getType()
                   .getQualifiedSourceName();
    }

    Map<String, Property> getProperties() {
        if (this.filterProperties != null) {
            Map<String, Property> results = new HashMap<String, Property>();

            for (String property : this.filterProperties) {
                property = property.trim();

                if (this.properties.containsKey(property)) {
                    results.put(property, this.properties.get(property));
                }
            }

            return results;
        }

        return this.properties;
    }

    private void buildMethods(JClassType type) {
        JMethod[] methods = type.getMethods();
        logger = logger.branch(TreeLogger.DEBUG, type.getQualifiedSourceName() + " " + type.getMethods().length, null);

        for (int i = 0; i < methods.length; i++) {
            if (!methods[i].isPublic()) {
                continue;
            }

            MethodWrapper w = new MethodWrapper(type, methods[i]);

            if (methods[i].getAnnotation(Omit.class) != null) {
                methodSet.remove(w);
            } else {
                logger.log(TreeLogger.DEBUG, w.getBaseMethod().getReadableDeclaration(), null);
                methodSet.add(w);
            }
        }

        JClassType[] interfaces = type.getImplementedInterfaces();

        for (int i = 0; i < interfaces.length; i++) {
            buildMethods(interfaces[i]);
        }

        if (type.getSuperclass() != null) {
            buildMethods(type.getSuperclass());
        } else {
            logger.log(TreeLogger.DEBUG, "no supertype", null);
        }
    }

    private void examineGetters() {
        for (Iterator it = methodSet.iterator(); it.hasNext();) {
            MethodWrapper w = (MethodWrapper) it.next();
            String methodName = w.getBaseMethod()
                                 .getName();
            Property p = null;

            if (
                methodName.startsWith("get") && (methodName.length() >= 4) &&
                    (methodName.charAt(3) == methodName.toUpperCase()
                                                           .charAt(3))) {
                p = new Property();
                p.setReadMethod(w);
                p.setName(
                    methodName.substring(3, 4).toLowerCase() +
                    ((methodName.length() > 4) ? methodName.substring(4, methodName.length())
                                               : ""));
            } else if (
                methodName.startsWith("is") && (methodName.length() >= 3) &&
                    (methodName.charAt(2) == methodName.toUpperCase()
                                                           .charAt(2))) {
                p = new Property();
                p.setReadMethod(w);
                p.setName(
                    methodName.substring(2, 3).toLowerCase() +
                    ((methodName.length() > 3) ? methodName.substring(3, methodName.length())
                                               : ""));
            }

            if (p == null) {
                continue;
            }

            p.setType(w.getBaseMethod().getReturnType());
            logger.log(TreeLogger.DEBUG, "Found new property: " + p.getName(), null);
            properties.put(p.getName(), p);
        }
    }

    private void examineSetters() {
        for (Iterator it = methodSet.iterator(); it.hasNext();) {
            MethodWrapper w = (MethodWrapper) it.next();
            String methodName = w.getBaseMethod()
                                 .getName();

            if (
                methodName.startsWith("set") && (methodName.length() >= 4) &&
                    (methodName.charAt(3) == methodName.toUpperCase()
                                                           .charAt(3))) {
                String name = methodName.substring(3, 4)
                                        .toLowerCase() +
                    ((methodName.length() > 4) ? methodName.substring(4, methodName.length())
                                               : "");

                Property p = (properties.containsKey(name) ? (Property) properties.get(name)
                                                           : new Property());
                p.setName(name);
                p.setWriteMethod(w);

                if (
                    (p.getType() == null) && (w.getBaseMethod()
                                                   .getParameters() != null) &&
                        (w.getBaseMethod().getParameters().length > 0)) {
                    p.setType(
                        w.getBaseMethod()
                         .getParameters()[w.getBaseMethod()
                                           .getParameters().length - 1].getType());
                }

                if (logger.isLoggable(TreeLogger.DEBUG)) {
                    if (properties.get(p.getName()) == null) {
                        logger.log(TreeLogger.DEBUG, "Found new property on setter: " + p.getName(), null);
                    }
                }

                properties.put(p.getName(), p);
            }
        }
    }
}
