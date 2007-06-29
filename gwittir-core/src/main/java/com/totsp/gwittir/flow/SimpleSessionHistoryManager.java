/*
 * SimpleSessionHistoryManager.java
 *
 * Created on June 27, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.flow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.ui.BoundWidget;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rcooper
 */
public class SimpleSessionHistoryManager extends AbstractHistoryManager{
    
    private int currentState = -1;
    private ArrayList states = new ArrayList();
    private boolean ignoreNext = false;
    
    
    /** Creates a new instance of SimpleSessionHistoryManager */
    public SimpleSessionHistoryManager() {
    }
    
    public void transition(String name, BoundWidget old, BoundWidget current) {
        if( ignoreNext ) return;
        State state = new State();
        state.context = FlowController.findContextWidget( (Widget) current, name );
        state.name = name;
        state.model = current.getModel();
        currentState++;
        while( states.size() < currentState +1 ){
            states.add(null);
        }
        states.set( currentState, state );
        GWT.log( "Transitioning to state: "+currentState+" of "+states.size(), null );
        HashMap values = this.parseHistoryToken( History.getToken() );
        values.put( "s", new Integer(currentState) );
        History.newItem( this.generateHistoryToken( values ) );
    }
    
    public void apply(String historyToken) {
        HashMap values = this.parseHistoryToken( historyToken );
        if( values.get("s") == null || Integer.parseInt( values.get("s").toString() ) == currentState ){
            return;
        }
        int targetState = Integer.parseInt( values.get("s").toString() );
        GWT.log( "Repositioning to state: "+targetState+" of "+states.size()+"from "+currentState, null );
        for( int i = currentState;
                i != targetState; 
                i = (i > targetState )? i-1 : i+1 ){
            
            GWT.log("\tCalling intermediate state "+i, null);
            callState( i );
        }
        callState( targetState );
            
        currentState = targetState;
    }
    
    private void callState( int index ){
        State state = (State) states.get(index);
        ignoreNext = true;
        FlowController.call( state.context, state.name, state.model );
        ignoreNext = false;
    }
    
    private static class State {
        Widget context;
        String name;
        Object model;
    }
    
}
