/*
 * Dimensions.java
 *
 * Created on August 21, 2007, 5:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.fx.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;

import com.totsp.gwittir.client.util.UnitsParser;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class Dimensions {
    public static final Dimensions INSTANCE = (Dimensions) GWT.create(Dimensions.class);

    /** Creates a new instance of Dimensions */
    protected Dimensions() {
    }

    public native String getMarginBottom(Element e) /*-{
    return  document.defaultView.getComputedStyle(e, '').getPropertyValue('margin-bottom');
    }-*/;

    public native String getMarginHeight(Element e) /*-{
    return  document.defaultView.getComputedStyle(e, '').getPropertyValue('margin-height');
    }-*/;

    public native String getMarginTop(Element e) /*-{
    return document.defaultView.getComputedStyle(e, '').getPropertyValue('margin-top');
    }-*/;

    public int getTotalVerticalMargin(Element e) {
        return UnitsParser.parse(getMarginTop(e)).value + UnitsParser.parse(getMarginBottom(e)).value;
    }
}
