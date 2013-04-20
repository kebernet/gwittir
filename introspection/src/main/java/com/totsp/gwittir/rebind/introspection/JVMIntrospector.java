/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.rebind.introspection;

import com.totsp.gwittir.introspection.BeanDescriptor;
import com.totsp.gwittir.introspection.Introspector;
import com.totsp.gwittir.introspection.Method;
import com.totsp.gwittir.introspection.Property;
import com.totsp.gwittir.introspection.SelfDescribed;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.HashMap;

/**
 *
 * @author kebernet
 */
public class JVMIntrospector implements Introspector {

    private HashMap<Class, BeanDescriptor> cache = new HashMap<Class, BeanDescriptor>();

    public BeanDescriptor getDescriptor(Object object) {
        if(cache.containsKey(object.getClass())){
            return cache.get(object.getClass());
        }
        BeanDescriptor result = null;
        if(object instanceof SelfDescribed){
            System.out.println("SelfDescribed\t"+ object.getClass().getName());
            result =  ((SelfDescribed) object).__descriptor();
        } else {
            System.out.println("Reflection\t"+ object.getClass().getName());
            result = new ReflectionBeanDescriptor(object.getClass());
            cache.put(object.getClass(), result);
        }
        
        return result;
    }

    public Class resolveClass(Object instance) {
        return instance.getClass();
    }

    private static class ReflectionBeanDescriptor implements BeanDescriptor {

        BeanInfo info;
        Property[] props;
        String className;
        ReflectionBeanDescriptor(Class clazz){
            try{
                className = clazz.getName();
                info = java.beans.Introspector.getBeanInfo(clazz);
                props = new Property[info.getPropertyDescriptors().length-1];
                int index =0;
                for(PropertyDescriptor d: info.getPropertyDescriptors()){
                    if(d.getName().equals("class")){
                        continue;
                    }
                    props[index] = new Property(d.getName(), d.getPropertyType(), 
                            d.getReadMethod() == null ? null : new MethodWrapper(d.getReadMethod()),
                            d.getWriteMethod() == null ? null : new MethodWrapper(d.getWriteMethod()));
                   // System.out.println(clazz+" mapped property: "+props[index]);
                    index++;
                }
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }

        public Property[] getProperties() {
            return this.props;
        }

        public Property getProperty(String name) {
            for(Property p: props){
                if(p.getName().equals(name)){
                    return p;
                }
            }
            throw new RuntimeException("Unknown property: "+name+" on class "+className);
        }



    }

    private static class MethodWrapper implements Method {
        private final java.lang.reflect.Method inner;

        MethodWrapper(java.lang.reflect.Method inner){
            assert inner != null;
            this.inner = inner;
        }

        //@Override
        //For JDK1.5 compatibility, don't override methods inherited from an interface
        public String getName() {
            return ((java.lang.reflect.Method)inner).toString();
        }

        //@Override
        public Object invoke(Object target, Object[] args) throws Exception {
              return inner.invoke(target, args);
        }

        @Override
        public String toString(){
            return inner.toString();
        }

    }

}
