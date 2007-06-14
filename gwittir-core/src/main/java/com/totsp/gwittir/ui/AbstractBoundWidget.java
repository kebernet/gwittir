/*
 * AbstractBoundWidget.java
 *
 * Created on June 14, 2007, 9:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.Composite;
import com.totsp.gwittir.Action;
import com.totsp.gwittir.BindingAction;

/**
 *
 * @author rcooper
 */
public abstract class AbstractBoundWidget extends Composite implements BoundWidget{
    
    private Renderer rendered;
    private Object model;
    private Action action;
    
    /** Creates a new instance of AbstractBoundWidget */
    public AbstractBoundWidget() {
    }

    public Renderer getRendered() {
        return rendered;
    }

    public void setRendered(Renderer rendered) {
        this.rendered = rendered;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
        if( this.action instanceof BindingAction ){
            ((BindingAction)action).set( this );
        }
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    protected void onDetach() {
        super.onDetach();
        if( this.action instanceof BindingAction ){
            ((BindingAction)action).unbind( this );
        }
    }

    protected void onAttach() {
        super.onAttach();
        if( this.action instanceof BindingAction ){
            ((BindingAction)action).bind( this );
        }
    }
    
}
