/*
 * CalendarMessages.java
 *
 * Created on November 9, 2007, 3:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.ui.calendar;

import com.google.gwt.i18n.client.Messages;

/**
 * Messages for formatting dates/times. Please provide properties files for any
 * language you application supports.
 * @author rcooper
 */
public interface CalendarMessages extends Messages {
    
    public String short_date( int day, int month, int year );
    public String medium_date( int day, String month, int year );
    public String long_date( int day, String month, int year );
    
}
