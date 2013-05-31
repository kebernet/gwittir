package com.totsp.gwittir.compat;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 5/21/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestTest {


    @Test
    public void test() {
        for(double i= 0.1D; i < 5; i=i*1.22D){
            System.out.println(Double.valueOf(i).hashCode());
        }


    }
}
