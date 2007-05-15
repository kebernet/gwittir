/*
 * BindingAction.java
 *
 * Created on April 12, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir;

import com.totsp.gwittir.ui.BoundWidget;

/**
 *
 * @author cooper
 */
public interface BindingAction extends Action{
    
    public void bind(BoundWidget widget);
    public void unbind(BoundWidget widget);
    public void set(BoundWidget widget);
}
