/*
 * Column.java
 *
 * Created on July 24, 2007, 5:32 PM
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

import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;
import com.totsp.gwittir.client.ui.Renderer;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.validator.Validator;


/**
 *
 * @author cooper
 */
public class Column {
    private BoundWidgetProvider cellProvider;
    private Renderer renderer;
    private String label;
    private String propertyName;
    private String styleName;
    private ValidationFeedback feedback;
    private Validator validator;

    /** Creates a new instance of Column */
    public Column(String propertyName) {
        this.propertyName = propertyName;
    }

    public Column(String propertyName, String label) {
        this.propertyName = propertyName;
        this.label = label;
    }

    public Column(String propertyName, String label, String styleName) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
    }

    public Column(String propertyName, String label, String styleName,
        Renderer renderer) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.renderer = renderer;
    }

    public Column(String propertyName, String label, String styleName,
        Renderer renderer, Validator validator, ValidationFeedback feedback) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.validator = validator;
        this.feedback = feedback;
        this.renderer = renderer;
    }

    public Column(String label, String styleName,
        BoundWidgetProvider cellProvider) {
        this.label = label;
        this.styleName = styleName;
        this.cellProvider = cellProvider;
    }

    public BoundWidgetProvider getCellProvider() {
        return cellProvider;
    }

    public ValidationFeedback getFeedback() {
        return feedback;
    }

    public String getLabel() {
        return label;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public String getStyleName() {
        return styleName;
    }

    public Validator getValidator() {
        return validator;
    }
}
