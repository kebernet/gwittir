/*
 * HasAWidget.java
 *
 * Created on August 5, 2007, 1:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author rcooper
 */
public interface HasWidget {
    public Widget getWidget();
    public void setWidget(Widget widget);
}
