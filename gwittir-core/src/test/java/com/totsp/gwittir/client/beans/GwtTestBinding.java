/*
 * BindingTest.java
 * JUnit based test
 *
 * Created on November 28, 2007, 2:40 PM
 */
package com.totsp.gwittir.client.beans;

import com.google.gwt.junit.client.GWTTestCase;

import com.totsp.gwittir.client.testmodel.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 * @author rcooper
 */
public class GwtTestBinding extends GWTTestCase {
    public String getModuleName() {
        return "com.totsp.gwittir.GwittirTestBinding";
    }

    public void testBasicDotted() throws Exception {
        System.out.println("start testBasicDotted");

        Person mark = new Person("Mark", "Lanford", 31);
        mark.setSpouse(new Person("Benay", "Krissel", 37));

        Person benayCopy = new Person();
        Binding b = new Binding(benayCopy, "firstName", mark, "spouse.firstName");
        b.setLeft();
        this.assertEquals(mark.getSpouse().getFirstName(), benayCopy.getFirstName());
        b.bind();
        mark.getSpouse()
            .setFirstName("change");
        this.assertEquals("change", benayCopy.getFirstName());

        Person doe = new Person("Jane", "Doe", 99);
        mark.setSpouse(doe);
        this.assertEquals(mark.getSpouse().getFirstName(), benayCopy.getFirstName());
        mark.getSpouse()
            .setFirstName("change");
        this.assertEquals("change", benayCopy.getFirstName());

        b.unbind();

        // Test Array bindings with descriminators
        Person childCopy = new Person();
        Person[] children1 = {
                new Person("Delaney", "Krissel", 12), new Person("Jonny", "Doe", 1), new Person("Jenny", "Nobody", 3)
            };

        Person[] children2 = {
                new Person("Jonny", "Doe", 1), new Person("Delaney", "Krissel", 12), new Person("Timmy", "Nobody", 3)
            };

        mark.setChildren(children1);
        b = new Binding(childCopy, "firstName", mark, "children[2].firstName");
        b.setLeft();
        this.assertEquals("Jenny", childCopy.getFirstName());
        b.bind();
        children1[2].setFirstName("Becky");
        this.assertEquals("Becky", childCopy.getFirstName());
        mark.setChildren(children2);
        this.assertEquals("Timmy", childCopy.getFirstName());
        b.unbind();

        b = new Binding(childCopy, "firstName", mark, "children[lastName=Doe].firstName");
        b.setLeft();
        this.assertEquals("Jonny", childCopy.getFirstName());
        b.bind();
        b.unbind();

        //
        // Make sure cleanups worked.
        //
        Person pcsTest = new Person();
        PropertyChangeListener l = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent arg0) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };

        pcsTest.addPropertyChangeListener("firstName", l);
        pcsTest.removePropertyChangeListener("firstName", l);
        this.assertEquals(0, pcsTest.getPropertyChangeListeners().length);
        this.assertEquals(0, childCopy.getPropertyChangeListeners().length);
        this.assertEquals(0, mark.getPropertyChangeListeners().length);
        this.assertEquals(0, mark.getSpouse()
                                 .getPropertyChangeListeners().length);

        for (int i = 0; i < children1.length; i++) {
            this.assertEquals(0, children1[i].getPropertyChangeListeners().length);
        }

        for (int i = 0; i < children2.length; i++) {
            this.assertEquals(0, children2[i].getPropertyChangeListeners().length);
        }
    }

    public void testBindingBuilder() throws Exception {
        System.out.println("start testBindingBuilder");

        Person[] children1 = {
                new Person("Delaney", "Krissel", 12), new Person("Jonny", "Doe", 1), new Person("Jenny", "Nobody", 3)
            };

        Person[] children2 = {
                new Person("Jonny", "Doe", 1), new Person("Delaney", "Krissel", 12), new Person("Timmy", "Nobody", 3)
            };

        Binding b = BindingBuilder.bindLeft(children1[0])
                                  .property("firstName")
                                  .to(children1[1])
                                  .toProperty("firstName")
                                  .toBinding();

        b.setLeft();
        assertEquals(children1[0].getFirstName(), children1[1].getFirstName());
        b.bind();
        children1[0].setFirstName("Changed!");
        assertEquals("Changed!", children1[0].getFirstName());
        assertEquals("Changed!", children1[1].getFirstName());

        b.unbind();

        b = BindingBuilder.bindLeft(children1[0])
                          .property("firstName")
                          .to(children1[1])
                          .toProperty("firstName")
                          .toBinding();

        Person johnny = new Person("Jonny", "Doe", 1);
        Person delaney = new Person("Delaney", "Krissel", 12);

        BindingBuilder.appendChildToBinding(b)
                      .bind(johnny)
                      .property("firstName")
                      .to(delaney)
                      .toProperty("firstName")
                      .convertRightWith(
            new Converter<String, String>() {
                public String convert(String original) {
                    return original.toUpperCase();
                }
            })
                      .toBinding();
        b.setLeft();
        System.out.println(b.getChildren().get(0));
        System.out.println(children2[0]);
        System.out.println(children2[1]);
        assertEquals(delaney.getFirstName().toUpperCase(), johnny.getFirstName());
        b.bind();
        delaney.setFirstName("Changed!");
        assertEquals("CHANGED!", johnny.getFirstName());


    }
}
