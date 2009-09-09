/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.serialtest.client;

import com.totsp.gwittir.client.beans.annotations.Introspectable;

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
}
