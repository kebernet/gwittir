/*
 * TextBox.java
 *
 * Created on April 12, 2007, 12:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.Action;
import com.totsp.gwittir.BindingAction;
import java.util.Comparator;

/**
 *
 * @author cooper
 */
public class TextBox extends com.google.gwt.user.client.ui.TextBox implements BoundWidget {
    
    private Object model;
    private Action action;
    private Renderer renderer = new ToStringRenderer();
    private Comparator comparator;
    
    /** Creates a new instance of TextBox */
    public TextBox() {
        super();
        final BoundWidget bound = this;
        this.addChangeListener( new ChangeListener(){
            public void onChange(Widget sender) {
                action.execute( bound );
            }
            
        });
    }
    
    protected void onDetach() {
        super.onDetach();
        if( this.getAction() instanceof BindingAction ){
            ((BindingAction) this.getAction() ).unbind( this );
        }
    }
    
    protected void onAttach() {
        super.onAttach();
        if( this.getAction() instanceof BindingAction ){
            ((BindingAction) this.getAction() ).set( this );
            ((BindingAction) this.getAction() ).bind( this );
        }
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
    
    public Renderer getRenderer() {
        return renderer;
    }
    
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
    
    public Comparator getComparator() {
        return comparator;
    }
    
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }
    
    public void setValue(Object object){
        this.setText( renderer.render( object ) );
    }
    public Object getValue(){
        return this.getText();
    }
    
}
