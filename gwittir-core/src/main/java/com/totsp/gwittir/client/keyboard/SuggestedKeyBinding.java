/*
 * SuggestedKeyBinding.java
 *
 * Created on October 22, 2007, 4:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.keyboard;

import com.totsp.gwittir.client.action.KeyBinding;

/**
 *
 * @author rcooper
 */
public class SuggestedKeyBinding extends KeyBinding{
    /** Creates a new instance of SuggestedKeyBinding */
    public SuggestedKeyBinding(char key, boolean control, boolean alt, boolean shift ){
        super(key,control,alt, shift);
    }
    
    public int hashCode(){
        return super.hashCode() + 4096;
    }
}
