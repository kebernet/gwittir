/*
 * RadioButton.java
 *
 * Created on July 15, 2007, 1:40 PM
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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesKeyboardEvents;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 DOCUMENT ME!
 *
 * @author ccollins
 */
public class RadioButton extends AbstractBoundWidget<Boolean> implements HasEnabled, SourcesClickEvents, HasFocus,
    SourcesKeyboardEvents {
    private com.google.gwt.user.client.ui.RadioButton base;

    /** Creates a new instance of RadioButton */
    public RadioButton() {
        super();
        init(null);
    }

    public RadioButton(String group) {
        super();
        init(group);
    }

    public RadioButton(String group, String label) {
        super();
        init(group);
        setText(label);
    }

    public RadioButton(String group, boolean value) {
        super();
        init(group);
        this.setChecked(value);
    }

    public RadioButton(String group, String label, boolean value) {
        super();
        init(group);
        setText(label);
        this.setChecked(value);
    }

    public int getAbsoluteLeft() {
        return this.base.getAbsoluteLeft();
    }

    public int getAbsoluteTop() {
        return this.base.getAbsoluteTop();
    }

    public void setAccessKey(char key) {
        this.base.setAccessKey(key);
    }

    public boolean isAttached() {
        return this.base.isAttached();
    }

    public void setChecked(boolean checked) {
        this.base.setChecked(checked);
    }

    public boolean isChecked() {
        return this.base.isChecked();
    }

    public void setEnabled(boolean enabled) {
        this.base.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return this.base.isEnabled();
    }

    public void setFocus(boolean focused) {
        this.base.setFocus(focused);
    }

    public void setHTML(String html) {
        this.base.setHTML(html);
    }

    public String getHTML() {
        return this.base.getHTML();
    }

    public void setHeight(String height) {
        this.base.setHeight(height);
    }

    public void setName(String name) {
        this.base.setName(name);
    }

    public String getName() {
        return this.base.getName();
    }

    public void setPixelSize(int width, int height) {
        this.base.setPixelSize(width, height);
    }

    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }

    public void setStyleName(String style) {
        this.base.setStyleName(style);
    }

    public String getStyleName() {
        return this.base.getStyleName();
    }

    public void setTabIndex(int index) {
        this.base.setTabIndex(index);
    }

    public int getTabIndex() {
        return this.base.getTabIndex();
    }

    public void setText(String text) {
        this.base.setText(text);
    }

    public String getText() {
        return this.base.getText();
    }

    public void setTitle(String title) {
        this.base.setTitle(title);
    }

    public String getTitle() {
        return this.base.getTitle();
    }

    public void setValue(Boolean value) {
        Boolean old = (Boolean) this.getValue();
        this.setChecked(value);

        if ((old != this.getValue()) && !old.equals(this.getValue())) {
            this.changes.firePropertyChange("value", old, this.getValue());
        }
    }

    public Boolean getValue() {
        return this.isChecked() ? Boolean.TRUE
                                : Boolean.FALSE;
    }

    public void setWidth(String width) {
        this.base.setWidth(width);
    }

    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }

    public void addFocusListener(FocusListener listener) {
        this.base.addFocusListener(listener);
    }

    public void addKeyboardListener(KeyboardListener listener) {
        this.base.addKeyboardListener(listener);
    }

    public void addStyleName(String style) {
        this.base.addStyleName(style);
    }

    public void onBrowserEvent(Event event) {
        this.base.onBrowserEvent(event);
    }

    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }

    public void removeFocusListener(FocusListener listener) {
        this.base.removeFocusListener(listener);
    }

    public void removeKeyboardListener(KeyboardListener listener) {
        this.base.removeKeyboardListener(listener);
    }

    public void removeStyleName(String style) {
        this.base.removeStyleName(style);
    }

    private void init(String group) {
        this.base = new com.google.gwt.user.client.ui.RadioButton(group);
        super.initWidget(this.base);
        this.base.addClickListener(
            new ClickListener() {
                public void onClick(Widget sender) {
                    Boolean old = isChecked() ? Boolean.FALSE
                                              : Boolean.TRUE;
                    changes.firePropertyChange("value", old, getValue());
                }
            });
    }
}
