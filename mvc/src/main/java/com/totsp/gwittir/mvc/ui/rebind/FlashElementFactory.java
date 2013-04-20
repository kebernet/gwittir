package com.totsp.gwittir.mvc.ui.rebind;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class FlashElementFactory {

	private static final String QUALITY = "quality";
	private static final String MOVIE = "movie";
	private static final String VALUE = "value";
	private static final String PARAM = "param";
	private static final String NAME = "name";
	private static final String CLASS_ID = "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000";
	private static final String CODEBASE = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0";
	
	
	public Element create(String url){
		Element e = DOM.createElement("object");
		Element param = DOM.createElement(PARAM);
		DOM.setElementProperty(param, NAME, MOVIE);
		DOM.setElementAttribute(param, VALUE, url);
		DOM.appendChild(e, param);
		param = DOM.createElement(PARAM);
		DOM.setElementProperty(param, NAME, QUALITY);
		DOM.setElementProperty(param, VALUE, "high");
		DOM.appendChild(e, param);
		return e;
	}
	
	public void setParams(Element e, Set<String> remove, Map<String,String> params){
		List<Element> toRemove = new ArrayList<Element>();
		for(int i=0; i < DOM.getChildCount(e); i++ ){
			Element child = DOM.getChild(e, i);
			if(child.getNodeName().equals(PARAM) &&
					remove.contains(child.getNodeName())){
				toRemove.add(child);
			}	
		}
		for(Element child: toRemove){
			DOM.removeChild(e, child);
		}
		
		for(Entry<String, String> entry : params.entrySet() ){
			Element param = DOM.createElement(PARAM);
			DOM.setElementProperty(param, NAME, entry.getKey());
			DOM.setElementProperty(param, VALUE, entry.getValue());
			DOM.appendChild(e, param);
		}
	}
	
}
