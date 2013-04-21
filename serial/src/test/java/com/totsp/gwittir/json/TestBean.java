/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.json;

import com.totsp.gwittir.introspection.Introspectable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * @author kebernet
 */
@Introspectable
public class TestBean {
    private HashSet<String> stringHash;
    private List<Integer> integerList;
    private Set<TestChildBean> childSet;
    private String string;
    private TestChildBean child;
    private int intProperty;
    private int integer;

    /**
     * Set the value of child
     *
     * @param newchild new value of child
     */
    public void setChild(TestChildBean newchild) {
        this.child = newchild;
    }

    /**
     * Get the value of child
     *
     * @return the value of child
     */
    public TestChildBean getChild() {
        return this.child;
    }

    /**
     * Set the value of childSet
     *
     * @param newchildSet new value of childSet
     */
    public void setChildSet(Set<TestChildBean> newchildSet) {
        this.childSet = newchildSet;
    }

    /**
     * Get the value of childSet
     *
     * @return the value of childSet
     */
    public Set<TestChildBean> getChildSet() {
        return this.childSet;
    }

    /**
     * Set the value of intProperty
     *
     * @param newintProperty new value of intProperty
     */
    public void setIntProperty(int newintProperty) {
        this.intProperty = newintProperty;
    }

    /**
     * Get the value of intProperty
     *
     * @return the value of intProperty
     */
    public int getIntProperty() {
        return this.intProperty;
    }

    /**
     * Set the value of integer
     *
     * @param newinteger new value of integer
     */
    public void setInteger(int newinteger) {
        this.integer = newinteger;
    }

    /**
     * Get the value of integer
     *
     * @return the value of integer
     */
    public int getInteger() {
        return this.integer;
    }

    /**
     * Set the value of integerList
     *
     * @param newintegerList new value of integerList
     */
    public void setIntegerList(List<Integer> newintegerList) {
        this.integerList = newintegerList;
    }

    /**
     * Get the value of integerList
     *
     * @return the value of integerList
     */
    @JSONField("my-list-of-integers")
    public List<Integer> getIntegerList() {
        return this.integerList;
    }

    /**
     * Set the value of string
     *
     * @param newstring new value of string
     */
    public void setString(String newstring) {
        this.string = newstring;
    }

    /**
     * Get the value of string
     *
     * @return the value of string
     */
    public String getString() {
        return this.string;
    }

    /**
     * Set the value of stringHash
     *
     * @param newstringHash new value of stringHash
     */
    public void setStringHash(HashSet<String> newstringHash) {
        this.stringHash = newstringHash;
    }

    /**
     * Get the value of stringHash
     *
     * @return the value of stringHash
     */
    public HashSet<String> getStringHash() {
        return this.stringHash;
    }


    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestBean other = (TestBean) obj;
        if (this.stringHash != other.stringHash && (this.stringHash == null || !this.stringHash.equals(other.stringHash))) {
            return false;
        }
        if (this.integerList != other.integerList && (this.integerList == null || !this.integerList.equals(other.integerList))) {
            return false;
        }
        if (this.childSet != other.childSet && (this.childSet == null || !this.childSet.equals(other.childSet))) {
            return false;
        }
        if ((this.string == null) ? (other.string != null) : !this.string.equals(other.string)) {
            return false;
        }
        if (this.child != other.child && (this.child == null || !this.child.equals(other.child))) {
            return false;
        }
        if (this.intProperty != other.intProperty) {
            return false;
        }
        if (this.integer != other.integer) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.stringHash != null ? this.stringHash.hashCode() : 0);
        hash = 29 * hash + (this.integerList != null ? this.integerList.hashCode() : 0);
        hash = 29 * hash + (this.childSet != null ? this.childSet.hashCode() : 0);
        hash = 29 * hash + (this.string != null ? this.string.hashCode() : 0);
        hash = 29 * hash + (this.child != null ? this.child.hashCode() : 0);
        hash = 29 * hash + this.intProperty;
        hash = 29 * hash + this.integer;
        return hash;
    }



    private TestChildBean[] arrayProp;

    /**
     * Get the value of arrayProp
     *
     * @return the value of arrayProp
     */
    public TestChildBean[] getArrayProp() {
        return this.arrayProp;
    }

    /**
     * Set the value of arrayProp
     *
     * @param newarrayProp new value of arrayProp
     */
    public void setArrayProp(TestChildBean[] newarrayProp) {
        this.arrayProp = newarrayProp;
    }

}
