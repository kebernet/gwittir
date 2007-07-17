/*
 * FooEdit.java
 *
 * Created on April 12, 2007, 4:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.ui.BasicPropertyContainer;
import com.totsp.gwittir.client.ui.PropertyContainer;
import com.totsp.gwittir.client.ui.TableBeanForm;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.client.validator.IntegerValidator;


/**
 *
 * @author cooper
 */
public abstract class AbstractFooEdit extends TableBeanForm {
    //Each of these blogs needs generatin, but they are boilerplate
    BasicPropertyContainer intProperty = new BasicPropertyContainer("intProperty",
            new TextBox(), "Int Property", "Some help.",
            new IntegerValidator(), // The type of validator should be customizable at gen time, or overrideable in the base constructor.
            new BasicPropertyContainer.PropertySetter() {
                public void setProperty(Object target, Object value) {
                    try {
                        ((Foo) target).setIntProperty(((Integer) (intProperty.getValidator()
                                                                             .validate(value))).intValue());
                    } catch(ValidationException e) {
                        Widget w = (Widget) intProperty.getWidget();
                        PopupPanel p = new PopupPanel(true);
                        p.setPopupPosition(w.getAbsoluteLeft(),
                            w.getAbsoluteTop() + w.getOffsetHeight());
                        p.setWidget(new HTML(e.getMessage()));
                        p.show();
                    }
                }
            },
            new BasicPropertyContainer.PropertyGetter() {
                public Object getProperty(Object target) {
                    GWT.log("GETTING " + ((Foo) target).getIntProperty(), null);

                    return Integer.valueOf(((Foo) target).getIntProperty());
                }
            });
    BasicPropertyContainer stringProperty = new BasicPropertyContainer("stringProperty",
            new TextBox(), "String property", "This is <b>help</b>.", null,
            new BasicPropertyContainer.PropertySetter() {
                public void setProperty(Object target, Object value) {
                    try {
                        ((Foo) target).setStringProperty((String) (stringProperty.getValidator()
                                                                                 .validate(value)));
                    } catch(ValidationException e) {
                        Widget w = (Widget) stringProperty.getWidget();
                        PopupPanel p = new PopupPanel(true);
                        p.setPopupPosition(w.getAbsoluteLeft(),
                            w.getAbsoluteTop() + w.getOffsetHeight());
                        p.setWidget(new HTML(e.getMessage()));
                        p.show();
                    }
                }
            },
            new BasicPropertyContainer.PropertyGetter() {
                public Object getProperty(Object target) {
                    return ((Foo) target).getStringProperty();
                }
            });

    /** Creates a new instance of FooEdit */
    public AbstractFooEdit() {
        super();

        //This array needs generatin.
        PropertyContainer[] properties = { stringProperty, intProperty };

        for(int i = 0; i < properties.length; i++) {
            BasicPropertyContainer container = (BasicPropertyContainer) properties[i];
            BindingAction action = new BasicPropertyContainer.BasicBindingAction(container.getProperyName(),
                    container.getSetter(), container.getGetter());
            container.getWidget().setAction(action);
        }

        this.setProperties(properties);
    }
}
