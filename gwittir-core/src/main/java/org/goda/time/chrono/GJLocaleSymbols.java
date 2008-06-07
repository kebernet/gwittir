/*
 *  Copyright 2001-2005 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.goda.time.chrono;

import com.google.gwt.core.client.GWT;
import com.totsp.gwittir.client.ui.calendar.CalendarConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

import org.goda.time.DateTimeFieldType;
import org.goda.time.IllegalFieldValueException;

/**
 * Utility class used by a few of the GJDateTimeFields.
 *
 * @author Brian S O'Neill
 * @since 1.0
 */
class GJLocaleSymbols {
    private static final int FAST_CACHE_SIZE = 64;

    private static final GJLocaleSymbols[] cFastCache = new GJLocaleSymbols[FAST_CACHE_SIZE];

    private static HashMap cCache = new HashMap();

    public static GJLocaleSymbols forLocale(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        int index = System.identityHashCode(locale) & (FAST_CACHE_SIZE - 1);
        GJLocaleSymbols symbols = cFastCache[index];
        if (symbols != null && symbols.iLocale == locale) {
            return symbols;
        }
        synchronized (cCache) {
            symbols = (GJLocaleSymbols) cCache.get(locale);
            if (symbols == null) {
                symbols = new GJLocaleSymbols(locale);
                cCache.put(locale, symbols);
            }
        }
        cFastCache[index] = symbols;
        return symbols;
    }

    private static String[] realignMonths(String[] months) {
        String[] a = new String[13];
        for (int i=1; i<13; i++) {
            a[i] = months[i - 1];
        }
        return a;
    }

    private static String[] realignDaysOfWeek(String[] daysOfWeek) {
        String[] a = new String[8];
        for (int i=1; i<8; i++) {
            a[i] = daysOfWeek[(i < 7) ? i + 1 : 1];
        }
        return a;
    }

    private static void addSymbols(TreeMap map, String[] symbols, Integer[] integers) {
        for (int i=symbols.length; --i>=0; ) {
            String symbol = symbols[i];
            if (symbol != null) {
                map.put(symbol, integers[i]);
            }
        }
    }

    private static void addNumerals(TreeMap map, int start, int end, Integer[] integers) {
        for (int i=start; i<=end; i++) {
            map.put(String.valueOf(i).intern(), integers[i]);
        }
    }

    private static int maxLength(String[] a) {
        int max = 0;
        for (int i=a.length; --i>=0; ) {
            String s = a[i];
            if (s != null) {
                int len = s.length();
                if (len > max) {
                    max = len;
                }
            }
        }
        return max;
    }

    private final Locale iLocale;

    private final String[] iEras;
    private final String[] iDaysOfWeek;
    private final String[] iShortDaysOfWeek;
    private final String[] iMonths;
    private final String[] iShortMonths;
    private final String[] iHalfday;

    // These map Strings to Integers.
    private final TreeMap iParseEras;
    private final TreeMap iParseDaysOfWeek;
    private final TreeMap iParseMonths;

    private final int iMaxEraLength;
    private final int iMaxDayOfWeekLength;
    private final int iMaxShortDayOfWeekLength;
    private final int iMaxMonthLength;
    private final int iMaxShortMonthLength;
    private final int iMaxHalfdayLength;

    /**
     * @param locale must not be null
     */
    private GJLocaleSymbols(Locale locale) {
        iLocale = locale;
        CalendarConstants calcon = (CalendarConstants) GWT.create(CalendarConstants.class);
        
        //TODO i18n this
        iEras = new String[]{"BCE", "CE"};
        iDaysOfWeek = realignDaysOfWeek(
                new String[]{ calcon.sunday(), calcon.monday(), calcon.tuesday(), 
                calcon.wednesday(), calcon.thursday(), calcon.friday(),
                calcon.saturday()}
                );
        iShortDaysOfWeek = realignDaysOfWeek(
                new String[]{ calcon.sunday_short(), calcon.monday_short(), calcon.tuesday_short(),
                calcon.wednesday_short(), calcon.thursday_short(), calcon.friday_short(), 
                calcon.saturday_short() }
                );
        iMonths = realignMonths( new String[]{
            calcon.january(), calcon.february(), calcon.march(), calcon.april(),
            calcon.may(), calcon.june(), calcon.july(), calcon.august(), calcon.september(),
            calcon.october(), calcon.november(), calcon.december()
        });
        iShortMonths = realignMonths(new String[]{
            calcon.january_short(), calcon.february_short(), calcon.march_short(), calcon.april_short(),
            calcon.may_short(), calcon.june_short(), calcon.july_short(), calcon.august_short(), 
            calcon.september_short(),
            calcon.october_short(), calcon.november_short(), calcon.december_short()
        });
        iHalfday = new String[]{ "AM", "PM"};

        Integer[] integers = new Integer[13];
        for (int i=0; i<13; i++) {
            integers[i] = new Integer(i);
        }

        iParseEras = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        addSymbols(iParseEras, iEras, integers);
        if ("en".equals(locale.getLanguage())) {
            // Include support for parsing "BCE" and "CE" if the language is
            // English. At some point Joda-Time will need an independent set of
            // localized symbols and not depend on java.text.DateFormatSymbols.
            iParseEras.put("BCE", integers[0]);
            iParseEras.put("CE", integers[1]);
        }

        iParseDaysOfWeek = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        addSymbols(iParseDaysOfWeek, iDaysOfWeek, integers);
        addSymbols(iParseDaysOfWeek, iShortDaysOfWeek, integers);
        addNumerals(iParseDaysOfWeek, 1, 7, integers);

        iParseMonths = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        addSymbols(iParseMonths, iMonths, integers);
        addSymbols(iParseMonths, iShortMonths, integers);
        addNumerals(iParseMonths, 1, 12, integers);

        iMaxEraLength = maxLength(iEras);
        iMaxDayOfWeekLength = maxLength(iDaysOfWeek);
        iMaxShortDayOfWeekLength = maxLength(iShortDaysOfWeek);
        iMaxMonthLength = maxLength(iMonths);
        iMaxShortMonthLength = maxLength(iShortMonths);
        iMaxHalfdayLength = maxLength(iHalfday);
    }

    public String eraValueToText(int value) {
        return iEras[value];
    }

    public int eraTextToValue(String text) {
        Integer era = (Integer) iParseEras.get(text);
        if (era != null) {
            return era.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.era(), text);
    }

    public int getEraMaxTextLength() {
        return iMaxEraLength;
    }

    public String monthOfYearValueToText(int value) {
        return iMonths[value];
    }

    public String monthOfYearValueToShortText(int value) {
        return iShortMonths[value];
    }

    public int monthOfYearTextToValue(String text) {
        Integer month = (Integer) iParseMonths.get(text);
        if (month != null) {
            return month.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.monthOfYear(), text);
    }

    public int getMonthMaxTextLength() {
        return iMaxMonthLength;
    }

    public int getMonthMaxShortTextLength() {
        return iMaxShortMonthLength;
    }

    public String dayOfWeekValueToText(int value) {
        return iDaysOfWeek[value];
    }

    public String dayOfWeekValueToShortText(int value) {
        return iShortDaysOfWeek[value];
    }

    public int dayOfWeekTextToValue(String text) {
        Integer day = (Integer) iParseDaysOfWeek.get(text);
        if (day != null) {
            return day.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.dayOfWeek(), text);
    }

    public int getDayOfWeekMaxTextLength() {
        return iMaxDayOfWeekLength;
    }

    public int getDayOfWeekMaxShortTextLength() {
        return iMaxShortDayOfWeekLength;
    }

    public String halfdayValueToText(int value) {
        return iHalfday[value];
    }

    public int halfdayTextToValue(String text) {
        String[] halfday = iHalfday;
        for (int i = halfday.length; --i>=0; ) {
            if (halfday[i].equalsIgnoreCase(text)) {
                return i;
            }
        }
        throw new IllegalFieldValueException(DateTimeFieldType.halfdayOfDay(), text);
    }

    public int getHalfdayMaxTextLength() {
        return iMaxHalfdayLength;
    }
}
