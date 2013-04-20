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


public class SimpleDateFormat extends DateFormat {

    private DateTimeFormat format;

    public SimpleDateFormat()
    {
        super();
    }


    protected SimpleDateFormat( DateTimeFormat format )
    {
        this.format = format;
    }

    public SimpleDateFormat( String pattern )
    {
        applyPattern( pattern );
    }

    public void applyPattern (String pattern)
    {
        this.format = DateTimeFormat.getFormat( pattern );
    }

    public String format( Date date )
    {
        return format.format( date );
    }

    /**
     * Parses text and returns the corresponding date object.
     */
    public Date parse( String source )
    {
        return format.parse( source );
    }

}
