package com.totsp.gwittir.client.util.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;

import java.util.Map;


public class WindowContextPersisterMacShell extends AbstractWindowContextPersister {
    Map<String, String> values;

    @Override
    public int getByteLimit() {
        return 512000;
    }

    @Override
    public Map<String, String> getWindowContextData() {
        return this.values;
    }

    @Override
    public void init(final WindowContextCallback listener) {
        assert !GWT.isScript() : "How did you get this?";
        MacShellServiceAsync.Util.INSTANCE.get(
            new AsyncCallback<Map<String, String>>() {
                public void onFailure(Throwable caught) {
                    Window.alert("What the hell are you doing?");
                    GWT.log(null, caught);
                }

                public void onSuccess(Map<String, String> result) {
                    values = result;
                    listener.onInitialized();
                }
            });
    }

    @Override
    public void storeWindowContextData(Map<String, String> windowContextData) {
        MacShellServiceAsync.Util.INSTANCE.store(
            windowContextData,
            new AsyncCallback<Object>() {
                public void onFailure(Throwable caught) {
                    Window.alert("Failed to store.");
                }

                public void onSuccess(Object result) {
                    GWT.log("Stored remotely on mac shell.", null);
                }
            });
    }

    public static interface MacShellService extends RemoteService {
        Map<String, String> get();

        void store(Map<String, String> map);
    }

    public static interface MacShellServiceAsync {
        void get(AsyncCallback<Map<String, String>> callback);

        void store(Map<String, String> map, AsyncCallback<Object> callback);

        public static class Util {
            public static final MacShellServiceAsync INSTANCE = (MacShellServiceAsync) GWT.create(
                    MacShellService.class);

            static {
                ((ServiceDefTarget) INSTANCE).setServiceEntryPoint(GWT.getModuleBaseURL() + "MacShellPersister");
            }
        }
    }
}
