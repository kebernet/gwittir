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
    private HashMap<Class, Converter<String, ?>> fromStringLookups = new HashMap<Class, Converter<String, ?>>();
    private HashMap<Class, Converter<?, String>> toStringLookups = new HashMap<Class, Converter<?, String>>();

    public StringConverter(boolean defaults) {
        if (defaults) {
            fromStringLookups.put(Integer.class, Converter.STRING_TO_INTEGER_CONVERTER);
            fromStringLookups.put(Long.class, Converter.STRING_TO_LONG_CONVERTER);
            fromStringLookups.put(Double.class, Converter.STRING_TO_DOUBLE_CONVERTER);

            toStringLookups.put(Integer.class, Converter.INTEGER_TO_STRING_CONVERTER);
            toStringLookups.put(Long.class, Converter.LONG_TO_STRING_CONVERTER);
            toStringLookups.put(Double.class, Converter.DOUBLE_TO_STRING_CONVERTER);
        }
    }

    public StringConverter() {
        this(true);
    }

    public <T> void register(Class<T> clazz, Converter<String, T> from, Converter<T, String> to) {
        fromStringLookups.put(clazz, from);
        toStringLookups.put(clazz, to);
    }
}
