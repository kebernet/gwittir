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
package com.totsp.gwittir.mvc.ui.table;

import com.totsp.gwittir.mvc.beans.Converter;
import com.totsp.gwittir.mvc.ui.util.BoundWidgetProvider;
import com.totsp.gwittir.mvc.validator.ValidationFeedback;
import com.totsp.gwittir.mvc.validator.Validator;

import java.util.Comparator;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class Field {
    private BoundWidgetProvider cellProvider;
    private Comparator comparator;
    private Converter converter;
    private String helpText;
    private String label;
    private String propertyName;
    private String styleName;
    private ValidationFeedback feedback;
    private Validator validator;
    private boolean isClickable = true;

    /** Creates a new instance of Column */
    public Field(String propertyName) {
        this.propertyName = propertyName;
    }

    public Field(String propertyName, String label) {
        this.propertyName = propertyName;
        this.label = label;
    }
    
    public Field(String propertyName, String label, boolean isClickable){
        this.propertyName = propertyName;
        this.label = label;
        this.isClickable = isClickable;
    }

    public Field(String propertyName, String label, String styleName) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
    }

    public Field(String propertyName, String label, String styleName,
        String helpText) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.helpText = helpText;
    }

    public Field(String propertyName, String label, String styleName,
        Converter converter) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.converter = converter;
    }

    public Field(String propertyName, String label, String styleName,
        String helpText, Converter converter) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.converter = converter;
        this.helpText = helpText;
    }

    public Field(String propertyName, String label, String styleName,
        Converter converter, Validator validator, ValidationFeedback feedback) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.validator = validator;
        this.feedback = feedback;
        this.converter = converter;
    }

    public Field(String propertyName, String label, String styleName,
        String helpText, Converter converter, Validator validator,
        ValidationFeedback feedback) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.validator = validator;
        this.feedback = feedback;
        this.converter = converter;
        this.helpText = helpText;
    }

    public Field(String propertyName, String label, String styleName,
        String helpText, Converter converter, Validator validator,
        ValidationFeedback feedback, Comparator comparator) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.validator = validator;
        this.feedback = feedback;
        this.converter = converter;
        this.helpText = helpText;
    }

    public Field(String propertyName, String label, String styleName,
        BoundWidgetProvider cellProvider) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.cellProvider = cellProvider;
    }

    public Field(String propertyName, String label, String styleName,
        String helpText, BoundWidgetProvider cellProvider) {
        this.propertyName = propertyName;
        this.label = label;
        this.styleName = styleName;
        this.cellProvider = cellProvider;
    }
    
    public Field(String propertyName, String label,
			BoundWidgetProvider cellProvider, Validator validator,
			ValidationFeedback feedback) {
		this.propertyName = propertyName;
		this.label = label;
		this.cellProvider = cellProvider;
		this.validator = validator;
		this.feedback = feedback;
	}
    
    public Field(String propertyName, String label,
			BoundWidgetProvider cellProvider, Validator validator,
			ValidationFeedback feedback,Converter converter, boolean isClickable) {
		this.propertyName = propertyName;
		this.label = label;
		this.cellProvider = cellProvider;
		this.validator = validator;
		this.feedback = feedback;
		this.converter=converter;
                this.isClickable = isClickable;
	}
    
    public BoundWidgetProvider getCellProvider() {
        return cellProvider;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public ValidationFeedback getFeedback() {
        return feedback;
    }

    public String getHelpText() {
        return helpText;
    }

    public String getLabel() {
        return label;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Converter getConverter() {
        return converter;
    }

    public String getStyleName() {
        return styleName;
    }

    public Validator getValidator() {
        return validator;
    }
    
    public boolean isClickable() {
        return isClickable;
    }
}
