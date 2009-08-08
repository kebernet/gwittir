package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


public interface ReadSourceServiceAsync {
    public Request getSource(String classname, AsyncCallback<String> callback);

    public static class Util {
        public static final ReadSourceServiceAsync INSTANCE = GWT.create(ReadSourceService.class);

        static {
            ((ServiceDefTarget) INSTANCE).setServiceEntryPoint(GWT.getModuleBaseURL() + "ReadSourceService");
        }
    }
}
