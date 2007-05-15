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
import com.google.gwt.user.client.ui.RootPanel;

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
    }
    
}
