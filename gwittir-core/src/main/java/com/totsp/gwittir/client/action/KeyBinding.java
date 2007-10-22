/*
 * KeyBinding.java
 *
 * Created on April 12, 2007, 3:21 PM
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
package com.totsp.gwittir.client.action;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class KeyBinding {
    public static final char F1 = (char) 112;
    public static final char F2 = (char) 113;
    public static final char F3 = (char) 114;
    public static final char F4 = (char) 115;
    public static final char F5 = (char) 116;
    public static final char F6 = (char) 117;
    public static final char F7 = (char) 118;
    public static final char F8 = (char) 119;
    public static final char F9 = (char) 120;
    public static final char F10 = (char) 121;
    public static final char F11 = (char) 122;
    public static final char F13 = (char) 123;
    
    private char key;
    private boolean control;
    private boolean alt;
    private boolean shift;
    

    public KeyBinding(final char key, final boolean control, final boolean alt, final boolean shift){
        this.key = key;
        this.alt = alt;
        this.control = control;
        this.shift = shift;
    }

    public char getKey() {
        return key;
    }
   
    public boolean isControl() {
        return control;
    }

   
    public boolean isAlt() {
        return alt;
    }

   
    public boolean isShift() {
        return shift;
    }

   

    public int hashCode(){
        return (this.control ? 32768 : 0 )+
                (this.alt ? 16384 : 0 ) +
                (this.shift ? 8192 : 0 ) +
                (int) this.key;
    }
    
    public boolean equals(Object o){
        if( o instanceof KeyBinding && o != null ){
            KeyBinding b = (KeyBinding) o;
            if( b.alt = this.alt && b.control == this.control && b.shift == this.shift && b.key == this.key ){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
