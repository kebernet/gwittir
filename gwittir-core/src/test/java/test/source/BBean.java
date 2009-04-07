/*
 * BBean.java
 *
 * Created on November 29, 2006, 4:09 PM
 *
 */
package test.source;

import java.util.List;


/**
 *
 * @author cooper
 */
public class BBean {
    private ABean parent;
    private List<ABean> abeans;
    private String value;

    /** Creates a new instance of BBean */
    public BBean() {
    }

    /**
     * Set the value of abeans
     *
     * @param newabeans new value of abeans
     */
    public void setAbeans(List<ABean> newabeans) {
        this.abeans = newabeans;
    }

    /**
     * Get the value of abeans
     *
     * @return the value of abeans
     */
    public List<ABean> getAbeans() {
        return this.abeans;
    }

    public void setParent(ABean parent) {
        this.parent = parent;
    }

    public ABean getParent() {
        return parent;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
