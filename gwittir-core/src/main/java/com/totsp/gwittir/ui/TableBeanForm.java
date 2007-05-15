/*
 * TableForm.java
 *
 * Created on April 12, 2007, 3:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.BindingAction;

/**
 *
 * @author cooper
 */
public class TableBeanForm extends AbstractBeanForm {
    
    protected int columns;
    protected FlexTable table = new FlexTable();
    
    /** Creates a new instance of TableForm */
    public TableBeanForm() {
        super.initWidget( table );
    }
    
    protected void init() {
        super.init();
        int currentCol = 0;
        int currentRow = 0;
        PropertyContainer[] props = this.getProperties();
        for(int i=0; i < props.length; i++ ){
            if( props[i] != null ){
                props[i].getWidget().setModel( this.getModel() );
                table.setWidget( currentRow, currentCol * 2, props[i].getLabel() );
                table.setWidget( currentRow, (currentCol * 2) +1, (Widget) props[i].getWidget() );
                if( props[i].getWidget().getAction() instanceof BindingAction ){
                    BindingAction action = (BindingAction) props[i].getWidget().getAction();
                    action.set( props[i].getWidget() );
                    action.set( props[i].getWidget() );
                }
            }
            CellFormatter f = table.getCellFormatter();
            f.setStyleName( currentRow, currentCol *2, "gwails-TableHeading" );
            f.setStyleName( currentRow, (currentCol * 2) +1, "gwails-TableValue" );
            currentCol++;
            if( currentCol > columns -1 ){
                currentCol = 0;
                currentRow++;
            }
        }
    }
    
    public Object getValue(){
        return this.getModel();
    }
    
    public void setValue(Object object){
        this.setModel( object );
    }
    
    
    
    
}
