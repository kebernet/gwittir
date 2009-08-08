/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.compress;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.totsp.gwittir.client.util.StringUtil;
import java.util.HashMap;

/**
 *
 * @author kebernet
 */
public class HuffmanString implements IsSerializable {

    private HashMap<Character,String> encodingData = new HashMap<Character, String>();
    private int contentLength;
    private byte[] compress;
    private transient String source;
    private static final transient HuffmanEncoder ENC = new HuffmanEncoder();
    private static final transient HuffmanDecoder DEC  = new HuffmanDecoder();

    HuffmanString(){
        super();
    }

    public HuffmanString(String source){
        super();
        this.source = source;
        byte[] data = StringUtil.toUtf16ByteArray(source);
        this.contentLength = data.length;
        this.compress = ENC.encode(source, encodingData);
    }

    @Override
    public String toString(){
        if(this.source != null){
            return this.source;
        } else {
            return this.source = DEC.decode(compress, encodingData, contentLength);
        }
    }
}
