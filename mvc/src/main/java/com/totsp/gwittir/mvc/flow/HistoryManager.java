/*
 * HistoryManager.java
 *
 * Created on June 27, 2007, 11:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.mvc.flow;


/** A HistoryManager is a class that parses history tokens and logs transitions
 * to control the global state of the FlowController.
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface HistoryManager {
	
	/**
	 * Applies a state on the historyToken
	 * @param historyToken the history token to set.
	 */
    public void apply(String historyToken);

    /**
     * Called when the flow controller transitions between states.
     * @param name name of the new state
     * @param old the old BoundWidget being transitioned from
     * @param current the new BoundWidget being transitioned to.
     */
    public void transition(FlowEvent event, boolean appendHistory);
}
