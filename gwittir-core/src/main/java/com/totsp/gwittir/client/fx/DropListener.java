/*
 * DropListener.java
 *
 * Created on March 7, 2007, 9:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.fx;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface DropListener {
    
    public boolean onDrop( Widget dropped );
    public boolean onHover( Widget hovered );
}
