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
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollListener;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.beans.Bindable;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.client.util.ListSorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


/**
 * This is an option-rich table for use with objects implementing the Bindable interfaces.
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 * @see com.totsp.gwittir.client.beans.Bindable
 */
public class BoundTable extends AbstractBoundWidget implements HasChunks {
    /**
     * A placholder for no mask options (0)
     */
    public static final int NONE_MASK = 0;
    /**
     * Renderer the table inside a scroll panel.
     * 
     * <p>If the table has a DataProvider, it will use the "Google Reader"
     * get-next-chunk-on-max-scroll operation.</p>
     */
    public static final int SCROLL_MASK = 1;
    /**
     * Renderers a heading row on the table using the labels on the Column objects.
     */
    public static final int HEADER_MASK = 2;
    /**
     * Lets the user have multiple rows in the "selected" state at a time.
     */
    public static final int MULTIROWSELECT_MASK = 4;
    /**
     * Turns off row selection and styling.
     */
    public static final int NO_SELECT_ROW_MASK = 8;
    /**
     * Turns off selected column stying.
     */
    public static final int NO_SELECT_COL_MASK = 16;
    /**
     * Turns off cell selection stying.
     */
    public static final int NO_SELECT_CELL_MASK = 32;
    /**
     * Tells the table to render a spacing row in between bound rows.
     */
    public static final int SPACER_ROW_MASK = 64;
    /**
     * If this table has a DataProvider AND it is not scrolling, this supresses the 
     * first, previous, next and last buttons at the bottom of the table.
     */
    public static final int NO_NAV_ROW_MASK = 128;
    /**
     * Enables sorting on the table when a header row is clicked.
     * 
     * If this table has a DataProvider, it must be a SortableDataProvider for this to work.
     */
    public static final int SORT_MASK = 256;
    private Binding topBinding;
    private Collection value;
    private DataProvider provider;
    private FlexTable table;
    private HashMap focusListeners = new HashMap() /*<HasFocus, FocusListener>*/;
    private HashMap selectedRowStyles /*<Integer, String>*/;
    private ScrollPanel scroll;
    private String selectedCellLastStyle;
    private String selectedColLastStyle = "";
    private String selectedRowLastStyle = "";
    private Widget base;
    private boolean[] ascending;
    private Column[] columns;
    private boolean inChunk = false;
    private int currentChunk = -1;
    private int lastScrollPosition;
    private int masks;
    private int numberOfChunks;
    private int selectedColLastIndex = -1;
    private int selectedRowLastIndex = -1;

    /** Creates a new instance of BoundTable */
    public BoundTable() {
        super();
        this.init(0);
    }

    /**
     * Creates a new instance of Bound table with the indicated options value.
     * @param masks int value containing the sum of the *_MASK options for the table.
     */
    public BoundTable(int masks) {
        super();
        this.init(masks);
    }

    /**
     * Creates a new instance of a table using a Collection as a data set.
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     * @param value A collection containing Bindable objects to render in the table.
     */
    public BoundTable(int masks, Column[] cols, Collection value) {
        super();
        this.columns = cols;
        this.value = value;
        this.init(masks);
    }

    /**
     * Creates a new instance of BoundTable using an DataProvider as a data source.
     * @param masks int value containing the sum of the *_MASK options for the table.
     * @param cols The Column objects for the table.
     * @param provider Instance of DataProvider to get chunked data from.
     */
    public BoundTable(int masks, Column[] cols, DataProvider provider) {
        super();
        this.columns = cols;
        this.provider = provider;
        this.init(masks);
    }

    /**
     * Adds a new Bindable object to the table.
     * @param o An object of type Bindable.
     */
    public void add(Bindable o) {
        if(this.value.add(o)) {
            this.addRow(o);
        }
    }

    /**
     * Adds a colleciton of Bindables to the table
     * @param c A collection containing Bindable objects.
     */
    public void add(Collection c) {
        for(Iterator it = c.iterator(); it.hasNext();)
            this.add((Bindable) it.next());
    }

    private void addRow(Bindable o) {
        int row = table.getRowCount();

        if((
                    (((masks & BoundTable.HEADER_MASK) > 0) && (row >= 2))
                    || (((masks & BoundTable.HEADER_MASK) == 0) && (row >= 1))
                ) && ((masks & BoundTable.SPACER_ROW_MASK) > 0)) {
            table.setWidget(row, 0, new Label(""));
            table.getFlexCellFormatter()
                 .setColSpan(row, 0, this.getColumns().length);
            table.getRowFormatter().setStyleName(row, "spacer");
            row++;
        }

        Binding bindingRow = new Binding();
        topBinding.getChildren().add(bindingRow);

        for(int col = 0; col < getColumns().length; col++) {
            final int colF = col;
            Widget widget = (Widget) createCellWidget(row, col, bindingRow,
                    getColumns()[col], o);
            table.setWidget(row, col, widget);

            if(widget instanceof HasFocus) {
                addSelectedFocusListener((HasFocus) widget, row, col);
            }
        }

        if(this.isAttached()) {
            bindingRow.setLeft();
            bindingRow.bind();
        }
    }

    private void addSelectedFocusListener(final HasFocus widget, final int row,
        final int col) {
        FocusListener l = new FocusListener() {
                public void onLostFocus(Widget sender) {
                }

                public void onFocus(Widget sender) {
                    handleSelect(row, col);
                }
            };

        widget.addFocusListener(l);
        focusListeners.put(widget, l);
    }

    public void addStyleName(String style) {
        this.base.addStyleName(style);
    }

    public void addTableListener(TableListener listener) {
        this.table.addTableListener(listener);
    }

    private int calculateObjectToRowOffset(int row) {
        GWT.log("Offsetting from object: " + row, null);

        if((masks & BoundTable.SPACER_ROW_MASK) > 0) {
            row = row + row;
        }

        if((masks & BoundTable.HEADER_MASK) > 0) {
            row = row + 1;
        }

        GWT.log("Offsetting to row: " + row, null);

        return row;
    }

    private Integer calculateRowToObjectOffset(Integer rowNumber) {
        int row = rowNumber.intValue();

        if((masks & BoundTable.HEADER_MASK) > 0) {
            row = row - 1;
        }

        if((masks & BoundTable.SPACER_ROW_MASK) > 0) {
            row = row - (row / 2);
        }

        return new Integer(row);
    }

    /**
     * Clears the table and cleans up all bindings and listeners.
     */
    public void clear() {
        this.topBinding.unbind();
        this.topBinding.getChildren().clear();

        for(Iterator it = this.focusListeners.entrySet().iterator();
                it.hasNext();) {
            Entry entry = (Entry) it.next();
            ((HasFocus) entry.getKey()).removeFocusListener((FocusListener) entry
                .getValue());
        }

        this.table.clear();

        while(table.getRowCount() > 0) {
            this.table.removeRow(0);
        }

        if(this.selectedRowStyles != null) {
            this.selectedRowStyles.clear();
        }

        this.clearSelectedCol();
        this.selectedCellLastStyle = "default";
        this.selectedColLastIndex = -1;
        this.selectedColLastStyle = "default";
        this.selectedRowLastIndex = -1;
        this.selectedRowLastStyle = "default";

        GWT.log("Number of rows after clear: " + table.getRowCount(), null);
    }

    private void clearSelectedCol() {
        if(this.selectedColLastIndex != -1) {
            this.getColumnFormatter()
                .setStyleName(this.selectedColLastIndex,
                this.selectedColLastStyle);
        }
    }

    private void clearSelectedRows() {
        List old = this.getSelected();

        if((this.masks & BoundTable.MULTIROWSELECT_MASK) > 0) {
            for(Iterator it = this.selectedRowStyles.entrySet().iterator();
                    it.hasNext();) {
                Entry entry = (Entry) it.next();
                int row = ((Integer) entry.getKey()).intValue();
                this.table.getRowFormatter()
                          .setStyleName(row, (String) entry.getValue());
            }

            this.selectedRowStyles.clear();
        } else if((this.masks & BoundTable.NO_SELECT_ROW_MASK) == 0) {
            if(this.selectedRowLastIndex != -1) {
                this.getRowFormatter()
                    .setStyleName(this.selectedRowLastIndex,
                    this.selectedRowLastStyle);
                this.selectedRowLastIndex = -1;
                this.selectedRowLastStyle = "default";
            }
        }

        this.changes.firePropertyChange("selected", old, this.getSelected());
    }

    private BoundWidget createCellWidget(final int row, final int column,
        Binding rowBinding, Column col, Bindable target) {
        BoundWidget widget;
        Binding binding = null;

        if(col.getCellProvider() != null) {
            widget = col.getCellProvider().get();
        } else {
            if(col.getValidator() != null) {
                widget = new TextBox();
            } else {
                widget = new Label();
            }

            binding = new Binding(widget, "value", col.getValidator(),
                    col.getFeedback(), target, col.getPropertyName(), null, null);
        }

        widget.setModel(target);

        if(binding != null) {
            rowBinding.getChildren().add(binding);
        }

        return widget;
    }

    private Widget createNavWidget() {
        Grid p = new Grid(1, 5);

        p.setStyleName("nav-grid");

        Button b = new Button("<<",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        first();
                    }
                });
        b.setStyleName("nav");

        if(this.getCurrentChunk() == 0) {
            b.setEnabled(false);
        }

        p.setWidget(0, 0, b);
        b = new Button("<",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        previous();
                    }
                });
        b.setStyleName("nav");

        if(this.getCurrentChunk() == 0) {
            b.setEnabled(false);
        }

        p.setWidget(0, 1, b);

        b = new Button(">",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        next();
                    }
                });
        b.setStyleName("nav");

        if(this.getCurrentChunk() == (this.getNumberOfChunks() - 1)) {
            b.setEnabled(false);
        }

        Label l = new Label((this.getCurrentChunk() + 1) + " / "
                + this.getNumberOfChunks());
        p.setWidget(0, 2, l);
        p.getCellFormatter()
         .setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);

        p.setWidget(0, 3, b);
        b = new Button(">>",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        last();
                    }
                });
        b.setStyleName("nav");

        if(this.getCurrentChunk() == (this.getNumberOfChunks() - 1)) {
            b.setEnabled(false);
        }

        p.setWidget(0, 4, b);

        return p;
    }

    /**
     * Causes the table to go to the first chunk of data, if a data provider is used.
     */
    public void first() {
        this.currentChunk = 0;
        this.provider.getChunk(this, this.getCurrentChunk());
        this.inChunk = true;
    }

    /**
     * Returns the Binding object used by this table.
     * @return The Binding object for this table.
     */
    public Binding getBinding() {
        return this.topBinding;
    }

    public int getCellCount(int row) {
        return this.table.getCellCount(row);
    }

    public HTMLTable.CellFormatter getCellFormatter() {
        return this.table.getCellFormatter();
    }

    public int getCellPadding() {
        return this.table.getCellPadding();
    }

    public int getCellSpacing() {
        return this.table.getCellSpacing();
    }

    public HTMLTable.ColumnFormatter getColumnFormatter() {
        return this.table.getColumnFormatter();
    }

    /**
     * Returns the Columns used in this table.
     * @return Column[] used for rendering this table.
     */
    public Column[] getColumns() {
        return columns;
    }

    /**
     * Returns the current fetched chunk from the data provider.
     * @return int index of the current chunk.
     */
    public int getCurrentChunk() {
        return currentChunk;
    }

    public FlexTable.FlexCellFormatter getFlexCellFormatter() {
        return this.table.getFlexCellFormatter();
    }

    public String getHTML(int row, int column) {
        return this.table.getHTML(row, column);
    }

    /**
     * Returns the number of available chunks (passed in from the DataProvider)
     * @return int number of chunks available from the DataProvider
     */
    public int getNumberOfChunks() {
        return numberOfChunks;
    }

    public int getRowCount() {
        return this.table.getRowCount();
    }

    public HTMLTable.RowFormatter getRowFormatter() {
        return this.table.getRowFormatter();
    }

    /**
     * Returns a List containing the current selected row objects.
     * @return List of Bindables from the selected rows.
     */
    public List getSelected() {
        ArrayList selected = new ArrayList();
        HashSet realIndexes = new HashSet();

        //GWT.log( "Number of selected rows: "+this.selectedRowStyles.size(), null );
        if(this.selectedRowStyles != null) {
            for(Iterator it = this.selectedRowStyles.keySet().iterator();
                    it.hasNext();) {
                realIndexes.add(calculateRowToObjectOffset((Integer) it.next()));
            }
        } else if(this.selectedRowLastIndex != -1) {
            realIndexes.add(calculateRowToObjectOffset(
                    new Integer(this.selectedColLastIndex)));
        }

        int i = 0;

        for(Iterator it = this.topBinding.getChildren().iterator();
                it.hasNext(); i++) {
            if(realIndexes.contains(new Integer(i))) {
                selected.add(((Binding) ((Binding) it.next()).getChildren()
                                         .get(0)).getRight().object);
            } else {
                it.next();
            }
        }

        return selected;
    }

    public String getStyleName() {
        return this.base.getStyleName();
    }

    public String getTitle() {
        return this.table.getTitle();
    }

    public Object getValue() {
        return value;
    }

    public BoundWidget getWidget(int row, int col) {
        return (BoundWidget) this.table.getWidget(row, col);
    }

    private void handleSelect(int row, int col) {
        if((
                    ((masks & BoundTable.SPACER_ROW_MASK) == 0)
                    && (
                        (((masks & BoundTable.HEADER_MASK) > 0) && (row > 0))
                        || ((masks & BoundTable.HEADER_MASK) == 0)
                    )
                )
                || (((masks & BoundTable.HEADER_MASK) > 0) & ((row % 2) != 0))
                || (
                    ((masks & BoundTable.HEADER_MASK) == 0) && (
                        (row % 2) != 1
                    )
                )) {
            setSelectedCell(row, col);
            setSelectedRow(row);
            setSelectedCol(col);
        }
    }

    private void init(int masksValue) {
        final BoundTable instance = this;
        this.topBinding = new Binding();
        this.masks = masksValue;

        if((this.masks & BoundTable.SORT_MASK) > 0) {
            this.ascending = new boolean[this.columns.length];
        }

        if((this.masks & BoundTable.MULTIROWSELECT_MASK) > 0) {
            this.selectedRowStyles = new HashMap();
        }

        this.table = new FlexTable();

        if((masks & BoundTable.SCROLL_MASK) > 0) {
            this.scroll = new ScrollPanel();
            this.scroll.setWidget(table);
            super.initWidget(scroll);

            if(this.provider != null) {
                scroll.addScrollListener(new ScrollListener() {
                        public void onScroll(Widget widget, int scrollLeft,
                            int scrollTop) {
                            GWT.log("" + inChunk, null);

                            if((inChunk == false)
                                    && (
                                        scrollTop >= (
                                            table.getOffsetHeight()
                                            - scroll.getOffsetHeight()
                                        )
                                    )) {
                                lastScrollPosition = scrollTop - 1;
                                next();
                            }
                        }
                    });
            }
        } else {
            super.initWidget(table);
        }

        this.table.setCellSpacing(0);
        table.addTableListener(new TableListener() {
                public void onCellClicked(SourcesTableEvents sender, int row,
                    int cell) {
                    handleSelect(row, cell);

                    if(((masks & BoundTable.SORT_MASK) > 0)
                            && ((masks & BoundTable.HEADER_MASK) > 0)
                            && (row == 0)) {
                        sortColumn(cell);
                    }
                }
            });
        this.base = (
                (this.scroll == null) ? (Widget) this.table : (Widget) this.scroll
            );
        this.value = (this.value == null) ? new ArrayList() : this.value;
        this.columns = (this.columns == null) ? new Column[0] : this.columns;
        this.setStyleName("gwittir-BoundTable");

        if((this.provider != null) && (this.getCurrentChunk() == -1)) {
            this.provider.init(this);
            this.inChunk = true;
        }
    }

    /**
     * Method called by the DataProvider to initialize the first chunk and pass 
     * in the to total number of chunks available.
     * @param c Data for Chunk index 0
     * @param numberOfChunks The total number of available chunks of data.
     */
    public void init(Collection c, int numberOfChunks) {
        GWT.log("Chunk init", null);
        this.numberOfChunks = numberOfChunks;
        this.currentChunk = 0;
        this.setValue(c);
        this.inChunk = false;
    }

    /**
     * Causes the table to render the last chunk of data.
     */
    public void last() {
        if((this.numberOfChunks - 1) >= 0) {
            this.currentChunk = this.numberOfChunks - 1;
            this.provider.getChunk(this, currentChunk);
            this.inChunk = true;
        }
    }

    /**
     * Causes the table to render the next chunk of data.
     */
    public void next() {
        if((this.currentChunk + 1) < this.numberOfChunks) {
            this.provider.getChunk(this, ++currentChunk);
            this.inChunk = true;
        }

        GWT.log("Next fired", null);
    }

    protected void onAttach() {
        super.onAttach();
        GWT.log("onAttach", null);
        this.renderAll();
    }

    protected void onDetach() {
        super.onDetach();
        this.clear();
    }

    /**
     * Causes teh table to render the previous chunk of data.
     */
    public void previous() {
        if((this.getCurrentChunk() - 1) >= 0) {
            this.provider.getChunk(this, --currentChunk);
            inChunk = true;
        }
    }

    private void renderAll() {
        this.clear();

        if((this.masks & BoundTable.HEADER_MASK) > 0) {
            for(int i = 0; i < this.columns.length; i++) {
                this.table.setWidget(0, i, new Label(this.columns[i].getLabel()));
                this.table.getCellFormatter().setStyleName(0, i, "header");
            }
        }

        GWT.log("Number of entries: " + this.value.size(), null);

        for(Iterator it = this.value.iterator(); it.hasNext();) {
            this.addRow((Bindable) it.next());
        }

        if((this.provider != null)
                && ((this.masks & BoundTable.SCROLL_MASK) == 0)
                && ((this.masks & BoundTable.NO_NAV_ROW_MASK) == 0)) {
            int row = this.table.getRowCount();
            this.table.setWidget(row, 0, this.createNavWidget());
            this.table.getFlexCellFormatter()
                      .setColSpan(row, 0, this.columns.length);
            table.getCellFormatter()
                 .setHorizontalAlignment(row, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
        }
    }

    public void setBorderWidth(int width) {
        this.table.setBorderWidth(width);
    }

    public void setCellPadding(int padding) {
        this.table.setCellPadding(padding);
    }

    public void setCellSpacing(int spacing) {
        this.table.setCellSpacing(spacing);
    }

    /**
     * Called by the DataProvider to pass in a requested chunk of data. 
     * THIS METHOD MUST BE CALLED ASYNCRONOUSLY.
     * @param c The next requested chunk of Bindable objects.
     */
    public void setChunk(Collection c) {
        GWT.log("SetChunk Firing : " + c.size() + " " + this.inChunk, null);

        if(!this.inChunk) {
            throw new RuntimeException(
                "This method MUST becalled asyncronously!");
        }

        if((masks & this.SCROLL_MASK) > 0) {
            this.add(c);
        } else {
            GWT.log("Setting value " + c.size(), null);
            this.setValue(c);
        }

        if(((masks & this.SCROLL_MASK) > 0)
                && (
                    this.scroll.getScrollPosition() >= this.lastScrollPosition
                )) {
            this.scroll.setScrollPosition(this.lastScrollPosition);
        }

        this.inChunk = false;
    }

    /**
     * Sets Column[] object for use on the table. 
     * Note, this will foce a re-init of the table.
     * @param columns Column[] to use to render the table.
     */
    public void setColumns(Column[] columns) {
        this.columns = columns;
        this.renderAll();
    }

    public void setHeight(String height) {
        this.base.setHeight(height);
    }

    public void setPixelSize(int width, int height) {
        this.table.setPixelSize(width, height);
    }

    /**
     * Sets the indicated items in the list to "selected" state.
     * @param selected A List of Bindables to set as the Selected value.   
     */
    public void setSelected(List selected) {
        int i = 0;
        this.clearSelectedRows();

        for(Iterator it = this.topBinding.getChildren().iterator();
                it.hasNext(); i++) {
            Bindable b = ((Binding) ((Binding) it.next()).getChildren().get(0))
                .getRight().object;

            if(selected.contains(b)) {
                this.setSelectedRow(calculateObjectToRowOffset(i));
            }
        }
    }

    private void setSelectedCell(int row, int col) {
        if((((this.masks & BoundTable.HEADER_MASK) > 0) && (row == 0))
                || ((this.masks & BoundTable.NO_SELECT_CELL_MASK) > 0)) {
            return;
        }

        if((this.selectedColLastIndex != -1)
                && (this.selectedRowLastIndex != -1)) {
            this.getCellFormatter()
                .setStyleName(this.selectedRowLastIndex,
                this.selectedColLastIndex, this.selectedCellLastStyle);
        }

        this.selectedCellLastStyle = table.getCellFormatter()
                                          .getStyleName(row, col);

        if((this.selectedCellLastStyle == null)
                || (this.selectedCellLastStyle.length() == 0)) {
            this.selectedCellLastStyle = "default";
        }

        table.getCellFormatter().setStyleName(row, col, "selected");
    }

    private void setSelectedCol(int col) {
        clearSelectedCol();
        this.selectedColLastIndex = col;

        if((this.masks & BoundTable.NO_SELECT_COL_MASK) == 0) {
            this.selectedColLastStyle = table.getRowFormatter().getStyleName(col);

            if((this.selectedColLastStyle == null)
                    || (this.selectedColLastStyle.length() == 0)) {
                this.selectedColLastStyle = "default";
            }

            table.getColumnFormatter().setStyleName(col, "selected");
        }
    }

    private void setSelectedRow(int row) {
        if(((this.masks & BoundTable.HEADER_MASK) > 0) && (row == 0)) {
            return;
        }

        List old = this.getSelected();

        if((this.masks & BoundTable.MULTIROWSELECT_MASK) > 0) {
            if(this.selectedRowStyles.containsKey(new Integer(row))) {
                this.getRowFormatter()
                    .setStyleName(row,
                    (String) this.selectedRowStyles.remove(new Integer(row)));
            } else {
                String lastStyle = table.getRowFormatter().getStyleName(row);
                lastStyle = ((lastStyle == null) || (lastStyle.length() == 0))
                    ? "default" : lastStyle;
                this.selectedRowStyles.put(new Integer(row), lastStyle);
                this.getRowFormatter().setStyleName(row, "selected");
            }
        } else if((this.masks & BoundTable.NO_SELECT_ROW_MASK) == 0) {
            if(this.selectedRowLastIndex != -1) {
                this.getRowFormatter()
                    .setStyleName(this.selectedRowLastIndex,
                    this.selectedRowLastStyle);
            }

            this.selectedRowLastIndex = row;
            this.selectedRowLastStyle = table.getRowFormatter().getStyleName(row);

            if((this.selectedRowLastStyle == null)
                    || (this.selectedRowLastStyle.length() == 0)) {
                this.selectedRowLastStyle = "default";
            }

            table.getRowFormatter().setStyleName(row, "selected");
        }

        this.selectedRowLastIndex = row;
        this.changes.firePropertyChange("selected", old, this.getSelected());
    }

    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }

    public void setStyleName(String style) {
        this.base.setStyleName(style);
    }

    public void setValue(Object value) {
        Collection old = this.value;
        this.value = (Collection) value;
        this.changes.firePropertyChange("value", old, this.value);

        if(this.isAttached()) {
            this.renderAll();
        }
    }

    public void setWidth(String width) {
        this.base.setWidth(width);
    }

    /**
     * Sorts the table based on the value of the property in the specified column 
     * index.
     * 
     * If using a SortableDataProvider, this will throw a runtime exception if the
     * column denoted by the index is not a supported sortable column.
     * @param index index of the column to sort the table on.
     */
    public void sortColumn(int index) {
        ascending[index] = !ascending[index];

        if(this.provider == null) {
            ArrayList sort = new ArrayList();
            sort.addAll(value);

            try {
                ListSorter.sortOnProperty(sort,
                    columns[index].getPropertyName(), ascending[index]);
            } catch(Exception e) {
                GWT.log("Exception during sort", e);
            }

            value.clear();

            for(Iterator it = sort.iterator(); it.hasNext();) {
                value.add(it.next());
            }

            setValue(value);
        } else if(this.provider instanceof SortableDataProvider) {
            SortableDataProvider sdp = (SortableDataProvider) this.provider;
            boolean canSort = false;
            String[] sortableProperties = sdp.getSortableProperties();

            for(int i = 0; (i < sortableProperties.length) && !canSort; i++) {
                if(sortableProperties[i].equals(
                            this.columns[index].getPropertyName())) {
                    canSort = true;
                }
            }

            if(!canSort) {
                throw new RuntimeException(
                    "That is not a sortable field from this data provider.");
            }

            sdp.sortOnProperty(this, this.columns[index].getPropertyName(),
                this.ascending[index]);
        }
    }
}
