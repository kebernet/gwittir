/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.util.WindowContextItem;

import java.io.Serializable;

import java.util.List;


/**
 *
 * @author kebernet
 */
public class TestContextItem implements WindowContextItem {
    /**
     * @serialization-type com.totsp.gwittir.example.client.TestContextItem.SomethingElse
     */
    private List somethings;
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
     * Set the value of somethings
     *
     * @param newsomethings new value of somethings
     */
    public void setSomethings(List newsomethings) {
        this.somethings = newsomethings;
    }

    /**
     * Get the value of somethings
     *
     * @return the value of somethings
     */
    public List getSomethings() {
        return this.somethings;
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

    public static class SomethingElse implements Serializable {
        private String world;

        /**
         * Set the value of world
         *
         * @param newworld new value of world
         */
        public void setWorld(String newworld) {
            this.world = newworld;
        }

        /**
         * Get the value of world
         *
         * @return the value of world
         */
        public String getWorld() {
            return this.world;
        }
    }
}
