/*
 * BBean.java
 *
 * Created on November 29, 2006, 4:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test.destination;

import java.util.List;

/**
 *
 * @author cooper
 */
public class BBean {
    
    private String value;
    private ABean parent;
    
    /** Creates a new instance of BBean */
    public BBean() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ABean getParent() {
        return parent;
    }

    public void setParent(ABean parent) {
        this.parent = parent;
    }



    private List<ABean> abeans;

    /**
     * Get the value of abeans
     *
     * @return the value of abeans
     */
    public List<ABean> getAbeans() {
        return this.abeans;
    }

    /**
     * Set the value of abeans
     *
     * @param newabeans new value of abeans
     */
    public void setAbeans(List<ABean> newabeans) {
        this.abeans = newabeans;
    }

}
