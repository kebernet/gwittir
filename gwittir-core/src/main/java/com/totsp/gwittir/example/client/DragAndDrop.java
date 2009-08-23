/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;

/**
 *
 * @author kebernet
 */
public class DragAndDrop extends AbstractBoundWidget<Object> {


    public DragAndDrop(){
        super();
        HorizontalPanel hp = new HorizontalPanel();
        super.initWidget(hp);
    }

    public Object getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
