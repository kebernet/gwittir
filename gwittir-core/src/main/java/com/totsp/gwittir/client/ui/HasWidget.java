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
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface HasWidget {
    public void setWidget(Widget widget);

    public Widget getWidget();
}
