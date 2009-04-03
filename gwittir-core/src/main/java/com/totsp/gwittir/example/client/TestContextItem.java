/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.util.WindowContextItem;


/**
 *
 * @author kebernet
 */
public class TestContextItem implements WindowContextItem {
    private String string;
    private int intPropert;

    /**
     * Set the value of intPropert
     *
     * @param newintPropert new value of intPropert
     */
    public void setIntPropert(int newintPropert) {
        this.intPropert = newintPropert;
    }

    /**
     * Get the value of intPropert
     *
     * @return the value of intPropert
     */
    public int getIntPropert() {
        return this.intPropert;
    }

    /**
     * Set the value of string
     *
     * @param newstring new value of string
     */
    public void setString(String newstring) {
        this.string = newstring;
    }

    /**
     * Get the value of string
     *
     * @return the value of string
     */
    public String getString() {
        return this.string;
    }
}
