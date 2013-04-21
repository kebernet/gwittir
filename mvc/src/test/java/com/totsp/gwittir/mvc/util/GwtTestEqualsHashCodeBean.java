package com.totsp.gwittir.mvc.util;

import com.google.gwt.junit.client.GWTTestCase;

import com.totsp.gwittir.mvc.testmodel.Person;

import junit.framework.Assert;


/**
 * 
DOCUMENT ME!
 *
 * @author ccollins
 */
public class GwtTestEqualsHashCodeBean extends GWTTestCase {
    public String getModuleName() {
        return "com.totsp.gwittir.GwittirTest";
    }

    public void testEquals() {
        Person person1 = new Person("first1", "last1", 25);
        Person person2 = new Person("first1", "last1", 25);
        System.out.println("\""+person1+"\"");
        System.out.println("\""+person2+"\"");
        Assert.assertEquals(person1, person2);

        System.out.println("person1 - " + person1);
        System.out.println("person2 - " + person1);

        Person person3 = new Person("first1", "last1", 55);
        Assert.assertFalse(person3.equals(person1));
    }
}
