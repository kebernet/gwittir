/*
 * ABean.java
 *
 * Created on November 29, 2006, 3:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test.destination;

import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author cooper
 */
public class ABean {
    
    public String stringProperty;
    public int intProperty;
    public String[] arrayProperty;
    public BBean[][] beanArray;
    public Map<String, BBean> beanMap;
    public HashSet<String> stringSet;
    /** Creates a new instance of ABean */
    public ABean() {
    }

    private char charProperty;

    /**
     * Get the value of charProperty
     *
     * @return the value of charProperty
     */
    public char getCharProperty() {
        return this.charProperty;
    }

    /**
     * Set the value of charProperty
     *
     * @param newcharProperty new value of charProperty
     */
    public void setCharProperty(char newcharProperty) {
        this.charProperty = newcharProperty;
    }

    
}
