/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.util;

import junit.framework.TestCase;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author kebernet
 */
public class MD5Test extends TestCase {
    public MD5Test(String testName) {
        super(testName);
    }

    public static String MD5(String text)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");

        byte[] md5hash = new byte[16];
        md.update(text.getBytes("UTF-8"), 0, text.length());
        md5hash = md.digest();

        return toHexString(md5hash);
    }

    public void testMD5() throws Exception {
        MD5 md5 = new MD5("This is a test.");
        String client = md5.asHex();
        String server = MD5("This is a test.");
        assertEquals(server, client);
    }

    private static String toHexString(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;

            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }

                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }

        return buf.toString();
    }
}
