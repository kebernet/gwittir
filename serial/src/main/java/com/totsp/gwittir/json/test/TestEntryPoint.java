package com.totsp.gwittir.json.test;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Created with IntelliJ IDEA.
 * User: Robert
 * Date: 2/3/14
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {

        try {
        TestParent test = new TestParent();
        test.setParentProp("BAZ");
        TestParentCodec codec = GWT.create(TestParentCodec.class);
//        String testString = codec.serialize(test);
//
//        System.out.println(testString);
//        assertEquals(test, codec.deserialize(testString));

        TestSubclass subclass = new TestSubclass();
        subclass.setChildProperty("Foo");
        String subclassString = codec.serialize(subclass);
        System.out.println(subclassString);


        SubSubclass subsub = new SubSubclass();
        subsub.setSubsub(55);

        String subsubString = codec.serialize(subsub);
        System.out.println("------------------------------");
        System.out.println("SubSubclass: ");
        System.out.println(subsubString);

        System.out.println("------------------------------");

        SubSubclass subsub2 = (SubSubclass) codec.deserialize(subsubString);
        //assertEquals(subsub, subsub2);
        } catch(Exception e){
            GWT.log("Exception ", e);
        }
    }
}
