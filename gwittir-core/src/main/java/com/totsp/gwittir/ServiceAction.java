/*
 * ServiceAction.java
 *
 * Created on April 12, 2007, 3:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir;


/**
 *
 * @author cooper
 */
public interface ServiceAction {
    public KeyBinding getKeyBinding();

    public void setKeyBinding(KeyBinding binding);
}
