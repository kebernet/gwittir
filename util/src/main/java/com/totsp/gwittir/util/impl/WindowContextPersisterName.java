package com.totsp.gwittir.util.impl;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.totsp.gwittir.util.HistoryTokenizer;
import com.totsp.gwittir.util.WindowContext;

import java.util.Map;

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
	public void init(final WindowContext.WindowContextCallback listener) {
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
	/*-{ var name = $wnd.name; alert(name); return name == undefined ?  null : name;}-*/;

	private static native void setWindowName(String name)
	/*-{ alert(name); $wnd.name = name; return; }-*/;
	
}
