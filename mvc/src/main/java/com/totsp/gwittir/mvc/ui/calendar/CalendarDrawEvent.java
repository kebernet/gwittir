/*
 * CalendarDrawEvent.java
 *
 * Created on October 31, 2007, 8:51 PM
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.totsp.gwittir.mvc.ui.calendar;

import java.util.Date;

/**
 * An event indicating a date has been drawn on a calendar.
 * @author cooper
 */
public class CalendarDrawEvent {
    
    private SourcesCalendarDrawEvents source;
    private Date date;
    
    
    /**
     * Creates a new instance of CalendarDrawEvent
     * @param source Source calendar
     * @param date Date rendered.
     */
    public CalendarDrawEvent(SourcesCalendarDrawEvents source, Date date) {
        this.source = source;
        this.date = date;
    }

    /**
     * Returns the source Calendar for the event.
     * @return The object that fired the event.
     */
    public SourcesCalendarDrawEvents getSource() {
        return source;
    }

    /**
     * The date rendered.
     * @return  The date rendered.
     */
    public Date getDate() {
        return date;
    }
    
}
