/*
 * GridForm.java
 *
 * Created on August 7, 2007, 7:15 PM
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

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.beans.Bindable;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.HasDefaultBinding;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class GridForm extends AbstractTableWidget implements HasDefaultBinding {
    private static final String STYLE_NAME = "gwittir-GridForm";
    private static final BindingAction DEFAULT_ACTION = new BindingAction() {
            public void bind(BoundWidget widget) {
                try {
                    ((HasDefaultBinding) widget).bind();
                } catch(ClassCastException cce) {
                    throw new RuntimeException(cce);
                }
            }

            public void execute(BoundWidget model) {
            }

            public void unbind(BoundWidget widget) {
                try {
                    ((GridForm) widget).unbind();
                } catch(ClassCastException cce) {
                    throw new RuntimeException(cce);
                }
            }

            public void set(BoundWidget widget) {
                try {
                    ((GridForm) widget).set();
                } catch(ClassCastException cce) {
                    throw new RuntimeException(cce);
                }
            }
        };

    private Binding binding = new Binding();
    private FlexTable base = new FlexTable();
    private Field[] fields;
    private int columns = 1;

    /** Creates a new instance of GridForm */
    public GridForm(Field[] fields) {
        this.fields = fields;
        this.factory = new BoundWidgetTypeFactory(true);
        super.initWidget(this.base);
        this.setStyleName(GridForm.STYLE_NAME);
        this.setAction(GridForm.DEFAULT_ACTION);
    }

    public GridForm(Field[] fields, int columns) {
        this.fields = fields;
        this.columns = columns;
        this.factory = new BoundWidgetTypeFactory(true);
        super.initWidget(this.base);
        this.setStyleName(GridForm.STYLE_NAME);
        this.setAction(GridForm.DEFAULT_ACTION);
    }

    public GridForm(
        Field[] fields, int columns, BoundWidgetTypeFactory factory) {
        this.fields = fields;
        this.columns = columns;
        this.factory = factory;
        super.initWidget(this.base);
        this.setStyleName(GridForm.STYLE_NAME);
        this.setAction(GridForm.DEFAULT_ACTION);
    }

    public void bind() {
        this.binding.bind();
    }

    public Object getValue() {
        return this.getModel();
    }

    private void render() {
        if(this.binding.getChildren().size() > 0) {
            this.binding.unbind();
            this.binding.getChildren().clear();
        }

        int row = 0;

        for(int i = 0; i < this.fields.length;) {
            for(int col = 0; (col < this.columns) && (i < fields.length);
                    col++) {
                final Field field = this.fields[i];

                if(field == null) {
                    i++;

                    continue;
                }

                Widget widget = (Widget) this.createWidget(
                        this.binding, field, (Bindable) this.getValue());
                Label label = new Label(field.getLabel());
                this.base.setWidget(row, col * 2, label);
                this.base.getCellFormatter().setStyleName(
                    row, col * 2, "label");
                this.base.setWidget(row, (col * 2) + 1, widget);
                this.base.getCellFormatter()
                         .setStyleName(row, (col * 2) + 1, "field");

                if(field.getHelpText() != null) {
                    label.addClickListener(
                        new ClickListener() {
                            public void onClick(Widget widget) {
                                final PopupPanel p = new PopupPanel(true);
                                p.setStyleName("gwittir-GridForm-Help");
                                p.setWidget(new HTML(field.getHelpText()));
                                p.setPopupPosition(
                                    widget.getAbsoluteLeft(),
                                    widget.getAbsoluteTop()
                                    + widget.getOffsetHeight());
                                p.show();
                            }
                        });
                }

                i++;
            }

            row++;
        }
    }

    public void set() {
        this.binding.setLeft();
    }

    public void setModel(Object model) {
        super.setModel(model);
        this.render();
    }

    public void setValue(Object value) {
        Object old = this.getModel();
        this.setModel(value);
        this.changes.firePropertyChange("value", old, value);
    }

    public void unbind() {
        this.binding.unbind();
    }
}
