package com.totsp.gwittir.json.test;

import com.totsp.gwittir.introspection.Introspectable;
import com.totsp.gwittir.json.JSONSubclassed;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 2/3/14
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Introspectable
@JSONSubclassed(discriminator = "@class")
public class TestParent<T extends Serializable> {

    private String parentProp = "Hello";

    public String getParentProp() {
        return parentProp;
    }

    private T serialized = (T) new Date();


    public T getSerialized() {
        return serialized;
    }

    public void setSerialized(T serialized) {
        this.serialized = serialized;
    }

    public void setParentProp(String parentProp) {
        this.parentProp = parentProp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestParent)) return false;

        TestParent that = (TestParent) o;

        if (parentProp != null ? !parentProp.equals(that.parentProp) : that.parentProp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return parentProp != null ? parentProp.hashCode() : 0;
    }
}
