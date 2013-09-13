package com.totsp.gwittir.stack.test;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.SerializationException;
import com.totsp.gwittir.stack.ErrorCodeCreator;

/**
 *
 */
public class TestEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        try{
            throw new RuntimeException("This is my exception. There are many like it but this one is mine.");
        } catch(Throwable t){
            try {
                Window.alert(new ErrorCodeCreator().encode(t));
            } catch (SerializationException e) {
                GWT.log("", e);
            }
        }
    }
}
