/*
 * BoundTable.java
 *
 * Created on July 24, 2007, 5:30 PM
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.totsp.gwittir.client.ui.table;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.beans.Bindable;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.TextBox;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author cooper
 */
public class BoundTable extends AbstractBoundWidget {
    
    private Collection value;
    private DataProvider provider;
    private ScrollPanel scroll;
    private FlexTable table;
    private Column[] columns;
    private Widget base;
    int currentScrollPosition;
    private int nextChunk = 0;
    private ArrayList bindingRows = new ArrayList();
    
    /** Creates a new instance of BoundTable */
    public BoundTable() {
        super();
        table = new FlexTable();
        super.initWidget( table );
        this.base = table;
        this.setWidth( "100%" );
        this.setStyleName("gwittir-BoundTable");
    }
    
    public BoundTable(boolean scrolling){
        super();
        this.table = new FlexTable();
        if(scrolling){
            scroll = new ScrollPanel();
            scroll.setWidget( table );
            super.initWidget( scroll );
        } else {
            super.initWidget( table );
        }
        this.base = (this.scroll == null ? (Widget) this.table : (Widget) this.scroll );
        this.setStyleName("gwittir-BoundTable");
    }
    
    public BoundTable(boolean scrolling, Column[] cols, Collection value){
        super();
        table = new FlexTable();
        if(scrolling){
            scroll = new ScrollPanel();
            scroll.setWidget( table );
            super.initWidget( scroll );
        } else {
            super.initWidget( table );
        }
        this.columns = cols;
        this.value = value;
        this.base = (this.scroll == null ? (Widget) this.table : (Widget) this.scroll );
        this.setStyleName("gwittir-BoundTable");
        renderAll();
    }
    
    private void unbindAll(){
        for( Iterator it = this.bindingRows.iterator(); it.hasNext();  ){
            ((Binding) it.next()).unbind();
        }
    }
    
    private void renderAll(){
        
        this.unbindAll();
        this.table.clear();
        for(Iterator it = this.value.iterator(); it.hasNext(); ){
            this.addRow( (Bindable) it.next() );
        }
        
        
    }
    
    public void add( Bindable o ){
        if( this.value.add( o ) ){
            this.addRow( o );
        }
    }
    
    public void add( Collection c ){
        for(Iterator it = c.iterator(); it.hasNext(); )
            this.add( (Bindable) it.next() );
    }
    
    private void addRow(Bindable o){
        int row = table.getRowCount();
        Binding bindingRow = new Binding();
        bindingRows.add( bindingRow );
        for( int col=0; col < getColumns().length; col++ ){
            table.setWidget( row, col, (Widget) createCellWidget( bindingRow, getColumns()[col],  o) );
        }
    }
    
    private BoundWidget createCellWidget( Binding rowBinding, Column col,  Bindable target ){
        BoundWidget widget = null;
        Binding binding = null;
        if( col.getCellProvider() != null ){
            widget = col.getCellProvider().get();
        } else {
            if( col.getValidator() != null ){
                widget = new TextBox();
            } else {
                widget = new Label();
            }
        }
        binding = new Binding( widget, "value", col.getValidator(), col.getFeedback(), target, col.getPropertyName(), null, null );
        widget.setModel( target );
        if( binding != null ){
            rowBinding.getChildren().add( binding );
        }
        return widget;
        
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        Collection old = this.value;
        this.value = (Collection) value;
        this.changes.firePropertyChange("value", old, this.value );
        this.renderAll();
    }
    
    public Column[] getColumns() {
        return columns;
    }
    
    public void setColumns(Column[] columns) {
        this.columns = columns;
        this.renderAll();
    }
    
    protected void onDetach() {
        super.onDetach();
        for( Iterator it = this.bindingRows.iterator(); it.hasNext(); ){
            ((Binding) it.next()).unbind();
        }
    }
    
    protected void onAttach() {
        for( Iterator it = this.bindingRows.iterator(); it.hasNext(); ){
            ((Binding) it.next()).setLeft();
        }
        super.onAttach();
        for( Iterator it = this.bindingRows.iterator(); it.hasNext(); ){
            ((Binding) it.next()).bind();
        }
    }
    
    public void setWidth(String width) {
        this.base.setWidth(width);
    }
    
    public void setHeight(String height) {
        this.base.setHeight(height);
    }
    
    public void setStyleName(String style) {
        this.table.setStyleName(style);
    }
    
    public void addStyleName(String style) {
        this.table.addStyleName(style);
    }
    
    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }
    
    public String getStyleName() {
        return this.table.getStyleName();
    }
    
   
    
    
}
