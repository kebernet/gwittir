/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util;

/**
 *
 * @author kebernet
 */
public class StringUtil {


    public static String fromUtf8ByteArray(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        byte[] four = new byte[4];
        for(int i= 0; i < bytes.length -2; i+=2){
            four[3] = bytes[ 3+i];
            int intVal = byteArrayToInt(four);
            sb = sb.append( (char)  intVal);
        }
        return sb.toString();
    }

    public static String fromUtf16ByteArray(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        byte[] four = new byte[4];
        for(int i= 0; i < bytes.length -2; i+=2){
            four[2] = bytes[ 2+i];
            four[3] = bytes[ 3+i];
            int intVal = byteArrayToInt(four);
            sb = sb.append( (char)  intVal);
        }
        return sb.toString();
    }

    public static byte[] toUtf16ByteArray(String source){
        byte[] bytes = new byte[source.length()*2 + 2];
        bytes[0] = -2;
        bytes[1] = -1;
         byte[] four = new byte[4];
        for(int i = 0; i < source.length() ; i++ ){
            intToByteArray( four, (int) source.charAt(i));
            bytes[2+2*i] = four[2];
            bytes[3+2*i] = four[3];
        }
        return bytes;
    }

    public static byte[] toUtf8ByteArray(String source){
        byte[] bytes = new byte[source.length()];
        byte[] four =  new byte[4];
       for(int i = 0; i < source.length() ; i++ ){
            intToByteArray( four, (int) source.charAt(i));
            if(four[2] > 0 ){
                throw new RuntimeException("Encoding not valid for string. Try UTF-8");
            }
            bytes[i] = four[3];
        }
        return bytes;
    }

    
     private static void intToByteArray(byte[] bytes, int i){
            bytes[0] =(byte)( i >> 24 );
            bytes[1] =(byte)( (i << 8) >> 24 );
            bytes[2] =(byte)( (i << 16) >> 24 );
            bytes[3] =(byte)( (i << 24) >> 24 );
     }

     private static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }

}
