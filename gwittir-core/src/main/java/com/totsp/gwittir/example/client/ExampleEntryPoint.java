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
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.beans.BeanDescriptor;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Property;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.SoftButton;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.client.ui.table.BoundTable;
import com.totsp.gwittir.client.ui.table.Column;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;
import com.totsp.gwittir.client.validator.CompositeValidationFeedback;
import com.totsp.gwittir.client.validator.CompositeValidator;
import com.totsp.gwittir.client.validator.IntegerRangeValidator;
import com.totsp.gwittir.client.validator.IntegerValidator;
import com.totsp.gwittir.client.validator.NotNullValidator;
import com.totsp.gwittir.client.validator.PopupValidationFeedback;
import com.totsp.gwittir.client.validator.StyleValidationFeedback;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author cooper
 */
public class ExampleEntryPoint implements EntryPoint {
    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }
    
    public void onModuleLoad() {
        GWT.log( int.class.toString(), null);
        Foo model = new Foo();
        model.setIntProperty(3);
        model.setStringProperty("Foo bar baz");
        
        Introspector is = Introspector.INSTANCE;
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
        
        TextBox box = new TextBox(true);
        TextBox intBox = new TextBox(true);
        
        CompositeValidator cv = new CompositeValidator()
        .add(new NotNullValidator())
        .add(new IntegerValidator())
        .add(new IntegerRangeValidator(1, 5));
        
        
        ValidationFeedback pvf =
                new PopupValidationFeedback(PopupValidationFeedback.BOTTOM)
                .addMessage(NotNullValidator.class,
                "You must provide a value")
                .addMessage(IntegerValidator.class,
                "Enter an integer")
                .addMessage(IntegerRangeValidator.class,
                "Value must be 1..5");
        
        
        
        
        
        final Binding b = new Binding(box, "value", model, "stringProperty");
        
        
        
        Binding b2 = new Binding(intBox, "value", cv, pvf, model,
                "intProperty", null, null);
        
        b.getChildren().add(b2);
        b.bind();
        b.setLeft();
        SoftButton button = new SoftButton("Validate");
        
        button.setAction(new Action() {
            public void execute(BoundWidget model) {
                GWT.log("Action Fired", null);
                Window.alert( ""+b.isValid() );
            }
        });
        //button.setSize("200px", "200px");
        button.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                GWT.log("CLICKED", null);
            }
        });
        button.setAction(new Action() {
            public void execute(BoundWidget model) {
                GWT.log("Action Fired", null);
                Window.alert("" + b.isValid());
            }
        });
        button.setSize("100px", "100px");
        button.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                GWT.log("CLICKED", null);
            }
        });
        RootPanel.get().add(button);
        RootPanel.get().add(box);
        RootPanel.get().add(intBox);
        
        Panel errors = new HorizontalPanel();
        RootPanel.get().add(errors);
        
        CompositeValidationFeedback cvf = new CompositeValidationFeedback()
        .add(pvf)
        .add(new StyleValidationFeedback("example-error"));
        
        ArrayList data =  new ArrayList();
         data.add(new Foo("Bacon", 1));
        data.add(new Foo("Egss", 2));
        data.add(new Foo("Hashbrowns", 3));
        data.add(new Foo("Toash", 4));
        data.add(new Foo("Sausage", 1));
        data.add(new Foo("Cereal", 2));
        data.add(new Foo("Melon", 3));
        data.add(new Foo("Biscuit", 4));
        data.add(new Foo("Waffle", 1));
        data.add(new Foo("Pancake", 2));
        data.add(new Foo("Ham", 3));
        data.add(new Foo("Bagel", 4));
        
        
        data.add(new Foo("Hamburger", 1));
        data.add(new Foo("Fries", 2));
        data.add(new Foo("Chicken Sandwich", 3));
        data.add(new Foo("Crab Cakes", 4));
        data.add(new Foo("Hot Dog", 1));
        data.add(new Foo("House Salad", 2));
        data.add(new Foo("Caesar Salad", 3));
        data.add(new Foo("Muffin", 4));
        data.add(new Foo("Fruit Plate", 1));
        data.add(new Foo("Macaroni and Cheese", 2));
        data.add(new Foo("Spinach", 3));
        data.add(new Foo("Okra", 4));
        
        Column[] c = new Column[3];
        c[0] = new Column("booleanProperty", "Boolean Property");
        c[1] = new Column("stringProperty", "String Property");
        c[2] = new Column("intProperty", "Integer Property", "int-cell", null,
                cv, cvf);
        
        
        BoundWidgetTypeFactory f = new BoundWidgetTypeFactory();
        /*f.add( String.class, new BoundWidgetProvider(){
            public BoundWidget get(){ return new Label(); }
        });
        */
        f.add( Foo.class, new BoundWidgetProvider() {
            public BoundWidget get() {
                FooEdit display = new FooEdit();
                display.setAction( new FooEditAction() );
                return display;
            }
            
        });
        
        
        final BoundTable t2 = new BoundTable(
                BoundTable.NONE_MASK
                + BoundTable.HEADER_MASK
                + BoundTable.SORT_MASK
                //+ BoundTable.SPACER_ROW_MASK
                //+ BoundTable.NO_SELECT_COL_MASK
                //+ BoundTable.NO_SELECT_CELL_MASK
                + BoundTable.SCROLL_MASK
                //+ BoundTable.MULTIROWSELECT_MASK
                + BoundTable.INSERT_WIDGET_MASK
                , f
                , c, data );
               // new TestSortableDataProvider() );
        t2.setHeight("200px");
        
        
        
        
        
        RootPanel.get().add(t2);
        Button selected = new Button("Selected", new ClickListener(){
            public void onClick(Widget sender) {
                for( Iterator it = t2.getSelected().iterator(); it.hasNext(); ){
                    RootPanel.get().add( new Label( ((Foo) it.next()).getStringProperty() ));
                }
            }
            
        });
        Button removeSelected = new Button("Remove Selected", new ClickListener(){
            public void onClick(Widget sender) {
                List selections = t2.getSelected();
                selections.remove( 1 );
                t2.setSelected( selections );
            }
            
        });
        RootPanel.get().add( selected );
        RootPanel.get().add( removeSelected );
    }
}
