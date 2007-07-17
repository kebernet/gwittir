/*
 * TableForm.java
 *
 * Created on April 12, 2007, 3:36 PM
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
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.action.BindingAction;


/**
 *
 * @author cooper
 */
public class TableBeanForm extends AbstractBeanForm {
    protected FlexTable table = new FlexTable();
    protected int columns;

    /** Creates a new instance of TableForm */
    public TableBeanForm() {
        super.initWidget(table);
    }

    public Object getValue() {
        return this.getModel();
    }

    protected void init() {
        super.init();

        int currentCol = 0;
        int currentRow = 0;
        PropertyContainer[] props = this.getProperties();

        for(int i = 0; i < props.length; i++) {
            if(props[i] != null) {
                props[i].getWidget().setModel(this.getModel());
                table.setWidget(currentRow, currentCol * 2, props[i].getLabel());
                table.setWidget(currentRow, (currentCol * 2) + 1,
                    (Widget) props[i].getWidget());

                if(props[i].getWidget().getAction() instanceof BindingAction) {
                    BindingAction action = (BindingAction) props[i].getWidget()
                                                                   .getAction();
                    action.set(props[i].getWidget());
                    action.set(props[i].getWidget());
                }
            }

            CellFormatter f = table.getCellFormatter();
            f.setStyleName(currentRow, currentCol * 2, "gwails-TableHeading");
            f.setStyleName(currentRow, (currentCol * 2) + 1, "gwails-TableValue");
            currentCol++;

            if(currentCol > (columns - 1)) {
                currentCol = 0;
                currentRow++;
            }
        }
    }

    public void setValue(Object object) {
        this.setModel(object);
    }
}
