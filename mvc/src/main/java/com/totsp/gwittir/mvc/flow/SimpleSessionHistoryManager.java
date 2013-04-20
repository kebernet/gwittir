/*
 * SimpleSessionHistoryManager.java
 *
 * Created on June 27, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.mvc.flow;

import java.util.ArrayList;

import com.totsp.gwittir.mvc.log.Level;
import com.totsp.gwittir.mvc.log.Logger;
import com.totsp.gwittir.mvc.util.HistoryTokenizer;

/**
 * A simple history manager that creates an inline List for each state change
 * in the flow controller.
 * 
 * This class will provide basic back-forward support without deep linking.
 * 
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet"
 *         Cooper</a>
 */
public class SimpleSessionHistoryManager implements HistoryManager {
	private static final Logger LOGGER = Logger.getLogger(SimpleSessionHistoryManager.class.toString());
	private ArrayList<FlowEvent> states = new ArrayList<FlowEvent>();
	private boolean ignoreNext = false;
	private int currentState = -1;
	
	/** Creates a new instance of SimpleSessionHistoryManager */
	public SimpleSessionHistoryManager() {
	}

	public void apply(String historyToken) {
		LOGGER.log(Level.DEBUG, "Apply "+historyToken, null);
		HistoryTokenizer tok = new HistoryTokenizer(historyToken);

		if ((tok.getToken("s") == null)
				|| (Integer.parseInt(tok.getToken("s").toString()) == currentState)) {
			return;
		}

		int targetState = Integer.parseInt(tok.getToken("s").toString());
		LOGGER.log(
				Level.SPAM,
				"Repositioning to state: " + targetState + " of "
						+ states.size() + "from " + currentState, null);
		if(targetState >= states.size() ){
			tok.setToken("s", ""+(states.size() -1));
			return;
		}
		callState(targetState);
		currentState = targetState;
	}
	
	private void callState(int index) {
		ignoreNext = true;
		if(currentState > index ){
			for(int i = currentState; i > index;	i--){
				FlowEvent state = states.get(i);
				LOGGER.log(Level.DEBUG, "calling "+state.getFromName(), null);
				FlowController.call( state.getManagedWidget(), 
						state.getFromName(), 
						state.getFromModel() );
			}
		} else {
			for(int i = currentState+1; i <= index; i++ ){
				FlowEvent state = states.get(i);
				FlowController.call( state.getManagedWidget(), 
						state.getToName(), 
						state.getToModel() );
			}
		}
		ignoreNext = false;
			
	}

	public void transition(FlowEvent event, boolean appendHistory) {
		if(ignoreNext)
			return;
		currentState++;
		if( currentState == states.size() ){
			states.add(event);
		} else {
			states.set(currentState, event);
		}
		if(appendHistory){
			HistoryTokenizer tok = new HistoryTokenizer();
			tok.begin();
			tok.setToken("s", Integer.toString(currentState));
			tok.commit();
		}
	}



}
