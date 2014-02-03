package com.totsp.gwittir.json.test;

import com.totsp.gwittir.json.JSONDiscriminatorValue;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 2/3/14
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
@JSONDiscriminatorValue("Other")
public class OtherSubSub extends TestSubclass {

    private boolean other = false;

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtherSubSub)) return false;
        if (!super.equals(o)) return false;

        OtherSubSub that = (OtherSubSub) o;

        if (other != that.other) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (other ? 1 : 0);
        return result;
    }
}
