/*
 * UnitsParser.java
 *
 * Created on August 3, 2007, 3:40 PM
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

package com.totsp.gwittir.client.util;

/**
 *
 * @author cooper
 */
public class UnitsParser {
    private static final String NUMBERS="-1234567890";
    
    /** Creates a new instance of UnitsParser */
    private UnitsParser() {
    }
    
    public static UnitValue parse( String input ){
        String intPart = "";
        int i=0;
        for( ; i < input.length(); i++ ){
            if( NUMBERS.indexOf( input.charAt(i) ) != -1 ){
                intPart +=input.charAt(i);
            } else {
                break;
            }
        }
        UnitValue value = new UnitValue();
        value.value = intPart.length() > 0 ? Integer.parseInt( intPart ) : 0;
        value.units = input.substring( i, input.length() );
        return value;
    }
    
    public static class UnitValue {
        public int value;
        public String units;
    }
    
}
