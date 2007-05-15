/*
 * KeyBinding.java
 *
 * Created on April 12, 2007, 3:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir;

/**
 *
 * @author cooper
 */
public class KeyBinding {
    
    private char key;
    private int modifiers;
    
    /** Creates a new instance of KeyBinding */
    public KeyBinding() {
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }
    
}
