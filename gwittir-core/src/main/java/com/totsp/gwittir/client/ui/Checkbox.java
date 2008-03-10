/*
 * Checkbox.java
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

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesKeyboardEvents;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class Checkbox<B> extends AbstractBoundWidget<B, Boolean> implements HasEnabled,
    SourcesClickEvents, HasFocus, SourcesKeyboardEvents {
    
    private com.google.gwt.user.client.ui.CheckBox base;

    /** Creates a new instance of Checkbox */
    public Checkbox() {
        super();
        init(null);
    }

    public Checkbox(String label) {
        super();
        init(label);
    }

    public Checkbox(String label, boolean value) {
        super();
        init(label);
        this.setChecked(value);
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

    public int getAbsoluteLeft() {
        return this.base.getAbsoluteLeft();
    }

    public int getAbsoluteTop() {
        return this.base.getAbsoluteTop();
    }

    public String getHTML() {
        return this.base.getHTML();
    }

    public String getName() {
        return this.base.getName();
    }

    public String getStyleName() {
        return this.base.getStyleName();
    }

    public int getTabIndex() {
        return this.base.getTabIndex();
    }

    public String getText() {
        return this.base.getText();
    }

    public String getTitle() {
        return this.base.getTitle();
    }

    public Boolean getValue() {
        return this.isChecked() ? Boolean.TRUE : Boolean.FALSE;
    }

    private void init(String label) {
        this.base = new com.google.gwt.user.client.ui.CheckBox(label);
        this.setRenderer( (Renderer<Boolean, B>) ToBooleanRenderer.INSTANCE );
        super.initWidget(this.base);
        this.base.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    Boolean old = isChecked() ? Boolean.FALSE : Boolean.TRUE;
                    changes.firePropertyChange("value", old, getValue());
                }
            });
    }

   
    public boolean isChecked() {
        return this.base.isChecked();
    }

    public boolean isEnabled() {
        return this.base.isEnabled();
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

    public void setAccessKey(char key) {
        this.base.setAccessKey(key);
    }

    public void setChecked(boolean checked) {
        this.base.setChecked(checked);
    }

    public void setEnabled(boolean enabled) {
        this.base.setEnabled(enabled);
    }

    public void setFocus(boolean focused) {
        this.base.setFocus(focused);
    }

    public void setHTML(String html) {
        this.base.setHTML(html);
    }

    public void setHeight(String height) {
        this.base.setHeight(height);
    }

    public void setName(String name) {
        this.base.setName(name);
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

    public void setTabIndex(int index) {
        this.base.setTabIndex(index);
    }

    public void setText(String text) {
        this.base.setText(text);
    }

    @Override
    public void setTitle(String title) {
        this.base.setTitle(title);
    }

    public void setValue(B value) {
        Boolean old =  this.getValue();
        this.setChecked(value == null ? false
                                        : this.getRenderer()
                                              .render(value)
                                              .booleanValue());

        if((old != this.getValue()) && !old.equals(this.getValue())) {
            this.changes.firePropertyChange("value", old, this.getValue());
        }
    }

    public void setWidth(String width) {
        this.base.setWidth(width);
    }
}
