/*
 * HistoryManager.java
 *
 * Created on June 27, 2007, 11:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.flow;


import com.totsp.gwittir.client.ui.BoundWidget;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface HistoryManager {
	
    public void apply(String historyToken);

    public void transition(String name, BoundWidget<?> old, BoundWidget<?> current);
}
