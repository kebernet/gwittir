package com.totsp.gwittir.client.beans;

import com.totsp.gwittir.client.util.EqualsHashCodeBean;
import com.totsp.gwittir.client.util.ToStringBean;


/**
 * 
DOCUMENT ME!
 *
 * @author ccollins
 */
public abstract class AbstractModelBean implements Introspectable {
    public AbstractModelBean() {
    }

    public String toString() {
        ToStringBean b = new ToStringBean(this);

        return b.toString();
    }

    public boolean equals(Object obj) {
        EqualsHashCodeBean b = new EqualsHashCodeBean(this);

        return b.equals(obj);
    }
}
