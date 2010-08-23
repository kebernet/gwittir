/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.util;

import com.totsp.gwittir.client.beans.Converter;

import java.util.HashMap;


/**
 *
 * @author robert.cooper
 */
public class StringConverter {
    private static final Converter<String, String> PASSTHROUGH = new Converter<String, String>(){

        public String convert(String original) {
            return original;
        }

    };
    private HashMap<Class, Converter<String, ?>> fromStringLookups = new HashMap<Class, Converter<String, ?>>();
    private HashMap<Class, Converter<?, String>> toStringLookups = new HashMap<Class, Converter<?, String>>();

    public StringConverter(boolean defaults) {
        if (defaults) {
            fromStringLookups.put(Integer.class, Converter.STRING_TO_INTEGER_CONVERTER);
            fromStringLookups.put(int.class, Converter.STRING_TO_INTEGER_CONVERTER);
            fromStringLookups.put(Long.class, Converter.STRING_TO_LONG_CONVERTER);
            fromStringLookups.put(long.class, Converter.STRING_TO_LONG_CONVERTER);
            fromStringLookups.put(Double.class, Converter.STRING_TO_DOUBLE_CONVERTER);
            fromStringLookups.put(double.class, Converter.STRING_TO_DOUBLE_CONVERTER);
            fromStringLookups.put(Boolean.class, Converter.STRING_TO_BOOLEAN_CONVERTER);
            fromStringLookups.put(boolean.class, Converter.STRING_TO_BOOLEAN_CONVERTER);

            toStringLookups.put(Integer.class, Converter.INTEGER_TO_STRING_CONVERTER);
            toStringLookups.put(int.class, Converter.INTEGER_TO_STRING_CONVERTER);
            toStringLookups.put(Long.class, Converter.LONG_TO_STRING_CONVERTER);
            toStringLookups.put(long.class, Converter.LONG_TO_STRING_CONVERTER);
            toStringLookups.put(Double.class, Converter.DOUBLE_TO_STRING_CONVERTER);
            toStringLookups.put(double.class, Converter.DOUBLE_TO_STRING_CONVERTER);
            toStringLookups.put(Boolean.class, Converter.BOOLEAN_TO_STRING_CONVERTER);
            toStringLookups.put(boolean.class, Converter.BOOLEAN_TO_STRING_CONVERTER);
        }
    }

    public StringConverter() {
        this(true);
    }

    public <T> void register(Class<T> clazz, Converter<String, T> from, Converter<T, String> to) {
        fromStringLookups.put(clazz, from);
        toStringLookups.put(clazz, to);
    }

    public <T> Converter<String, T> from(Class<T> clazz){
        return (Converter<String, T>) this.fromStringLookups.get(clazz);
    }

    public <T> Converter<T, String> to(Class<T> clazz){
        return (Converter<T, String>) this.toStringLookups.get(clazz);
    }
}
