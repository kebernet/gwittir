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
        return "com.totsp.gwittir.GwittirTest";
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public void testBasicDotted() throws Exception {
        Person mark = new Person("Mark", "Lanford", 31);
        mark.setSpouse(new Person("Benay", "Krissel", 37));
        Person benayCopy = new Person();
        Binding b = new Binding(benayCopy, "firstName", mark, "spouse.firstName");
        b.setLeft();
        this.assertEquals(mark.getSpouse().getFirstName(), benayCopy.getFirstName());
        b.bind();
        mark.getSpouse().setFirstName("change");
        this.assertEquals("change", benayCopy.getFirstName());

        Person doe = new Person("Jane", "Doe", 99);
        mark.setSpouse(doe);
        this.assertEquals(mark.getSpouse().getFirstName(), benayCopy.getFirstName());
        mark.getSpouse().setFirstName("change");
        this.assertEquals("change", benayCopy.getFirstName());

        b.unbind();

        // Test Array bindings with descriminators
        Person childCopy = new Person();
        Person[] children1 = {new Person("Delaney", "Krissel", 12),
            new Person("Jonny", "Doe", 1),
            new Person("Jenny", "Nobody", 3)
        };

        Person[] children2 = {new Person("Jonny", "Doe", 1),
            new Person("Delaney", "Krissel", 12),
            new Person("Timmy", "Nobody", 3)
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

        Person pcsTest = new Person();
        PropertyChangeListener l = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent arg0) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
        pcsTest.addPropertyChangeListener("firstName", l);
        pcsTest.removePropertyChangeListener("firstName", l);
        this.assertEquals(0, pcsTest.getPropertyChangeListeners().length );
        this.assertEquals(0, childCopy.getPropertyChangeListeners().length);
        this.assertEquals(0, mark.getPropertyChangeListeners().length);
        this.assertEquals(0, mark.getSpouse().getPropertyChangeListeners().length);

        for (int i = 0; i < children1.length; i++) {
            this.assertEquals(0, children1[i].getPropertyChangeListeners().length);
        }
        for (int i = 0; i < children2.length; i++) {
            this.assertEquals(0, children2[i].getPropertyChangeListeners().length);
        }

    }
}
