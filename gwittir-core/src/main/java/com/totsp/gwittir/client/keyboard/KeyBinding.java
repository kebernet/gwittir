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
package com.totsp.gwittir.client.keyboard;

import com.totsp.gwittir.client.keyboard.KeyBindingEventListener;

import java.util.ArrayList;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class KeyBinding {
    private static final KeyBindingEventListener[] EMPTY_LISTENRS = new KeyBindingEventListener[0];
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
    public static final char F12 = (char) 123;
    public static final char DELETE = (char) 8;
    protected char key;
    protected boolean control;
    protected boolean alt;
    protected boolean shift;
    private ArrayList bindingListeners;

    public KeyBinding(
        final char key, final boolean control, final boolean alt,
        final boolean shift) {
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

    public String toString() {
        StringBuffer val = new StringBuffer();

        if(this.control) {
            val.append("CTRL+");
        }

        if(this.alt) {
            val.append("ALT+");
        }

        if(this.shift) {
            val.append("SHIFT+");
        }

        switch(this.key) {
            case KeyBinding.F1 :
                val.append("F1");

                break;

            case KeyBinding.F2 :
                val.append("F2");

                break;

            case KeyBinding.F3 :
                val.append("F3");

                break;

            case KeyBinding.F4 :
                val.append("F4");

                break;

            case KeyBinding.F5 :
                val.append("F5");

                break;

            case KeyBinding.F6 :
                val.append("F6");

                break;

            case KeyBinding.F7 :
                val.append("F7");

                break;

            case KeyBinding.F8 :
                val.append("F8");

                break;

            case KeyBinding.F9 :
                val.append("F9");

                break;

            case KeyBinding.F10 :
                val.append("F10");

                break;

            case KeyBinding.F11 :
                val.append("F11");

                break;

            case KeyBinding.F12 :
                val.append("F12");

                break;

            default :
                val.append(this.key);
        }

        return val.toString();
    }

    public int hashCode() {
        return (this.control ? 32768 : 0) + (this.alt ? 16384 : 0)
        + (this.shift ? 8192 : 0) + (int) this.key;
    }

    public boolean equals(Object o) {
        if(o instanceof KeyBinding && (o != null)) {
            KeyBinding b = (KeyBinding) o;

            if(
                (b.alt == this.alt) && (b.control == this.control)
                        && (b.shift == this.shift) && (b.key == this.key)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void addKeyBindingEventListener(KeyBindingEventListener l) {
        if(this.bindingListeners == null) {
            this.bindingListeners = new ArrayList();
        }

        this.bindingListeners.add(l);
    }

    public boolean removeKeyBindingEventListener(KeyBindingEventListener l) {
        if(this.bindingListeners == null) {
            return false;
        }

        return this.bindingListeners.remove(l);
    }

    public KeyBindingEventListener[] getKeyBindingEventListeners() {
        if(this.bindingListeners == null) {
            return KeyBinding.EMPTY_LISTENRS;
        }

        KeyBindingEventListener[] l = new KeyBindingEventListener[this.bindingListeners
            .size()];
        this.bindingListeners.toArray(l);

        return l;
    }
}
