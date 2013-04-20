/*
 * Copyright 2009 Robert "kebernet" Cooper
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
package com.totsp.gwittir.mvc.util;


/**
 * A utitlity class containing methods related to Strings.
 * @author kebernet
 */
public class StringUtil {

    /** Builds a String from a UTF-16 byte array
     *
     * @param bytes Array of double-byte sequences.
     * @return UTF-16 String.
     */
    public static String fromUtf16ByteArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        byte[] four = new byte[4];

        for (int i = 0; i < (bytes.length - 2); i += 2) {
            four[2] = bytes[2 + i];
            four[3] = bytes[3 + i];

            int intVal = byteArrayToInt(four);
            sb = sb.append((char) intVal);
        }

        return sb.toString();
    }

    /** Builds a String from a UTF-8 byte array
     *
     * @param bytes Array of single-byte sequences.
     * @return UTF-8 String.
     */
    public static String fromUtf8ByteArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        byte[] four = new byte[4];

        for (int i = 0; i < (bytes.length - 2); i += 2) {
            four[3] = bytes[3 + i];

            int intVal = byteArrayToInt(four);
            sb = sb.append((char) intVal);
        }

        return sb.toString();
    }

    /**
     * Converts a String to a UTF-16 byte array
     * @param source String
     * @return String as bytes with UTF-16 header.
     */
    public static byte[] toUtf16ByteArray(String source) {
        byte[] bytes = new byte[(source.length() * 2) + 2];
        bytes[0] = -2;
        bytes[1] = -1;

        byte[] four = new byte[4];

        for (int i = 0; i < source.length(); i++) {
            intToByteArray(four, (int) source.charAt(i));
            bytes[2 + (2 * i)] = four[2];
            bytes[3 + (2 * i)] = four[3];
        }

        return bytes;
    }

    /**
     * Converts a String to a UTF-8 byte array
     * @param source String
     * @return String as bytes
     */
    public static byte[] toUtf8ByteArray(String source) {
        byte[] bytes = new byte[source.length()];
        byte[] four = new byte[4];

        for (int i = 0; i < source.length(); i++) {
            intToByteArray(four, (int) source.charAt(i));

            if (four[2] > 0) {
                throw new RuntimeException(
                    "Encoding not valid for string. Try UTF-8");
            }

            bytes[i] = four[3];
        }

        return bytes;
    }

    private static int byteArrayToInt(byte[] b) {
        int value = 0;

        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += ((b[i] & 0x000000FF) << shift);
        }

        return value;
    }

    private static void intToByteArray(byte[] bytes, int i) {
        bytes[0] = (byte) (i >> 24);
        bytes[1] = (byte) ((i << 8) >> 24);
        bytes[2] = (byte) ((i << 16) >> 24);
        bytes[3] = (byte) ((i << 24) >> 24);
    }
}
