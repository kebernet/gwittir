package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class TestEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		FlowExamplePanel panel = new FlowExamplePanel();
		RootPanel.get().add(panel);
	}
}
