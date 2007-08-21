/*
 * DimensionsIE6.java
 *
 * Created on August 21, 2007, 5:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.fx.rebind;

import com.google.gwt.user.client.Element;
import com.totsp.gwittir.client.util.UnitsParser;

/**
 *
 * @author rcooper
 */
public class DimensionsIE6 extends Dimensions {
    
    /** Creates a new instance of DimensionsIE6 */
    public DimensionsIE6() {
    }

    public native String getMarginTop(Element e)/*-{
        return e.currentStyle.marginTop
    }-*/;
    
    public native String getMarginBottom(Element e)/*-{
        return e.currentStyle.marginBottom
    }-*/;
}
