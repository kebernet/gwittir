/*
 * SourcesCalendarDrawEvents.java
 *
 * Created on October 31, 2007, 8:49 PM
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

/**
 * An interface for classes that file CalendarDrawEvents
 * @author cooper
 */
public interface SourcesCalendarDrawEvents {
    
    /**
     * Adds a CalendarDrawListener
     * @param cdl CalendarDrawListener to add
     */
    void addCalendarDrawListener( CalendarDrawListener cdl );
    /**
     * Removes a CalendarDrawListener
     * @param cdl CalendarDrawListener to remove
     */
    void removeCalendarDrawListener( CalendarDrawListener cdl );
    /**
     * The current set of CalendarDrawListeners.
     * @return Array of CalendarDrawListeners currently registered
     */
    CalendarDrawListener[] getCalendarDrawListeners();
    
}
