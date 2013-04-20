/*
 * Label.java
 *
 * Created on July 24, 2007, 5:35 PM
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
package com.totsp.gwittir.mvc.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.SourcesClickEvents;


/**
 * 
DOCUMENT ME!
 *
 * @author ccollins
 */
public class Hyperlink extends AbstractBoundWidget<String> implements HasHTML,
    SourcesClickEvents {
    private com.google.gwt.user.client.ui.Hyperlink base;

/** Creates a new instance of Hyperlink */
    public Hyperlink() {
        this.init(null, false, null);
    }

    public Hyperlink(String text, boolean asHTML, String target) {
        this.init(text, asHTML, target);
    }

    public Hyperlink(String text, String target) {
        this.init(text, false, target);
    }

    private void init(String text, boolean asHTML, String target) {
        if (text != null) {
            base = new com.google.gwt.user.client.ui.Hyperlink(text, asHTML,
                    target);
        } else {
            base = new com.google.gwt.user.client.ui.Hyperlink();
        }

        super.initWidget(base);
    }

    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }

    public String getHTML() {
        return (this.base.getHTML().length() == 0) ? null : this.base.getHTML();
    }

    public String getTargetHistoryToken() {
        return (this.base.getTargetHistoryToken().length() == 0) ? null
        : this.base.getTargetHistoryToken();
    }

    public String getText() {
        return this.base.getText();
    }

    public String getValue() {
        return (this.base.getText().length() == 0) ? null : this.base.getText();
    }

    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }

    public void setHTML(String html) {
        this.base.setHTML(html);
    }

    public void setValue(String value) {
        //("Setting value "+ value, null );
        Object old = this.getValue();
        this.setText(value);

        if ((this.getValue() != old) && (this.getValue() != null) &&
                this.getValue().equals(old)) {
            this.changes.firePropertyChange("value", old, this.getValue());
        }
    }

    public void setTargetHistoryToken(String targetHistoryToken) {
        this.base.setTargetHistoryToken(targetHistoryToken);
    }

    public void setText(String text) {
        this.base.setText(text);
    }
}
