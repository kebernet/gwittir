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
import com.google.gwt.user.client.ui.RootPanel;
import com.totsp.gwittir.beans.BeanDescriptor;
import com.totsp.gwittir.beans.Binding;
import com.totsp.gwittir.beans.Introspector;
import com.totsp.gwittir.beans.Property;
import com.totsp.gwittir.ui.TextBox;

/**
 *
 * @author cooper
 */
public class ExampleEntryPoint implements EntryPoint{
    
    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }
    
    public void onModuleLoad() {
        Foo model = new Foo();
        model.setIntProperty( 3 );
        model.setStringProperty( "Foo bar baz");
        FooEdit edit = new FooEdit(model);
        edit.setModel( model );
        RootPanel.get().add( edit );
        
        Introspector is = (Introspector) GWT.create( Introspector.class );
        BeanDescriptor bd = is.getDescriptor( model );
        Object[] newValue = { new Integer(-255) };
        try{
            bd.getProperty("intProperty").getMutatorMethod().invoke( model, newValue );
            GWT.log( (String) bd.getProperty("stringProperty").getAccessMethod().invoke( model, null), null );
        } catch( Exception e){
            GWT.log( "AAAH!", e);
        }
        Property[] props = bd.getProperties();
        for( int i=0; i < props.length; i++ ){
            GWT.log( ""+ props[i].getName(), null  );
        }
        
        TextBox box = new TextBox();
        Binding b = new Binding( box, "value", model, "stringProperty" );
        b.bind();
        RootPanel.get().add( box );
        
        
    }
    
}
