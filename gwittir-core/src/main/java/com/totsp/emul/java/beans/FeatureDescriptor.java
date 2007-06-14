/*
 * FeatureDescriptor.java
 *
 * Created on May 17, 2007, 12:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.emul.java.beans;

import java.util.Enumeration;

/**
 *
 * @author rcooper
 */
public class FeatureDescriptor {
    private Object value;
    private boolean preferred;
    private String displayName;
    private boolean expert;
    private boolean hidden;
    private String name;
    private String shortDescription;
    
    /** Creates a new instance of FeatureDescriptor */
    public FeatureDescriptor() {
        super();
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public boolean isPreferred() {
        return preferred;
    }
    
    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public boolean isExpert() {
        return expert;
    }
    
    public void setExpert(boolean expert) {
        this.expert = expert;
    }
    
    public boolean isHidden() {
        return hidden;
    }
    
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getShortDescription() {
        return shortDescription;
    }
    
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    public Enumeration attributeNames(){
        return null;
    }
    
}
