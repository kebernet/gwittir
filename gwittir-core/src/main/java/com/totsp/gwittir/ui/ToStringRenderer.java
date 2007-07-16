/*
 * ToStringRenderer.java
 *
 * Created on April 12, 2007, 12:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

/**
 *
 * @author cooper
 */
public class ToStringRenderer implements Renderer {
    
    /** Creates a new instance of ToStringRenderer */
    public ToStringRenderer() {
    }

    public String render(Object o) {
        return o == null ? "" : o.toString();
    }
    
}
