/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.serialtest.client;

import com.totsp.gwittir.client.beans.annotations.Introspectable;

/**
 *
 * @author kebernet
 */
@Introspectable
public class TestChildBean {


    private boolean booleanProperty;

    /**
     * Get the value of booleanProperty
     *
     * @return the value of booleanProperty
     */
    public boolean isBooleanProperty() {
        return this.booleanProperty;
    }

    /**
     * Set the value of booleanProperty
     *
     * @param newbooleanProperty new value of booleanProperty
     */
    public void setBooleanProperty(boolean newbooleanProperty) {
        this.booleanProperty = newbooleanProperty;
    }



}
