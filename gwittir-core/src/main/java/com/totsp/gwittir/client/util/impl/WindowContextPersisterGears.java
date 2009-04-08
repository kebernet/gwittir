/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.util.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gears.client.Factory;
import com.google.gwt.gears.client.GearsException;
import com.google.gwt.gears.client.database.Database;
import com.google.gwt.gears.client.database.DatabaseException;
import com.google.gwt.gears.client.database.ResultSet;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.SerializationException;

import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;
import com.totsp.gwittir.client.util.WindowContextItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 *
 * @author kebernet
 */
public class WindowContextPersisterGears extends AbstractWindowContextPersister {
    private Database db;

    protected WindowContextPersisterGears() {
        try {
            db = Factory.getInstance().createDatabase();
            db.open("gwittir-windowcontext");
            db.execute(
                "CREATE TABLE IF NOT EXISTS windowcontext (key TEXT, value TEXT)");
        } catch (GearsException e) {
            Window.alert(e.toString());
        }
    }

    @Override
    public int getByteLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Map<String, String> getWindowContextData() {
        HashMap<String, String> map = new HashMap<String, String>();

        try {
            ResultSet rs = db.execute("SELECT key, value FROM windowcontext");
            while (rs.isValidRow()) {
                map.put(rs.getFieldAsString(0), rs.getFieldAsString(1));
                rs.next();
            }
        } catch (DatabaseException ex) {
            GWT.log(null, ex);
        }

        return map;
    }

    @Override
    public void storeWindowContextData(
        Map<String, WindowContextItem> windowContextData) {
        for (Entry<String, WindowContextItem> entry : windowContextData.entrySet()) {
            try {
                String[] vals = new String[] {
                        entry.getKey(), this.serializeObject(entry.getValue())
                    };
                Window.alert( vals[0]+"::"+vals[1]);
                db.execute("INSERT INTO windowcontext (key, value) VALUES( ? , ? )",
                    vals);
            } catch (SerializationException ex) {
                GWT.log(null, ex);
                Window.alert(""+ex);
            } catch (DatabaseException ex) {
                GWT.log(null, ex);
                Window.alert(""+ex);
            }
        }
    }

    @Override
    public void init(WindowContextCallback listener) {
        listener.onInitialized();
    }
}
