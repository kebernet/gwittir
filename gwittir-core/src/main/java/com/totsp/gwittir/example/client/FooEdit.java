/*
 * FooEdit.java
 *
 * Created on April 12, 2007, 4:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.example.client;

import com.totsp.gwittir.ui.PropertyContainer;

/**
 *
 * @author cooper
 */
public class FooEdit extends AbstractFooEdit {
    
    /** Creates a new instance of FooEdit */
    public FooEdit(Foo model) {
        super();
        this.setModel( model );
        PropertyContainer[] properties = {
            this.intProperty, null, null,
            this.stringProperty
        };
        super.setProperties( properties );
        super.columns = 2;
        super.init();
    }
    
}
