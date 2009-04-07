/*
 * BeanMappingTest.java
 * JUnit based test
 *
 *  Copyright (C) 2006  Robert "kebernet" Cooper <cooper@screaming-penguin.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.totsp.gwittir.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author cooper
 */
public class BeanMappingTest extends TestCase {
    
    public BeanMappingTest(String testName) {
        super(testName);
    }

    public void testConvert() throws Exception {
        System.out.println("convert");
        
        Properties mappings = new Properties();
        mappings.setProperty( "test.source.*", "test.destination.*");
        
        test.source.ABean source = new test.source.ABean();
        source.setStringProperty( "Foo");
        source.setIntProperty( -2 );
        String[] arr = { "Foo", "Bar", "Baz" };
        source.setArrayProperty(arr);
        Random r = new Random();
        test.source.BBean[][] bbeans = new test.source.BBean[3][5];
        for( int i=0; i < bbeans.length; i++ ){
            for( int j=0; j < bbeans[i].length; j++){
                bbeans[i][j] = new test.source.BBean();
                bbeans[i][j].setValue( ""+ r.nextLong() );
            }
        }
        
        HashMap<String,  test.source.BBean> map = new HashMap<String,  test.source.BBean>();
        map.put( "Foo", bbeans[0][0] );
        map.put( "Bar", bbeans[0][1] );
        source.setBeanMap( map );
        bbeans[0][0].setParent( source );
        
        source.setBeanArray( bbeans );
        test.destination.ABean result = (test.destination.ABean) 
            BeanMapping.convert(mappings, source);
        
        assertEquals( "stringProperty", source.getStringProperty(), result.stringProperty);
        assertEquals( "intProperty", source.getIntProperty(), result.intProperty);
        BeanMappingTest.assertEquals( "arrayProperty", source.getArrayProperty()[0], result.arrayProperty[0]);
        BeanMappingTest.assertEquals( "arrayProperty", source.getArrayProperty()[1], result.arrayProperty[1]);
        BeanMappingTest.assertEquals( "arrayProperty", source.getArrayProperty()[2], result.arrayProperty[2]);
        for( int i=0; i < bbeans.length; i++ ){
            for( int j=0; j < bbeans[i].length; j++){
                System.out.println(result.beanArray[i][j].getValue() );
                BeanMappingTest.assertEquals("arrayOfBeans", bbeans[i][j].getValue(),
                        result.beanArray[i][j].getValue());
            }
        }
        BeanMappingTest.assertEquals("beanMap", map.get("Foo").getValue(), result.beanMap.get("Foo").getValue() );
        BeanMappingTest.assertEquals("beanMap", map.get("Bar").getValue(), result.beanMap.get("Bar").getValue() );
        BeanMappingTest.assertTrue( "cyclic", result.beanMap.get("Foo").getParent() == result );
    }

    public void testList() throws Exception {
        test.source.BBean b = new test.source.BBean();
        List<test.source.ABean> abeans = new ArrayList<test.source.ABean>();
        test.source.ABean a = new test.source.ABean();
        a.setIntProperty(1);
        abeans.add(a);
        a = new test.source.ABean();
        a.setIntProperty(2);
        abeans.add(a);
        a = new test.source.ABean();
        a.setIntProperty(3);
        abeans.add(a);
        b.setAbeans(abeans);
        Properties mappings = new Properties();
        mappings.setProperty( "test.source.*", "test.destination.*");

        test.destination.BBean bb = (test.destination.BBean) BeanMapping.convert(mappings, b);
        int i=1;
        for(test.destination.ABean aa : bb.getAbeans() ){
            assertEquals(aa.intProperty, i++);
        }


    }

    public void testMixedType() throws Exception {
        test.source.ABean a = new test.source.ABean();
        a.charProperty = 'c';
        Properties mappings = new Properties();
        mappings.setProperty( "test.source.*", "test.destination.*");

        test.destination.ABean aa = (test.destination.ABean) BeanMapping.convert(mappings, a);
        assertEquals( a.charProperty, aa.getCharProperty());
    }

    public void testSet() throws Exception {
        test.source.ABean a = new test.source.ABean();
        HashSet<String> set = new HashSet<String>();
        set.add("the");
        set.add("quick");
        set.add("brown");
        set.add("fox");
        Set<String> control = new HashSet<String>(set);
        a.stringSet = set;
        Properties mappings = new Properties();
        mappings.setProperty( "test.source.*", "test.destination.*");

        test.destination.ABean aa = (test.destination.ABean) BeanMapping.convert(mappings, a);
        assertEquals(control, aa.stringSet);


    }
}
