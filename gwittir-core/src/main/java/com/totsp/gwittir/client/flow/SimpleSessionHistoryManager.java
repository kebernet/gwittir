/*
 * SimpleSessionHistoryManager.java
 *
 * Created on June 27, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.flow;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;

import com.totsp.gwittir.client.ui.BoundWidget;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class SimpleSessionHistoryManager extends AbstractHistoryManager {
    private ArrayList states = new ArrayList();
    private boolean ignoreNext = false;
    private int currentState = -1;

    /** Creates a new instance of SimpleSessionHistoryManager */
    public SimpleSessionHistoryManager() {
    }

    public void apply(String historyToken) {
        HashMap values = this.parseHistoryToken(historyToken);

        if((values.get("s") == null) ||
                (Integer.parseInt(values.get("s").toString()) == currentState)) {
            return;
        }

        int targetState = Integer.parseInt(values.get("s").toString());
        Logger.getAnonymousLogger().log( Level.SPAM,"Repositioning to state: " + targetState + " of " +
            states.size() + "from " + currentState, null);

        for(int i = currentState; i != targetState;
                i = (i > targetState) ? (i - 1) : (i + 1)) {
            Logger.getAnonymousLogger().log( Level.SPAM,"\tCalling intermediate state " + i, null);
            callState(i);
        }

        callState(targetState);

        currentState = targetState;
    }

    private void callState(int index) {
        State state = (State) states.get(index);
        ignoreNext = true;
        FlowController.call(state.context, state.name, state.model);
        ignoreNext = false;
    }

    public void transition(String name, BoundWidget old, BoundWidget current) {
        if(ignoreNext) {
            return;
        }

        State state = new State();
        state.context = FlowController.findContextWidget((Widget) current, name);
        state.name = name;
        state.model = current.getModel();
        currentState++;

        while(states.size() < (currentState + 1)) {
            states.add(null);
        }

        states.set(currentState, state);
        Logger.getAnonymousLogger().log( Level.INFO,"Transitioning to state: " + currentState + " of " +
            states.size(), null);

        HashMap values = this.parseHistoryToken(History.getToken());
        values.put("s", Integer.toString(currentState));
        History.newItem(this.generateHistoryToken(values));
    }

    private static class State {
        Object model;
        String name;
        Widget context;
    }
}
