/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.util.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gears.client.Factory;
import com.google.gwt.gears.client.GearsException;
import com.google.gwt.gears.client.database.Database;
import com.google.gwt.gears.client.database.DatabaseException;
import com.google.gwt.gears.client.database.ResultSet;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;

/**
 * 
 * @author kebernet
 */
public class WindowContextPersisterGears extends AbstractWindowContextPersister {
	private Database db;

	protected WindowContextPersisterGears() {

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
	public void storeWindowContextData(Map<String, String> windowContextData) {
		for (Entry<String, String> entry : windowContextData.entrySet()) {
			try {
				String[] vals = new String[] { entry.getKey(), entry.getValue() };
				Window.alert(vals[0] + "::" + vals[1]);
				db
						.execute(
								"INSERT INTO windowcontext (key, value) VALUES( ? , ? )",
								vals);
			} catch (DatabaseException ex) {
				GWT.log(null, ex);
				Window.alert("" + ex);
			}
		}
	}

	@Override
	public void init(final WindowContextCallback listener) {
		if (this.db == null) {
			try {
				db = Factory.getInstance().createDatabase();
				db.open("gwittir-windowcontext");
				db
						.execute("CREATE TABLE IF NOT EXISTS windowcontext (key TEXT, value TEXT)");
			} catch (GearsException e) {
				Window.alert(e.toString());
			}
			DeferredCommand.addCommand( new Command(){

				public void execute() {
					listener.onInitialized();
					
				}
				
			});
			
		}
	}
}
