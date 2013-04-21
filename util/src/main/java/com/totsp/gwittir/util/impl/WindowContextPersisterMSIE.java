/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.util.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.totsp.gwittir.util.UnavailableException;
import com.totsp.gwittir.util.WindowContext;
import com.totsp.gwittir.util.userdata.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author kebernet
 */
public class WindowContextPersisterMSIE extends AbstractWindowContextPersister {

	private static final String SEPARATOR = " ";
	private static final String WINDOWCONTEXT = "gwittir-windowcontext";
	private static final String ATTRIBUTE_NAMES = "GwittirAttributeNames";
	private UserData userdata;

	@Override
	public int getByteLimit() {
		return 128000;
	}

	@Override
	public Map<String, String> getWindowContextData() {
		Map<String, String> results = new HashMap<String, String>();
		String attributeNamesVal = userdata.get(ATTRIBUTE_NAMES);
		if (attributeNamesVal == null) {
			return results;
		}
		String[] attributeNames = attributeNamesVal.split(SEPARATOR);
		for (String key : attributeNames) {
			if (key.length() > 0) {
				String value = userdata.get(key);
				if (value != null) {
					results.put(key, value);
				}
			}
		}

		return results;
	}

	@Override
	public void storeWindowContextData(Map<String, String> windowContextData) {
		StringBuilder attributeNamesVal = new StringBuilder();
		for (Entry<String, String> entry : windowContextData.entrySet()) {
			attributeNamesVal = attributeNamesVal.append(entry.getKey())
					.append(SEPARATOR);
			userdata.set(entry.getKey(), entry.getValue());
		}
		userdata.set(ATTRIBUTE_NAMES, attributeNamesVal.toString());
		GWT.log(attributeNamesVal.toString(), null);
		GWT.log("After save: " + userdata.get(ATTRIBUTE_NAMES), null);
		userdata.save(WINDOWCONTEXT);

	}

	@Override
	public void init(final WindowContext.WindowContextCallback listener) {
		if (userdata == null) {
			try {
				userdata = UserData.getInstance();
				userdata.load(WINDOWCONTEXT);
			} catch (UnavailableException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to create a userdata tag", e);
			}
			DeferredCommand.addCommand(new Command() {

				public void execute() {
					listener.onInitialized();

				}

			});
		}
	}

}
