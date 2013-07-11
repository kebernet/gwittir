package com.totsp.gwittir.stack;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.logging.shared.RemoteLoggingService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.totsp.gwittir.util.Base64;
import com.totsp.gwittir.util.compress.HuffmanEncoder;

import java.util.HashMap;

/**
 *
 */
public class ErrorCodeCreator {

    public <T extends Throwable> String encode(T throwable) throws SerializationException {
        SerializationStreamFactory streamFactory = GWT.create(RemoteLoggingService.class);
        SerializationStreamWriter streamWriter = streamFactory.createStreamWriter();
        streamWriter.writeObject(throwable);
        String serializedObj = streamWriter.toString();
        HuffmanEncoder encoder = new HuffmanEncoder();
        HashMap<Character,String> map =  new HashMap<Character,String>();

        SerializationStreamFactory compressedFactory = GWT.create(Compressed.class);
        streamWriter = compressedFactory.createStreamWriter();
        streamWriter.writeObject(map);
        streamWriter.writeObject(serializedObj);
        byte[] data = encoder.encode(serializedObj, map);

        String encoded = Base64.encode(data);
        return encoded;
    }
}
