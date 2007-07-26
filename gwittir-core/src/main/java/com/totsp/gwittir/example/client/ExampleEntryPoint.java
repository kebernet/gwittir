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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.action.Action;

import com.totsp.gwittir.client.validator.PopupValidationFeedback;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.beans.BeanDescriptor;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Property;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.SoftButton;
import com.totsp.gwittir.client.ui.TextBox;
import com.totsp.gwittir.client.ui.table.BoundTable;
import com.totsp.gwittir.client.ui.table.Column;
import com.totsp.gwittir.client.ui.table.DataProvider;
import com.totsp.gwittir.client.validator.CompositeValidationFeedback;
import com.totsp.gwittir.client.validator.CompositeValidator;
import com.totsp.gwittir.client.validator.IntegerRangeValidator;
import com.totsp.gwittir.client.validator.IntegerValidator;
import com.totsp.gwittir.client.validator.NotNullValidator;
import com.totsp.gwittir.client.validator.PanelValidationFeedback;
import com.totsp.gwittir.client.validator.StyleValidationFeedback;
import java.util.ArrayList;

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
        
        
        
        Introspector is = (Introspector) GWT.create(Introspector.class);
        BeanDescriptor bd = is.getDescriptor(model);
        Object[] newValue = { new Integer(-255) };
        
        SoftButton button = new SoftButton("Validate");
        
        button.setAction( new Action(){
            public void execute(BoundWidget model) {
                GWT.log("Action Fired", null);
                //Window.alert( ""+b.isValid() );
            }
            
        });
        button.setSize( "200px", "200px");
        button.addClickListener( new ClickListener(){
            public void onClick(Widget sender) {
                GWT.log( "CLICKED", null);
            }
            
        });
        
        RootPanel.get().add( button );
        
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
        
        
        button.setAction( new Action(){
            public void execute(BoundWidget model) {
                GWT.log("Action Fired", null);
                Window.alert( ""+b.isValid() );
            }
            
        });
        button.setSize( "200px", "200px");
        button.addClickListener( new ClickListener(){
            public void onClick(Widget sender) {
                GWT.log( "CLICKED", null);
            }
            
        });
        
        RootPanel.get().add(box);
        RootPanel.get().add(intBox);
        
        final ArrayList foos = new ArrayList();
        foos.add( model );
        foos.add( new Foo( "String1", 1) );
        foos.add( new Foo( "String2", 2) );
        foos.add( new Foo( "String3", 3) );
        foos.add( new Foo( "String4", 4) );
        
        foos.add( new Foo( "String1", 1) );
        foos.add( new Foo( "String2", 2) );
        foos.add( new Foo( "String3", 3) );
        foos.add( new Foo( "String4", 4) );
        foos.add( new Foo( "String1", 1) );
        foos.add( new Foo( "String2", 2) );
        foos.add( new Foo( "String3", 3) );
        foos.add( new Foo( "String4", 4) );
        
        Panel errors = new SimplePanel();
        RootPanel.get().add( errors );
        CompositeValidationFeedback cvf = new CompositeValidationFeedback();
        cvf.add(new PanelValidationFeedback(errors) )
            .add( new StyleValidationFeedback( "example-error") );
        
        
        Column[] c = new Column[2];
        c[0] = new Column("stringProperty", "String Property" );
        c[1] = new Column("intProperty", "Integer Property", "int-cell", null, cv, cvf );
        
       
        
        
        BoundTable t = new BoundTable( BoundTable.SCROLL_MASK + BoundTable.HEADER_MASK,  c, foos );
        t.setHeight( "200px");
        
        BoundTable t2 = new BoundTable(  BoundTable.HEADER_MASK,  c, new DataProvider(){
            public void getChunk(final BoundTable table, final int chunkNumber) {
                ArrayList foos = new ArrayList();
                foos.add( new Foo( "String1", 1) );
                foos.add( new Foo( "String2", 2) );
                foos.add( new Foo( "String3", 3) );
                foos.add( new Foo( "String4", 4) );
                
                foos.add( new Foo( "String1", 1) );
                foos.add( new Foo( "String2", 2) );
                foos.add( new Foo( "String3", 3) );
                foos.add( new Foo( "String4", 4) );
                foos.add( new Foo( "String1", 1) );
                foos.add( new Foo( "String2", 2) );
                foos.add( new Foo( "String3", 3) );
                foos.add( new Foo( "String4", 4) );
                table.add( foos );
            }
        });
        t2.setHeight( "200px");
        
        
        //RootPanel.get().add( t );
        RootPanel.get().add( t2 );
        
    }
}
