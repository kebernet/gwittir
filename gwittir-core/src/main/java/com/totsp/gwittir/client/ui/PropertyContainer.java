/*
 * PropertyContainer.java
 *
 * Created on April 12, 2007, 12:43 PM
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

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.validator.Validator;


/**
 *
 * @author cooper
 */
public class PropertyContainer {
    private BoundWidget widget;
    private Label label;
    private PopupPanel help;
    private Validator validator;

    /** Creates a new instance of PropertyContainer */
    public PropertyContainer(final BoundWidget widget, final Label label,
        final PopupPanel help, final Validator validator) {
        init(widget, label, help, validator);
    }

    protected PropertyContainer() {
    }

    public PopupPanel getHelp() {
        return help;
    }

    public Label getLabel() {
        return label;
    }

    public Validator getValidator() {
        return validator;
    }

    public BoundWidget getWidget() {
        return widget;
    }

    protected void init(final BoundWidget widget, final Label label,
        final PopupPanel help, final Validator validator) {
        this.setWidget(widget);
        this.setLabel(label);
        this.setHelp(help);
        this.setValidator(validator);
        this.getLabel().addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    help.setPopupPosition(sender.getAbsoluteLeft(),
                        sender.getAbsoluteTop() + sender.getOffsetHeight());
                }
            });
    }

    public void setHelp(PopupPanel help) {
        this.help = help;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setWidget(BoundWidget widget) {
        this.widget = widget;
    }
}
