package com.totsp.gwittir.client.util;

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
}
