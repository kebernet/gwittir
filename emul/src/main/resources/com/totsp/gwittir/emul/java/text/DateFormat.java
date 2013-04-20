/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package java.text;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public abstract class DateFormat
{
    /**
     * Constant for full style pattern.
     */
    public static final int FULL = 0;

    /**
     * Constant for long style pattern.
     */
    public static final int LONG = 1;

    /**
     * Constant for medium style pattern.
     */
    public static final int MEDIUM = 2;

    /**
     * Constant for short style pattern.
     */
    public static final int SHORT = 3;

    /**
     * Constant for default style pattern. Its value is MEDIUM.
     */
    public static final int DEFAULT = MEDIUM;

    public final static DateFormat getDateInstance()
    {
        return new SimpleDateFormat( DateTimeFormat.getMediumDateFormat() );
    }

    public final static DateFormat getDateInstance( int style )
    {
        switch ( style )
        {
            case FULL:
                return new SimpleDateFormat( DateTimeFormat.getFullDateFormat() );
            case LONG:
                return new SimpleDateFormat( DateTimeFormat.getLongDateFormat() );
            case SHORT:
                return new SimpleDateFormat( DateTimeFormat.getShortDateFormat() );
            default:
                return getDateInstance();
        }
    }

    public final static DateFormat getTimeInstance()
    {
        return new SimpleDateFormat( DateTimeFormat.getMediumTimeFormat() );
    }

    public final static DateFormat getTimeInstance( int style )
    {
        switch ( style )
        {
            case FULL:
                return new SimpleDateFormat( DateTimeFormat.getFullTimeFormat() );
            case LONG:
                return new SimpleDateFormat( DateTimeFormat.getLongTimeFormat() );
            case SHORT:
                return new SimpleDateFormat( DateTimeFormat.getShortTimeFormat() );
            default:
                return getTimeInstance();
        }
    }

    public final static DateFormat getDateTimeInstance()
    {
        return new SimpleDateFormat( DateTimeFormat.getMediumDateTimeFormat() );
    }

    public final static DateFormat getDateTimeInstance( int dateStyle, int timeStyle )
    {
        if ( dateStyle != timeStyle )
        {
            throw new IllegalArgumentException( "Unsupported combinaison of dateStyle & timeStyle : " + dateStyle + "-"
                + timeStyle );
        }
        switch ( dateStyle )
        {
            case FULL:
                return new SimpleDateFormat( DateTimeFormat.getFullDateTimeFormat() );
            case LONG:
                return new SimpleDateFormat( DateTimeFormat.getLongDateTimeFormat() );
            case SHORT:
                return new SimpleDateFormat( DateTimeFormat.getShortDateTimeFormat() );
            default:
                return getDateTimeInstance();
        }
    }

    public final static DateFormat getInstance()
    {
        return getDateTimeInstance( SHORT, SHORT );
    }

    public abstract String format( Date date );

    public abstract Date parse( String source );
}