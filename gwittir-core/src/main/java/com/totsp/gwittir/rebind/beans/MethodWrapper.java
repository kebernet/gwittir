/*
 * Method.java
 *
 * Created on July 21, 2007, 4:36 PM
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

import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class MethodWrapper implements Comparable{
    
    private JType declaringType;
    private JMethod baseMethod;
    boolean hashWithType = false;
    
    /** Creates a new instance of Method */
    public MethodWrapper(JType declaringType, JMethod baseMethod) {
        if( declaringType == null ){
            throw new NullPointerException("declaringType cannot be null.");
        }
        if( baseMethod == null ){
            throw new NullPointerException("baseMethod cannot be null.");
        }
        this.declaringType = declaringType;
        this.baseMethod = baseMethod;
    }
    
    public JType getDeclaringType() {
        return declaringType;
    }
    
    public JMethod getBaseMethod() {
        return baseMethod;
    }
    
    @Override
    public String toString(){
        return ( hashWithType ? this.declaringType.getQualifiedSourceName() : "" ) +
                this.baseMethod.getReadableDeclaration(false, true, false, true, true);
    }
    
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
    
    public int compareTo(Object o ){
        if( o == null ){
            return -1;
        }
        return this.toString().compareTo( o.toString() );
    }
    
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals( Object o ){
        return this.compareTo( o ) == 0;
    }
    
}
