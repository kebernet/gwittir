/*
 * ExampleEntryPoint.java
 *
 * Created on April 12, 2007, 4:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.totsp.gwittir.client.action.Action;

import com.totsp.gwittir.client.validator.PopupValidationFeedback;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.beans.BeanDescriptor;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Property;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.SoftButton;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.client.validator.CompositeValidator;
import com.totsp.gwittir.client.validator.IntegerRangeValidator;
import com.totsp.gwittir.client.validator.IntegerValidator;
import com.totsp.gwittir.client.validator.NotNullValidator;


/**
 *
 * @author cooper
 */
public class ExampleEntryPoint implements EntryPoint {
    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }

    public void onModuleLoad() {
        Foo model = new Foo();
        model.setIntProperty(3);
        model.setStringProperty("Foo bar baz");

        FooEdit edit = new FooEdit(model);
        edit.setModel(model);
        RootPanel.get().add(edit);

        Introspector is = (Introspector) GWT.create(Introspector.class);
        BeanDescriptor bd = is.getDescriptor(model);
        Object[] newValue = { new Integer(-255) };

        try {
            bd.getProperty("intProperty").getMutatorMethod()
              .invoke(model, newValue);
            GWT.log((String) bd.getProperty("stringProperty").getAccessMethod()
                               .invoke(model, null), null);
        } catch(Exception e) {
            GWT.log("AAAH!  ", e);
        }

        Property[] props = bd.getProperties();

        for(int i = 0; i < props.length; i++) {
            GWT.log("" + props[i].getName(), null);
        }

        TextBox box = new TextBox(true);
        TextBox intBox = new TextBox(true);

        CompositeValidator cv = new CompositeValidator()
                .add(new NotNullValidator())
                .add(new IntegerValidator())
                .add(new IntegerRangeValidator(1,5));
        ValidationFeedback pvf = new PopupValidationFeedback(PopupValidationFeedback.BOTTOM)
                .addMessage(NotNullValidator.class, "You must provide a value")
                .addMessage(IntegerValidator.class, "Enter an integer")
                .addMessage(IntegerRangeValidator.class, "Value must be 1..5");

        final Binding b = new Binding(box, "value", model, "stringProperty");
        Binding b2 = new Binding(intBox, "value", cv, pvf, model,
                "intProperty", null, null);
        
        b.getChildren().add( b2 );
        b.bind();
        b.setLeft();
        Button button = new SoftButton("Validate");
        button.setAction( new Action(){
            public void execute(BoundWidget model) {
                GWT.log("Action Fired", null);
                Window.alert( ""+b.isValid() );
            }
            
        });
        RootPanel.get().add(box);
        RootPanel.get().add(intBox);
        RootPanel.get().add( button );
    }
}
