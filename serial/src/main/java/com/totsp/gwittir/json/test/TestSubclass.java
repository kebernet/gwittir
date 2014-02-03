package com.totsp.gwittir.json.test;

import com.totsp.gwittir.json.JSONDiscriminatorValue;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 2/3/14
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */

@JSONDiscriminatorValue("Subclass")
public class TestSubclass extends TestParent{

    private String childProperty = "World.";

    public String getChildProperty() {
        return childProperty;
    }

    public void setChildProperty(String childProperty) {
        this.childProperty = childProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSubclass)) return false;
        if (!super.equals(o)) return false;

        TestSubclass that = (TestSubclass) o;

        if (childProperty != null ? !childProperty.equals(that.childProperty) : that.childProperty != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (childProperty != null ? childProperty.hashCode() : 0);
        return result;
    }
}
