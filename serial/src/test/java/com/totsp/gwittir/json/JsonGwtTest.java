/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.json;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.totsp.gwittir.json.test.OtherSubSub;
import com.totsp.gwittir.json.test.SubSubclass;
import com.totsp.gwittir.json.test.TestParent;
import com.totsp.gwittir.json.test.TestParentCodec;
import com.totsp.gwittir.json.test.TestSubclass;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author kebernet
 */
public class JsonGwtTest extends GWTTestCase {
    public String getModuleName() {
        return "com.totsp.gwittir.JSON";
    }


    public void testBasic() throws Exception {
        try{
        String json = " { string:'a \\n \\\" $ & with \\u0945 string', integer: 5} ";
        TestBeanCodec codec = GWT.create(TestBeanCodec.class);
        TestBean b = codec.deserialize(json);

        TestChildBean tcb = new TestChildBean();
        tcb.setBooleanProperty(true);

        HashSet<TestChildBean> hs = new HashSet<TestChildBean>();
        hs.add(tcb);
        b.setChildSet(hs);
        b.setChild(tcb);

        ArrayList<Integer> intList = new ArrayList<Integer>();
        for(int i=0; i< 20;i++ ){
            intList.add(i);
        }
        b.setIntegerList(intList);

        String ser = codec.serialize(b);

        TestBean b2 = codec.deserialize(ser);
        System.out.println( ser );
        System.out.println( codec.serialize(b2));
        assertEquals( b, b2 );

        } catch(Throwable e){
            while(e.getCause() != null ){
                e = e.getCause();
            }
            e.printStackTrace();
        }
    }

    public void testSubclass() throws Exception {
        TestParent test = new TestParent();
        test.setParentProp("BAZ");
        TestParentCodec codec = GWT.create(TestParentCodec.class);
        String testString = codec.serialize(test);

        System.out.println(testString);
        assertEquals(test, codec.deserialize(testString));

        TestSubclass subclass = new TestSubclass();
        subclass.setChildProperty("Foo");
        String subclassString = codec.serialize(subclass);
        System.out.println(subclassString);

        assertEquals(subclass, codec.deserialize(subclassString));

        SubSubclass subsub = new SubSubclass();
        subsub.setSubsub(55);

        String subsubString = codec.serialize(subsub);
        System.out.println("------------------------------");
        System.out.println("SubSubclass: ");
        System.out.println(subsubString);

        System.out.println("------------------------------");

        SubSubclass subsub2 = (SubSubclass) codec.deserialize(subsubString);
        assertEquals(subsub, subsub2);


        OtherSubSub other = new OtherSubSub();
        String otherString = codec.serialize(other);
        System.out.println(otherString);
        assertEquals(other, codec.deserialize(otherString));

    }

}
