/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.ui.calendar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

/**
 *
 * @author kebernet
 */
public class GwtTestTimeZoneList extends GWTTestCase {
    
   public String getModuleName() {
        return "com.totsp.gwittir.GwittirTest";
    }
    

    /**
     * Test of getTimeZones method, of class TimeZoneList.
     */
    public void testGetTimeZones() {
        System.out.println("getTimeZones");
        TimeZoneList instance = new TimeZoneList();
        String[][] expResult = null;
        String[][] result = instance.getTimeZones();
        TimeZones tz = (TimeZones) GWT.create(TimeZones.class);
        
        assertEquals(result.length, 555);
        for(String[] x : result ){
            for(String y: x){
                System.out.print( y+" ");
            }
            System.out.println();
        }
        
    }

}
