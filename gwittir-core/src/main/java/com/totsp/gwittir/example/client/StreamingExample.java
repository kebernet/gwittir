package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.mvc.stream.StreamServiceCallback;
import com.totsp.gwittir.mvc.stream.impl.StreamingServiceStub;
import com.totsp.gwittir.mvc.ui.BoundVerticalPanel;
import com.totsp.gwittir.mvc.ui.Button;
import com.totsp.gwittir.mvc.ui.Label;
import com.totsp.gwittir.mvc.ui.util.BoundWidgetTypeFactory;

public class StreamingExample extends BoundVerticalPanel<Object> {

	
	public StreamingExample(){
		super(new BoundWidgetTypeFactory(),null);
		
		Button stream = new Button("Stream!");
        stream.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    ExampleStreamServiceAsync ser = (ExampleStreamServiceAsync) GWT.create(ExampleStreamService.class);
                    StreamingServiceStub stub = (StreamingServiceStub) ser;
                    stub.setServicePath(GWT.getModuleBaseURL() +
                        "ExampleStreamService");
                    ser.getResults(5, "foo",
                        new StreamServiceCallback<MyClass>() {
                            public void onReceive(MyClass object) {
                            	add( new Label("Got: " + object.getName()));
                            }

                            public void onError(Throwable thrown) {
                                Window.alert("Thrown: " + thrown.toString());
                            }

                            public void onComplete() {
                                add( new Label("complete."));
                            }
                        });
                }
            });
        
        add(stream);
	}
}
