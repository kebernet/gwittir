package java.net;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 9/12/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.UnsupportedEncodingException;

public class URLEncoder {

    public static String encode(String url){
        try{
        return encode(url, "utf-8");
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String encode(String url, String charset) throws java.io.UnsupportedEncodingException {
        if (!charset.equalsIgnoreCase("utf-8")) {
            throw new UnsupportedEncodingException();
        }
        return com.google.gwt.http.client.URL.encodeQueryString(url);
    }
}