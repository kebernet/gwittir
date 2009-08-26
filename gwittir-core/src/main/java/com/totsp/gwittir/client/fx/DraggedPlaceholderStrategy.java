/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.fx;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @author kebernet
 */
public interface DraggedPlaceholderStrategy {
    public void setupPlaceholderElement(Widget dragged, Element element);
}
