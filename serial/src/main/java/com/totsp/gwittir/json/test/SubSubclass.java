package com.totsp.gwittir.json.test;

import com.totsp.gwittir.json.JSONDiscriminatorValue;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 2/3/14
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
@JSONDiscriminatorValue("Subsub")
public class SubSubclass extends TestSubclass {

    private int subsub = 13;

    public int getSubsub() {
        return subsub;
    }

    public void setSubsub(int subsub) {
        this.subsub = subsub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubSubclass)) return false;
        if (!super.equals(o)) return false;

        SubSubclass that = (SubSubclass) o;

        if (subsub != that.subsub) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + subsub;
        return result;
    }
}
