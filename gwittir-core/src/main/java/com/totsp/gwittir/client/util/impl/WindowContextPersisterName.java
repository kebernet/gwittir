package com.totsp.gwittir.client.util.impl;

import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.totsp.gwittir.client.util.HistoryTokenizer;
import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;

public class WindowContextPersisterName extends AbstractWindowContextPersister {

	
	@Override
	public int getByteLimit() {
		return 4096;
	}

	@Override
	public Map<String, String> getWindowContextData() {
		HistoryTokenizer tok = new HistoryTokenizer(getWindowName());
		return tok.getTokensMap();
	}

	@Override
	public void init(final WindowContextCallback listener) {
		DeferredCommand.addCommand(new Command(){
			public void execute() {
				listener.onInitialized();
			}
		});
		
	}

	@Override
	public void storeWindowContextData(Map<String, String> windowContextData) {
		HistoryTokenizer tok = new HistoryTokenizer(windowContextData);
		setWindowName(tok.tokenize());
		
	}
	
	private static native String getWindowName()
	/*-{ var name = $wnd.name; return name == undefined ?  null : name;}-*/;

	private static native void setWindowName(String name)
	/*-{ $wnd.name = name; return; }-*/;
	
}
