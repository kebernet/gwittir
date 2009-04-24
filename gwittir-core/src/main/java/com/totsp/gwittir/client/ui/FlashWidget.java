package com.totsp.gwittir.client.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.ui.rebind.FlashElementFactory;

public class FlashWidget extends Widget{
	private static final FlashElementFactory FACTORY = GWT.create(FlashElementFactory.class);
	private static final Set<String> EMPTY_SET = new HashSet<String>();
	private Map<String, String> parameters;
	private String id;
	private String url;
	
	public FlashWidget(String url, String id, Map<String,String> parameters){
		assert url != null : "URL cannot be null";
		this.url = url;
		this.parameters = parameters;
		Element e = FACTORY.create(url);
		this.setElement(e);
		this.setParameters(parameters);
		this.setId(id);
		
	}
	
	public FlashWidget(String url){
		this( url, null, new HashMap<String,String>());
	}
	
	public void setParameters(Map<String,String> parameters){
		if(this.isAttached()){
			throw new RuntimeException("Cannot set parameters while attached.");
		}
		FACTORY.setParams(this.getElement(), this.parameters == null ? EMPTY_SET : this.parameters.keySet(), parameters);
		this.parameters = parameters;
		
	}
	public Map<String,String> getParameters(){
		return this.parameters;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
		DOM.setElementProperty(this.getElement(), "id", id);
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public native <T extends JavaScriptObject> T getAsJSO(Class<T> clazz)
	/*-{ return this.@com.totsp.gwittir.client.ui.FlashWidget::getElement()(); }-*/;
}
