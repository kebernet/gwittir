package com.totsp.gwittir.service.test;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.totsp.gwittir.client.util.impl.WindowContextPersisterMacShell.MacShellService;

public class MacShellPersisterServlet extends RemoteServiceServlet implements MacShellService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 991159990128948816L;
	
	private Map<String, String> map = new HashMap<String, String>();
	 
	public Map<String, String> get() {
		return this.map;
	}

	public void store(Map<String, String> map) {
		this.map = map;
	}

}
