/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rest.client.transports;

/**
 *
 * @author kebernet
 */
public class HTTPTransportException extends Exception {

    private int responseCode;
    private String body;

    public HTTPTransportException(String message, int responseCode, String body){
        super(message);
        this.responseCode = responseCode;
        this.body = body;
    }



    public int getResponseCode(){
        return this.responseCode;
    }

    public String getBody(){
        return this.body;
    }

}
