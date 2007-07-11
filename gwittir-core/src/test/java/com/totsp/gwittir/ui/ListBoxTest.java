/*
 * ListBoxTest.java
 * JUnit based test
 *
 * Created on July 5, 2007, 6:59 PM
 */

package com.totsp.gwittir.ui;

import com.google.gwt.junit.client.GWTTestCase;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author cooper
 */
public class ListBoxTest extends GWTTestCase {
    
    public ListBoxTest() {
        super();
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public void testOptionsChanges(){
        
        ListBox b = new ListBox();
        ArrayList options = new ArrayList();
        options.add( new Integer( 1 ) );
        options.add( new Integer( 3 ) );
        options.add( new Integer( 5 ) );
        options.add( new Integer( 7 ) );
        options.add( new Integer( 9 ) );
        b.setMultipleSelect( true );
        b.setOptions( options );
        ArrayList selected = new ArrayList();
        selected.add( new Integer( 3 ) );
        selected.add( new Integer( 5 ) );
        selected.add( new Integer( 7 ) );
        b.setModel( selected );
        options = new ArrayList();
        options.add( new Integer( 1 ) );
        options.add( new Integer( 3 ) );
        options.add( new Integer( 7 ) );
        options.add( new Integer( 9 ) );
        b.setOptions( options );
        selected = (ArrayList) b.getModel();
        for( Iterator it = selected.iterator(); it.hasNext(); ){
            System.out.print( it.next() + " " );
        }
        System.out.println();
        this.assertEquals( new Integer( 3 ), selected.get(0) );
        this.assertEquals( new Integer( 5 ), selected.get(1) );
        
    }

    public String getModuleName() {
        return "com.totsp.Gwittir";
    }
    
}
