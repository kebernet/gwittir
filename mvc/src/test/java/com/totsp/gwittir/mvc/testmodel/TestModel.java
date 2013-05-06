package com.totsp.gwittir.mvc.testmodel;

import com.totsp.gwittir.introspection.util.ToStringBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestModel {
    private String name;
    private char[] chars;
    private short[] shorts;
    private int[] ints;
    private long[] longs;
    private float[] floats;
    private double[] doubles;
    private List lister;
    private Map mapper;

    public TestModel() {
        name = "TestModel";
        chars = new char[] { 'a', 'b', 'c', 'd' };
        shorts = new short[] { 1, 2, 3, 4, 5 };
        ints = new int[] { 1, 2, 3, 4, 5 };
        longs = new long[] { 1L, 2L, 3L, 4L, 5L };
        floats = new float[] { 1F, 2F, 3F, 4F, 5F };
        doubles = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0 };
        lister = new ArrayList();
        mapper = new HashMap();

        Person p1 = new Person("first1", "last1", 15);
        Person p2 = new Person("first2", "last2", 16);
        Person p3 = new Person("first3", "last3", 17);
        lister.add(p1);
        lister.add(p2);
        lister.add(p3);
        mapper.put("1", p1);
        mapper.put("2", p2);
        mapper.put("3", p3);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public short[] getShorts() {
        return shorts;
    }

    public void setShorts(short[] shorts) {
        this.shorts = shorts;
    }

    public int[] getInts() {
        return ints;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public long[] getLongs() {
        return longs;
    }

    public void setLongs(long[] longs) {
        this.longs = longs;
    }

    public float[] getFloats() {
        return floats;
    }

    public void setFloats(float[] floats) {
        this.floats = floats;
    }

    public double[] getDoubles() {
        return doubles;
    }

    public void setDoubles(double[] doubles) {
        this.doubles = doubles;
    }

    public List getLister() {
        return lister;
    }

    public void setLister(List lister) {
        this.lister = lister;
    }

    public Map getMapper() {
        return mapper;
    }

    public void setMapper(Map mapper) {
        this.mapper = mapper;
    }

    public String toString() {
        ToStringBean tsb = new ToStringBean(this);

        return tsb.toString();
    }
}
