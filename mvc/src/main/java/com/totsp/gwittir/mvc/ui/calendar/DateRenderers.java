/*
 * Renderers.java
 *
 * Created on November 9, 2007, 5:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.mvc.ui.calendar;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.binding.Converter;

import java.util.Date;


/**
 *
 * @author rcooper
 */
public interface DateRenderers {
    public static final CalendarMessages MESSAGES = (CalendarMessages) GWT.create(CalendarMessages.class);
    public static final CalendarConstants CONSTANTS = (CalendarConstants) GWT.create(CalendarConstants.class);
    public static final String[] DAYS_OF_WEEK = {
            CONSTANTS.sunday(), CONSTANTS.monday(), CONSTANTS.tuesday(),
            CONSTANTS.wednesday(), CONSTANTS.thursday(), CONSTANTS.friday(),
            CONSTANTS.saturday()
        };
    public static final String[] MONTHS_OF_YEAR = {
            CONSTANTS.january(), CONSTANTS.february(), CONSTANTS.march(),
            CONSTANTS.april(), CONSTANTS.may(), CONSTANTS.june(),
            CONSTANTS.july(), CONSTANTS.august(), CONSTANTS.september(),
            CONSTANTS.october(), CONSTANTS.november(), CONSTANTS.december()
        };
    public static final String[] DAYS_OF_WEEK_SHORT = {
            CONSTANTS.sunday_short(), CONSTANTS.monday_short(),
            CONSTANTS.tuesday_short(), CONSTANTS.wednesday_short(),
            CONSTANTS.thursday_short(), CONSTANTS.friday_short(),
            CONSTANTS.saturday_short()
        };
    public static final String[] MONTHS_OF_YEAR_SHORT = {
            CONSTANTS.january_short(), CONSTANTS.february_short(),
            CONSTANTS.march_short(), CONSTANTS.april_short(),
            CONSTANTS.may_short(), CONSTANTS.june_short(),
            CONSTANTS.july_short(), CONSTANTS.august_short(),
            CONSTANTS.september_short(), CONSTANTS.october_short(),
            CONSTANTS.november_short(), CONSTANTS.december_short()
        };
    public static final Converter<Date, String> SHORT_DATE_RENDERER = new Converter<Date, String>() {
            public String convert(Date o) {
                return MESSAGES.short_date(o.getDate(), o.getMonth() + 1,
                    o.getYear() + 1900);
            }
        };

    public static final Converter<Date, String> MEDIUM_DATE_RENDERER = new Converter<Date, String>() {
            public String convert(Date o) {
                return MESSAGES.medium_date(o.getDate(),
                    MONTHS_OF_YEAR_SHORT[o.getMonth()], o.getYear() + 1900);
            }
        };

    public static final Converter<Date, String> LONG_DATE_RENDERER = new Converter<Date, String>() {
            public String convert(Date o) {
                return MESSAGES.long_date(o.getDate(),
                    MONTHS_OF_YEAR[o.getMonth()], o.getYear() + 1900);
            }
        };
}
