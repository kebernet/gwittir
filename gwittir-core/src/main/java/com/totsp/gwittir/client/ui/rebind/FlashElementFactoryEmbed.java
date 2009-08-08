package com.totsp.gwittir.client.ui.rebind;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class FlashElementFactoryEmbed extends FlashElementFactory {
    @Override
    public void setParams(Element e, Set<String> remove, Map<String, String> params) {
        for (String r : remove) {
            DOM.removeElementAttribute(e, r);
        }

        for (Entry<String, String> entry : params.entrySet()) {
            DOM.setElementAttribute(e, entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Element create(String url) {
        Element e = DOM.createElement("embed");
        DOM.setElementAttribute(e, "src", url);
        DOM.setElementAttribute(e, "quality", "high");
        DOM.setElementAttribute(e, "type", "application/x-shockwave-flash");
        DOM.setElementAttribute(e, "pluginspage", "http://www.adobe.com/go/getflashplayer");
        DOM.setElementAttribute(e, "play", "true");
        DOM.setElementAttribute(e, "loop", "false");
        DOM.setElementAttribute(e, "align", "middle");

        return e;
    }
}
