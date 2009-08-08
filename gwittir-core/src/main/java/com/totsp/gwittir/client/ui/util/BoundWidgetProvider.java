/*
 * BoundWidgetProvider.java
 *
 * Created on June 14, 2007, 4:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui.util;

import com.totsp.gwittir.client.ui.*;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface BoundWidgetProvider<T extends BoundWidget<?>> {
    public T get();
}
