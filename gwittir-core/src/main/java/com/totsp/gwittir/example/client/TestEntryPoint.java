package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.totsp.gwittir.client.util.WindowContext;
import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;

public class TestEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		
		WindowContext.INSTANCE.initialize(new WindowContextCallback(){

			public void onInitialized() {
				Window.alert(""+WindowContext.INSTANCE.get("TestValue"));
		    	WindowContext.INSTANCE.put("TestValue", "I am saved to the window context");
			}
    		
    	});
	}
}
