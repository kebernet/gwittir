/*
 * UndoableBindingAction.java
 *
 * Created on April 12, 2007, 12:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir;


/**
 *
 * @author cooper
 */
public interface UndoableBindingAction {
    public void redo();

    public void undo();
}
