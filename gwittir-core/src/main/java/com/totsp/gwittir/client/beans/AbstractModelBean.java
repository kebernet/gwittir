package com.totsp.gwittir.client.beans;

import com.totsp.gwittir.client.util.EqualsHashCodeBean;
import com.totsp.gwittir.client.util.ToStringBean;
import java.beans.PropertyChangeSupport;


/**
 * 
DOCUMENT ME!
 *
 * @author ccollins
 */
public abstract class AbstractModelBean implements Bindable {
    private final ToStringBean toString = new ToStringBean(this);
    private final EqualsHashCodeBean equalsHash = new EqualsHashCodeBean(this);
    protected final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
    public AbstractModelBean() {
    }

    public String toString() {
        return toString.toString();
    }

    public boolean equals(Object obj) {
        return equalsHash.equals(obj);
    }
    
    public int hashCode(){
        return equalsHash.hashCode();
    }
}
