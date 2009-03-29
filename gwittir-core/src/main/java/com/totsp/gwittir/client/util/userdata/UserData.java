/*
 * Copyright 2009 Robert "kebernet" Cooper
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.totsp.gwittir.client.util.userdata;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 * @author kebernet
 */
public class UserData {

    public static final UserData INSTANCE = new UserData();

    private Element element = null;
    private boolean loaded = false;
    private UserData(){
        this.element = DOM.createElement("span");
        DOM.setStyleAttribute(element, "behavior", "url(#default#userData)");
        DOM.setStyleAttribute(element, "position", "absolute");
        DOM.setStyleAttribute(element, "right", "0px");
        DOM.setStyleAttribute(element, "bottom", "0px");
        DOM.appendChild(RootPanel.getBodyElement(), element);
        Window.addWindowCloseListener(new WindowCloseListener(){

            public String onWindowClosing() {
                return null;
            }

            public void onWindowClosed() {
                store(element);
            }

        });
    }


    private native void load(Element e) /*-{
        e.load("gwittir");
     }-*/;

    private native String getNative(Element e, String key)/*-{
        var ret e.getAttribute(key);
        return ret == undefine ? null : ret;
     }-*/;

    public String get(String key){
        if(!loaded){
            load(element);
        }
        return getNative(this.element, key);
    }

    public void set(String key, String value){
        if(!loaded){
            load(element);
        }
        setNative(this.element, key, value);
    }

    private native void setNative(Element e, String key, String value)/*-{
        e.setAttribute(key, value);
     }-*/;

    private native void store(Element e)/*-{
        e.save("gwittir");
    }-*/;
}
