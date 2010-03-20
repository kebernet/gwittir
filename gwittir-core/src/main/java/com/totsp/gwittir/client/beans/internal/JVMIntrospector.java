/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.beans.internal;

import com.totsp.gwittir.client.beans.BeanDescriptor;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Method;
import com.totsp.gwittir.client.beans.Property;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;

/**
 *
 * @author kebernet
 */
public class JVMIntrospector implements Introspector {

    public BeanDescriptor getDescriptor(Object object) {
        return new ReflectionBeanDescriptor(object.getClass());
    }

    public Class resolveClass(Object instance) {
        return instance.getClass();
    }

    private static class ReflectionBeanDescriptor implements BeanDescriptor {

        BeanInfo info;
        Property[] props;
        ReflectionBeanDescriptor(Class clazz){
            try{
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
            throw new RuntimeException();
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
