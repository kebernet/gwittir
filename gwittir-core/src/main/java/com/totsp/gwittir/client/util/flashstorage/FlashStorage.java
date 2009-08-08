package com.totsp.gwittir.client.util.flashstorage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.RootPanel;

import com.totsp.gwittir.client.jsni.JavaScriptObjectDecorator;
import com.totsp.gwittir.client.ui.FlashWidget;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class FlashStorage extends JavaScriptObject {
    private static final FlashWidget WIDGET = new FlashWidget(GWT.getModuleBaseURL() + "flso.swf");

    static {
        WIDGET.setId("gwittir-FlastLocalStorage");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("AllowScriptAccess", "always");
        WIDGET.setParameters(params);
        WIDGET.setHeight("100px");
        WIDGET.setWidth("100px");
        GWT.log(WIDGET.toString(), null);
    }

    protected FlashStorage() {
    }

    public static final FlashStorage getInstance() {
        return WIDGET.getAsJSO(FlashStorage.class);
    }

    public static final void initialize(final StartupCallback callback) {
        if (WIDGET.isAttached()) {
            DeferredCommand.addCommand(
                new Command() {
                    public void execute() {
                        callback.onStart();
                    }
                });
        } else {
            registerCallback(callback);
            RootPanel.get()
                     .add(WIDGET);
        }
    }

    public final void setLocal(String name, Map<String, String> values) {
        JavaScriptObjectDecorator decorator = new JavaScriptObjectDecorator();

        for (Entry<String, String> entry : values.entrySet()) {
            decorator.setStringProperty(entry.getKey(), entry.getValue());
        }

        this.setLocalNative(name, decorator.getObject());
    }

    public final Map<String, String> getLocal(String name) {
        JavaScriptObjectDecorator decorator = new JavaScriptObjectDecorator(this.getLocalNative(name));
        HashMap<String, String> results = new HashMap<String, String>();

        for (String key : decorator.getProperties()) {
            results.put(key, decorator.getStringProperty(key));
        }

        return results;
    }

    public final native void flushAll() /*-{
    this.flushAll();
    }-*/;

    public final native void set(String localName, String key, String value) /*-{
    this.set(localName, key, value);
    }-*/;

    @SuppressWarnings("unused")
    private static final void delayCallback(final StartupCallback callback) {
        DeferredCommand.addCommand(
            new Command() {
                public void execute() {
                    callback.onStart();
                }
            });
    }

    private static final native void registerCallback(final StartupCallback callback);

    private final native void setLocalNative(String name, JavaScriptObject value) /*-{
    this.setLocal(name, value);
    }-*/;

    private final native JavaScriptObject getLocalNative(String name) /*-{
    var result = this.getLocal(name);
    return result == null ? new Object(): result;
    }-*/;
}
