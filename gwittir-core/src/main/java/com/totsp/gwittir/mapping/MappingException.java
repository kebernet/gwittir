/*
 * MappingException.java
 *
 * Created on November 29, 2006, 1:45 PM
 *
 *
 *  Copyright (C) 2006  Robert "kebernet" Cooper <cooper@screaming-penguin.com>
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

package com.totsp.gwittir.mapping;

/**
 *
 * @author cooper
 */
public class MappingException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>MappingException</code> without detail message.
     */
    public MappingException() {
    }
    
    
    /**
     * Constructs an instance of <code>MappingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MappingException(String msg) {
        super(msg);
    }
}
