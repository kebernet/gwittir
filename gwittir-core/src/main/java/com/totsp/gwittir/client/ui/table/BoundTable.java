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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.ScrollListener;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.beans.Bindable;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.TextBox;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
    private String selectedRowLastStyle = "";
    private int selectedRowLastIndex = -1;
    private String selectedColLastStyle = "";
    private int selectedColLastIndex = -1;
    private String selectedCellLastStyle;
    private HashMap focusListeners = new HashMap() /*<HasFocus, FocusListener>*/;
    
    /** Creates a new instance of BoundTable */
    public BoundTable() {
        super();
        table = new FlexTable();
        table.addTableListener( new TableListener(){
            public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
                setSelectedCell(row, cell);
                setSelectedRow( row );
                setSelectedCol( cell );
            }
            
        });
        super.initWidget( table );
        this.base = table;
        this.table.setCellSpacing(0);
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
        this.table.setCellSpacing(0);
        table.addTableListener( new TableListener(){
            public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
                setSelectedCell(row, cell);
                setSelectedRow( row );
                setSelectedCol( cell );
            }
            
        });
        this.base = (this.scroll == null ? (Widget) this.table : (Widget) this.scroll );
        this.setStyleName("gwittir-BoundTable");
    }
    
    public BoundTable(boolean scrolling, Column[] cols, Collection value){
        super();
        table = new FlexTable();
        if(scrolling){
            scroll = new ScrollPanel();
            scroll.setWidget( table );
            scroll.addScrollListener( new ScrollListener(){
                public void onScroll(Widget widget, int scrollLeft, int scrollTop) {
                    GWT.log( "Scroll: "+ scrollTop, null );
                    GWT.log( ""+table.getOffsetHeight(), null );
                    GWT.log( table.getOffsetHeight() - scroll.getOffsetHeight() + "", null );
                }
                
            });
            super.initWidget( scroll );
        } else {
            super.initWidget( table );
        }
        this.columns = cols;
        this.value = value;
        this.table.setCellSpacing(0);
        table.addTableListener( new TableListener(){
            public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
                setSelectedCell(row, cell);
                setSelectedRow( row );
                setSelectedCol( cell );
            }
            
        });
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
        final int row = table.getRowCount();
        Binding bindingRow = new Binding();
        bindingRows.add( bindingRow );
        for( int col=0; col < getColumns().length; col++ ){
            final int colF = col;
            Widget widget = (Widget) createCellWidget( row, col, bindingRow, getColumns()[col],  o);
            table.setWidget( row, col,widget );
            if( widget instanceof HasFocus ){
                addSelectedFocusListener( (HasFocus) widget, row, col );
            }
        }
    }
    private void addSelectedFocusListener(final HasFocus widget, final int row, final int col ){
        FocusListener l = new FocusListener() {
            public void onLostFocus(Widget sender) {
            }
            
            public void onFocus(Widget sender) {
                setSelectedCell( row, col);
                setSelectedRow( row );
                setSelectedCol( col );
            }
            
        };
        widget.addFocusListener( l );
        focusListeners.put( widget, l );
    }
    
    private void setSelectedRow( int row ){
        if( this.selectedRowLastIndex != -1 ){
            this.getRowFormatter().setStyleName( this.selectedRowLastIndex, this.selectedRowLastStyle );
        }
        this.selectedRowLastIndex = row;
        this.selectedRowLastStyle = table.getRowFormatter().getStyleName( row );
        if( this.selectedRowLastStyle == null || this.selectedRowLastStyle.length() == 0 ){
            this.selectedRowLastStyle = "default";
        }
        table.getRowFormatter().setStyleName( row, "selected");
    }
    
    private void setSelectedCol( int col ){
        if( this.selectedColLastIndex != -1 ){
            this.getColumnFormatter().setStyleName( this.selectedColLastIndex, this.selectedRowLastStyle );
        }
        this.selectedColLastIndex = col;
        this.selectedColLastStyle = table.getRowFormatter().getStyleName( col );
        if( this.selectedColLastStyle == null || this.selectedColLastStyle.length() == 0 ){
            this.selectedColLastStyle = "default";
        }
        table.getColumnFormatter().setStyleName( col, "selected");
    }
    
    private void setSelectedCell( int row, int col ){
        if( this.selectedColLastIndex != -1 && this.selectedRowLastIndex != -1 ){
            this.getCellFormatter().setStyleName( this.selectedRowLastIndex,
                    this.selectedColLastIndex,
                    this.selectedCellLastStyle );
        }
        this.selectedCellLastStyle = table.getCellFormatter().getStyleName( row,  col );
        if( this.selectedCellLastStyle == null || this.selectedCellLastStyle.length() == 0 ){
            this.selectedCellLastStyle = "default";
        }
        table.getCellFormatter().setStyleName( row, col, "selected");
    }
    
    private BoundWidget createCellWidget( final int row, final int column,
            Binding rowBinding, Column col,  Bindable target ){
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
        this.base.setStyleName(style);
    }
    
    public void addStyleName(String style) {
        this.base.addStyleName(style);
    }
    
    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }
    
    public String getStyleName() {
        return this.base.getStyleName();
    }
    
    public void addTableListener(TableListener listener) {
        this.table.addTableListener(listener);
    }
    
    public void setBorderWidth(int width) {
        this.table.setBorderWidth(width);
    }
    
    public int getCellCount(int row) {
        return this.table.getCellCount(row);
    }
    
    public void setCellPadding(int padding) {
        this.table.setCellPadding(padding);
    }
    
    public void setCellSpacing(int spacing) {
        this.table.setCellSpacing(spacing);
    }
    
    public void setPixelSize(int width, int height) {
        this.table.setPixelSize(width, height);
    }
    
    public int getCellSpacing() {
        return this.table.getCellSpacing();
    }
    
    public int getCellPadding() {
        return this.table.getCellPadding();
    }
    
    public HTMLTable.CellFormatter getCellFormatter() {
        return this.table.getCellFormatter();
    }
    
    public HTMLTable.ColumnFormatter getColumnFormatter() {
        return this.table.getColumnFormatter();
    }
    
    public FlexTable.FlexCellFormatter getFlexCellFormatter() {
        return this.table.getFlexCellFormatter();
    }
    
    public String getHTML(int row, int column) {
        return this.table.getHTML(row, column);
    }
    
    public int getRowCount() {
        return this.table.getRowCount();
    }
    
    public HTMLTable.RowFormatter getRowFormatter() {
        return this.table.getRowFormatter();
    }
    
    public String getTitle() {
        return this.table.getTitle();
    }
    
    public BoundWidget getWidget( int row, int col ){
        return (BoundWidget) this.table.getWidget(row, col);
    }
}
