/*
 * BindingTest.java
 * JUnit based test
 *
 * Created on November 28, 2007, 2:40 PM
 */
package com.totsp.gwittir.client.beans;

import com.google.gwt.junit.client.GWTTestCase;

import com.totsp.gwittir.client.testmodel.Person;

import com.totsp.gwittir.client.validator.IntegerValidator;
import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.ValidationFeedback;
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

        Binding b = BindingBuilder.bind(children1[0])
                                  .onLeftProperty("firstName")
                                  .toRight(children1[1])
                                  .onRightProperty("firstName")
                                  .toBinding();

        b.setLeft();
        assertEquals(children1[0].getFirstName(), children1[1].getFirstName());
        b.bind();
        children1[0].setFirstName("Changed!");
        assertEquals("Changed!", children1[0].getFirstName());
        assertEquals("Changed!", children1[1].getFirstName());

        b.unbind();

        b = BindingBuilder.bind(children1[0])
                          .onLeftProperty("firstName")
                          .toRight(children1[1])
                          .onRightProperty("firstName")
                          .toBinding();

        Person johnny = new Person("Jonny", "Doe", 1);
        Person delaney = new Person("Delaney", "Krissel", 12);

        BindingBuilder.appendChildToBinding(b)
                      .bindLeft(johnny)
                      .onLeftProperty("firstName")
                      .toRight(delaney)
                      .onRightProperty("firstName")
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

        ValidationFeedback vf = new ValidationFeedback() {

            public void handleException(Object source, ValidationException exception) {
                System.out.println(source);
                exception.printStackTrace();
            }

            public void resolve(Object source) {
                System.out.println("OK:" +source);
            }

        };

        b.unbind();
        BindingBuilder.appendChildToBinding(b)
                .bindLeft(johnny)
                .onLeftProperty("age")
                .toRight(delaney)
                .onRightProperty("lastName")
                .validateRightWith(IntegerValidator.INSTANCE)
                .notifiedWithRight(vf)
                .toBinding();
        b.bind();
        delaney.setLastName("12345");
        

    }
    
    
    public static void testMattRead() throws Exception {
       MockModel model1 = new MockModel();
       model1.setTestProp("model1");

       MockModel model2 = new MockModel();
       model2.setTestProp("model2");

       Binding b = new Binding(model1, "testProp", model2,
"testProp");
       b.bind();

       assertEquals("model1", model1.getTestProp());
       assertEquals("model2", model2.getTestProp());

       b.setRight();

       assertEquals("model1", model1.getTestProp());
       assertEquals("model1", model2.getTestProp());

       // WORKS UP TO THIS POINT

       model1.setTestProp("different");

       assertEquals("different", model1.getTestProp());
       assertEquals("different", model2.getTestProp()); // FAILS
    }

    private static class MockModel extends AbstractModelBean {

    private String testProp;

    public static final String PROP_TESTPROP = "testProp";

    /**
     * Get the value of testProp
     *
     * @return the value of testProp
     */
    public String getTestProp() {
        return this.testProp;
    }

    /**
     * Set the value of testProp
     *
     * @param newtestProp new value of testProp
     */
    public void setTestProp(String newtestProp) {
        String oldtestProp = testProp;
        this.testProp = newtestProp;
        this.changeSupport.firePropertyChange(PROP_TESTPROP, oldtestProp, newtestProp);
    }

    }
}
