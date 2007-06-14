/*
 * AbstractBoundWidget.java
 *
 * Created on June 14, 2007, 9:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.totsp.gwittir.Action;
import com.totsp.gwittir.BindingAction;
import java.util.Comparator;

/**
 *
 * @author rcooper
 */
public abstract class AbstractBoundWidget extends Composite implements BoundWidget{
    
    private Renderer renderer;
    private Object model;
    private Action action;
    private Comparator comparator;
    private ChangeListenerCollection changeListeners = new ChangeListenerCollection();
    
    /** Creates a new instance of AbstractBoundWidget */
    public AbstractBoundWidget() {
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        if( this.getAction() instanceof BindingAction && this.getModel() != null ){
            ((BindingAction)getAction()).unbind( this );
        }
        this.model = model;
        if( this.getAction() instanceof BindingAction ){
            ((BindingAction)getAction()).set( this );
            if(this.isAttached() && this.getModel() != null)
                ((BindingAction)getAction()).bind( this );
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
        if( this.getAction() instanceof BindingAction && this.getModel() != null ){
            ((BindingAction)getAction()).unbind( this );
        }
        
    }

    protected void onAttach() {
        super.onAttach();
        if( this.getAction() instanceof BindingAction ){
            ((BindingAction)getAction()).bind( this );
        }
    }

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public void addChangeListener(ChangeListener listener) {
        changeListeners.add( listener );
    }

    public void removeChangeListener(ChangeListener listener) {
        changeListeners.remove( listener );
    }
    
    protected void fireChange(){
        changeListeners.fireChange( this );
    }
}
