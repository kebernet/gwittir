/*
 * CalendarEventListener.java
 *
 * Created on October 31, 2007, 8:44 PM
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
package com.totsp.gwittir.client.ui.calendar;

import java.util.Date;


/**
 * A listener interface for click events on Calendars.
 * @author cooper
 */
public interface CalendarListener {
    /**
     * Called when a date is clicked.
     * @param calendar The calendar that fired the event
     * @param date The date that was clicked on.
     * @return A boolean indicating whether processing of events on this date should
     * continue (true) or be halted (false).
     */
    public boolean onDateClicked(Calendar calendar, Date date);
}
