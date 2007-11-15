/*
 * BoundWidgetTypeFactory.java
 *
 * Created on July 27, 2007, 8:11 PM
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
package com.totsp.gwittir.client.ui.util;

import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.Checkbox;
import com.totsp.gwittir.client.ui.Hyperlink;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.RadioButton;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.client.ui.calendar.PopupDatePicker;
import java.util.Date;

import java.util.HashMap;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class BoundWidgetTypeFactory {
    public static final BoundWidgetProvider CHECKBOX_PROVIDER = new BoundWidgetProvider() {
            public BoundWidget get() {
                return new Checkbox();
            }
        };

    public static final BoundWidgetProvider TEXTBOX_PROVIDER = new BoundWidgetProvider() {
            public BoundWidget get() {
                return new TextBox();
            }
        };

    public static final BoundWidgetProvider LABEL_PROVIDER = new BoundWidgetProvider() {
            public BoundWidget get() {
                return new Label();
            }
        };

    public static final BoundWidgetProvider BUTTON_PROVIDER = new BoundWidgetProvider() {
            public BoundWidget get() {
                return new Button();
            }
        };

    public static final BoundWidgetProvider RADIOBUTTON_PROVIDER = new BoundWidgetProvider() {
            public BoundWidget get() {
                return new RadioButton();
            }
        };

    public static final BoundWidgetProvider HYPERLINK_PROVIDER = new BoundWidgetProvider() {
            public BoundWidget get() {
                return new Hyperlink();
            }
        };
        
   public static final BoundWidgetProvider POPUP_DATE_PICKER_PROVIDER = new BoundWidgetProvider(){
        public BoundWidget get() {
            return new PopupDatePicker();
        }
        
   };

    HashMap registry = new HashMap();

    public BoundWidgetTypeFactory(boolean defaults) {
        super();

        if (defaults) {
            registry.put(Boolean.class, BoundWidgetTypeFactory.CHECKBOX_PROVIDER);
            registry.put(boolean.class, BoundWidgetTypeFactory.CHECKBOX_PROVIDER);
            registry.put(String.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(Integer.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(int.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(Long.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(long.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(Float.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(float.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(Double.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(double.class, BoundWidgetTypeFactory.TEXTBOX_PROVIDER);
            registry.put(Date.class, BoundWidgetTypeFactory.POPUP_DATE_PICKER_PROVIDER);
        }
    }

/** Creates a new instance of BoundWidgetTypeFactory */
    public BoundWidgetTypeFactory() {
        super();
    }

    public void add(Class type, BoundWidgetProvider provider) {
        registry.put(type, provider);
    }

    public void add(String propertyName, BoundWidgetProvider provider) {
        registry.put(propertyName, provider);
    }

    public BoundWidgetProvider getWidgetProvider(Class type) {
        return (BoundWidgetProvider) registry.get(type);
    }

    public BoundWidgetProvider getWidgetProvider(String propertyName, Class type) {
        return registry.containsKey(propertyName)
        ? (BoundWidgetProvider) registry.get(propertyName)
        : (BoundWidgetProvider) registry.get(type);
    }
}
