/*
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
package com.totsp.gwittir.util;

/**
 * Utils for working with arrays as Objects with GWT (where Class and
 * Arrays are unavail).
 *
 * @author ccollins
 */
public class ArrayUtils {
    public static boolean isArray(Object obj) {
        boolean result = false;

        if (obj != null) {
            if (obj instanceof char[]) {
                result = true;
            } else if (obj instanceof short[]) {
                result = true;
            } else if (obj instanceof int[]) {
                result = true;
            } else if (obj instanceof long[]) {
                result = true;
            } else if (obj instanceof float[]) {
                result = true;
            } else if (obj instanceof double[]) {
                result = true;
            } else if (obj instanceof boolean[]) {
                result = true;
            } else if (obj instanceof Object[]) {
                result = true;
            }
        }

        return result;
    }

    public static int getArrayLength(Object obj) {
        int result = 0;

        if ((obj != null) && isArray(obj)) {
            if (obj instanceof char[]) {
                char[] a = (char[]) obj;
                result = a.length;
            } else if (obj instanceof short[]) {
                short[] a = (short[]) obj;
                result = a.length;
            } else if (obj instanceof int[]) {
                int[] a = (int[]) obj;
                result = a.length;
            } else if (obj instanceof long[]) {
                long[] a = (long[]) obj;
                result = a.length;
            } else if (obj instanceof float[]) {
                float[] a = (float[]) obj;
                result = a.length;
            } else if (obj instanceof double[]) {
                double[] a = (double[]) obj;
                result = a.length;
            } else if (obj instanceof boolean[]) {
                boolean[] a = (boolean[]) obj;
                result = a.length;
            } else if (obj instanceof Object[]) {
                Object[] a = (Object[]) obj;
                result = a.length;
            }
        }

        return result;
    }

    public static Object getArrayElement(Object obj, int i) {
        Object result = null;

        if ((obj != null) && isArray(obj)) {
            if (obj instanceof char[]) {
                char[] a = (char[]) obj;
                result = new Character(a[i]);
            } else if (obj instanceof short[]) {
                short[] a = (short[]) obj;
                result = new Short(a[i]);
            } else if (obj instanceof int[]) {
                int[] a = (int[]) obj;
                result = new Integer(a[i]);
            } else if (obj instanceof long[]) {
                long[] a = (long[]) obj;
                result = new Long(a[i]);
            } else if (obj instanceof float[]) {
                float[] a = (float[]) obj;
                result = new Float(a[i]);
            } else if (obj instanceof double[]) {
                double[] a = (double[]) obj;
                result = new Double(a[i]);
            } else if (obj instanceof boolean[]) {
                boolean[] a = (boolean[]) obj;
                result = new Boolean(a[i]);
            } else if (obj instanceof Object[]) {
                Object[] a = (Object[]) obj;
                result = a[i];
            }
        }

        return result;
    }

    public static int indexOf( Object[] array, Object find ){
        for( int i=0; array!= null && i < array.length; i++ ){
            if( array[i] == find ){
                return i;
            }
        }
        return -1;
    }

    public static void copyArray(Object[] from, Object[] to){
        for(int i=0; i < from.length; i++){
            to[i] = from[i];
        }
    }

    public static void copyArray(int[] from, int[] to){
        for(int i=0; i < from.length; i++){
            to[i] = from[i];
        }
    }
}
