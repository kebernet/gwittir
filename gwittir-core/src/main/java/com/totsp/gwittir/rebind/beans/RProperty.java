/*
 * RProperty.java
 *
 * Created on July 21, 2007, 4:49 PM
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

import com.google.gwt.core.ext.typeinfo.JType;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class RProperty {
    private JType type;
    private MethodWrapper readMethod;
    private MethodWrapper writeMethod;
    private String jsonName;
    private String name;

    public RProperty() {
    }

    /**
     * Set the value of jsonName
     *
     * @param newjsonName new value of jsonName
     */
    public void setJsonName(String newjsonName) {
        this.jsonName = newjsonName;
    }

    /**
     * Get the value of jsonName
     *
     * @return the value of jsonName
     */
    public String getJsonName() {
        return this.jsonName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReadMethod(MethodWrapper readMethod) {
        this.readMethod = readMethod;
    }

    public MethodWrapper getReadMethod() {
        return readMethod;
    }

    public void setType(JType type) {
        this.type = type;
    }

    public JType getType() {
        return type;
    }

    public void setWriteMethod(MethodWrapper writeMethod) {
        this.writeMethod = writeMethod;
    }

    public MethodWrapper getWriteMethod() {
        return writeMethod;
    }

    @Override
    public String toString() {
        return "Property name:" + name + " type:" + type;
    }
}
