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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestChildBean other = (TestChildBean) obj;
        if (this.booleanProperty != other.booleanProperty) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.booleanProperty ? 1 : 0);
        return hash;
    }


}
