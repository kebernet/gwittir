/*
 * CalendarMessages.java
 *
 * Created on November 9, 2007, 3:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.ui.calendar;

import com.google.gwt.i18n.client.Messages;

/**
 *
 * @author rcooper
 */
public interface CalendarMessages extends Messages {
    
    public String short_date( int day, int month, int year );
    
}
