/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.Window;
import com.totsp.gwittir.client.util.domstorage.DOMStorage;
import com.totsp.gwittir.client.util.domstorage.Store;
import com.totsp.gwittir.client.util.domstorage.UnavailableException;

/**
 *
 * @author kebernet
 */
public class DOMStorageTest {

    private Store local;
    private Store session;

    public DOMStorageTest() throws UnavailableException{
        this.local = DOMStorage.getLocal();
        this.session = DOMStorage.getSession();
        Window.alert( "Local[test]"+ local.get("test"));
        Window.alert("Session[test]"+ session.get("test"));
    }

    public void saveValues(){
        local.set("test", "testValue!");
        session.set("test", "Session Test Value!");
    }

}
