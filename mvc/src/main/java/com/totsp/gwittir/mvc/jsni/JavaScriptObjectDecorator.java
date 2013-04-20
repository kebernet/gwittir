/*
 * JavaScriptObjectDecorator.java
 *
 * Created on November 9, 2007, 8:02 PM
 *

 */
package com.totsp.gwittir.mvc.jsni;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * This is a wrapper class around JavaScriptObject that allows for quick access to
 * data values on the JSO.
 */
public class JavaScriptObjectDecorator {
    private JavaScriptObject object;

    /**
     * Constructs a new instance.
     */
    public JavaScriptObjectDecorator() {
        this.setObject(newJSO());
    }

    /**
     * Constructs a new instance
     * @param object The JavaScriptObject to wrap.
     */
    public JavaScriptObjectDecorator(JavaScriptObject object) {
        this.setObject(object);
    }

    /**
     * Creates a new JavaScript Array object wrapped in a decorator.
     * @return 
     */
    public static native JavaScriptObject newArray() /*-{
    return new Array();
    }-*/;

    public void setBoolProperty(String name, boolean value) {
        this.setBoolProperty(this.getObject(), name, value);
    }

    public boolean getBoolProperty(String name) {
        return this.getBoolProperty(this.getObject(), name);
    }

    public void setBooleanProperty(String name, Boolean value) {
        if (value == null) {
            this.setNullObject(this.getObject(), name);
        } else {
            this.setBoolProperty(this.getObject(), name, value.booleanValue());
        }
    }

    public Boolean getBooleanProperty(String name) {
        return this.getBooleanProperty(this.getObject(), name);
    }

    public void setBytProperty(String name, byte value) {
        this.setBytProperty(this.getObject(), name, value);
    }

    public byte getBytProperty(String name) {
        return this.getBytProperty(this.getObject(), name);
    }

    public void setByteProperty(String name, Byte value) {
        if (value == null) {
            this.setNullObject(this.getObject(), name);
        } else {
            this.setBytProperty(this.getObject(), name, value.byteValue());
        }
    }

    public Byte getByteProperty(String name) {
        return this.getByteProperty(this.getObject(), name);
    }

    public void setCharProperty(String name, char value) {
        this.setCharProperty(this.getObject(), name, value);
    }

    public void setCharacterProperty(String name, Character value) {
        if (value == null) {
            this.setNullObject(this.getObject(), name);
        } else {
            this.setCharProperty(this.getObject(), name, value.charValue());
        }
    }

    public Character getCharacterProperty(String name) {
        return this.getCharacterProperty(this.getObject(), name);
    }

    public char getChrProperty(String name) {
        return this.getChrProperty(this.getObject(), name);
    }

    public void setDblProperty(String name, double value) {
        this.setDblProperty(this.getObject(), name, value);
    }

    public double getDblProperty(String name) {
        return this.getDblProperty(this.getObject(), name);
    }

    public void setDoubleProperty(String name, Double value) {
        if (value == null) {
            this.setNullObject(this.getObject(), name);
        } else {
            this.setDblProperty(this.getObject(), name, value.doubleValue());
        }
    }

    public Double getDoubleProperty(String name) {
        return this.getDoubleProperty(this.getObject(), name);
    }

    public void setFloatProperty(String name, Float value) {
        if (value == null) {
            this.setNullObject(this.getObject(), name);
        } else {
            this.setFltProperty(this.getObject(), name, value.floatValue());
        }
    }

    public Float getFloatProperty(String name) {
        return this.getFloatProperty(this.getObject(), name);
    }

    public void setFltProperty(String name, float value) {
        this.setFltProperty(this.getObject(), name, value);
    }

    public float getFltProperty(String name) {
        return this.getFltProperty(this.getObject(), name);
    }

    public void setIntProperty(String name, int value) {
        this.setIntProperty(this.getObject(), name, value);
    }

    public int getIntProperty(String name) {
        return this.getIntProperty(this.getObject(), name);
    }

    public void setIntegerProperty(String name, Integer value) {
        if (value == null) {
            this.setNullObject(this.getObject(), name);
        } else {
            this.setIntProperty(this.getObject(), name, value.intValue());
        }
    }

    public Integer getIntegerProperty(String name) {
        return this.getIntegerProperty(this.getObject(), name);
    }

    public void setJavaScriptObjectProperty(String name, JavaScriptObject value) {
        this.setJavaScriptObjectProperty(this.object, name, value);
    }

    public JavaScriptObjectDecorator getJavaScriptObjectProperty(String name) {
        JavaScriptObject jso = this.getJavaScriptObjectProperty(this.getObject(), name);

        return (jso == null) ? null : new JavaScriptObjectDecorator(jso);
    }

   
    
    public void setObject(JavaScriptObject object) {
        this.object = object;
    }

    /**
     * Returns the current JavaScriptObject this wraps.
     * @return the current JavaScriptObject this wraps.
     */
    public JavaScriptObject getObject() {
        return object;
    }

    public void setShortProperty(String name, Short value) {
        if (value == null) {
            this.setNullObject(this.getObject(), name);
        } else {
            this.setShtProperty(this.getObject(), name, value.shortValue());
        }
    }

    public Short getShortProperty(String name) {
        return this.getShortProperty(this.getObject(), name);
    }

    public void setShtProperty(String name, short value) {
        this.setShtProperty(this.getObject(), name, value);
    }

    public short getShtProperty(String name) {
        return this.getShtProperty(this.getObject(), name);
    }

    public void setStringProperty(String name, String value) {
        this.setStringProperty(this.getObject(), name, value);
    }

    public String getStringProperty(String name) {
        return this.getStringProperty(this.getObject(), name);
    }

    public String toString() {
        return toString(this.getObject());
    }

    public native String toString(JavaScriptObject object) /*-{
    return object;
    }-*/;

    private native void setBoolProperty(
        JavaScriptObject object, String name, boolean value) /*-{
    object[ name ] = value;
    }-*/;

    private native boolean getBoolProperty(JavaScriptObject object, String name) /*-{
    return object[ name ];
    }-*/;

    private native Boolean getBooleanProperty(
        JavaScriptObject object, String name) /*-{
    if( object[name] == null || object[name] == undefined ){
    return null;
    } else {
    return @com.totsp.gwittir.mvc.jsni.JavaScriptObjectDecorator::newBoolean(Z)( object[ name ] );
    }
    }-*/;

    private native void setBytProperty(JavaScriptObject object, String name, byte value) /*-{
    object[ name ] = value;
    }-*/;

    private native byte getBytProperty(JavaScriptObject object, String name) /*-{
    return object[ name ];
    }-*/;

    private native void setCharProperty(JavaScriptObject object, String name, char value) /*-{
    object[ name ] = value;
    }-*/;

    private native char getChrProperty(JavaScriptObject object, String name) /*-{
    return object[ name ];
    }-*/;

    private native void setDblProperty(
        JavaScriptObject object, String name, double value) /*-{
        object[ name ] = value;
    }-*/;

    private native double getDblProperty(JavaScriptObject object, String name) /*-{
    return object[ name ];
    }-*/;

    private native Float getFloatProperty(JavaScriptObject object, String name) /*-{
    if( object[name] == null || object[name] == undefined ){
    return null;
    } else {
    return @com.totsp.gwittir.mvc.jsni.JavaScriptObjectDecorator::newFloat(F)( object[ name ] );
    }
    }-*/;

    private native void setFltProperty(JavaScriptObject object, String name, float value) /*-{
    object[ name ] = value;
    }-*/;

    private native float getFltProperty(JavaScriptObject object, String name) /*-{
    return object[ name ];
    }-*/;

    private native void setIntProperty(JavaScriptObject object, String name, int value) /*-{
    object[ name ] = value;
    }-*/;

    private native int getIntProperty(JavaScriptObject object, String name) /*-{
    return object[ name ];
    }-*/;

    private native Integer getIntegerProperty(
        JavaScriptObject object, String name) /*-{
    if( object[name] == null || object[name] == undefined ){
    return null;
    } else {
    return @com.totsp.gwittir.mvc.jsni.JavaScriptObjectDecorator::newInteger(I)( object[ name ] );
    }
    }-*/;

    private native void setJavaScriptObjectProperty(
        JavaScriptObject object, String name, JavaScriptObject value) /*-{
    object[ name ] = value;
    }-*/;

    private native JavaScriptObject getJavaScriptObjectProperty(
        JavaScriptObject object, String name) /*-{
    if( object[ name ] == null || object[ name ] == undefined ){
    return null;
    } else {
    return object[ name ];
    }
    }-*/;

    

    
    private native void setNullObject(JavaScriptObject object, String name) /*-{
    object[name] = null;
    }-*/;

    private native void setShtProperty(JavaScriptObject object, String name, short value) /*-{
    object[ name ] = value;
    }-*/;

    private native short getShtProperty(JavaScriptObject object, String name) /*-{
    return object[ name ];
    }-*/;

    private static Double newDouble(double value) {
        return new Double(value);
    }

    private static Float newFloat(float value) {
        return new Float(value);
    }

    private static Integer newInteger(int value) {
        return new Integer(value);
    }

    private native JavaScriptObject newJSO() /*-{
    return new Object();
    }-*/;

    
    private native Byte getByteProperty(JavaScriptObject object, String name) /*-{
    if( object[name] == null || object[name] == undefined ){
    return null;
    } else {
    return @com.totsp.gwittir.mvc.jsni.JavaScriptObjectDecorator::newByte(B)( object[ name ] );
    }
    }-*/;

    private native Double getDoubleProperty(
        JavaScriptObject object, String name) /*-{
    if( object[name] == null || object[name] == undefined ){
    return null;
    } else {
    return @com.totsp.gwittir.mvc.jsni.JavaScriptObjectDecorator::newDouble(D)( object[ name ] );
    }
    }-*/;

    private static Byte newByte(byte value) {
        return new Byte(value);
    }

    private native Character getCharacterProperty(
        JavaScriptObject object, String name) /*-{
    if( object[name] == null || object[name] == undefined ){
    return null;
    } else {
    return @com.totsp.gwittir.mvc.jsni.JavaScriptObjectDecorator::newCharacter(C)( object[ name ] );
    }
    }-*/;

    private static Character newCharacter(char value) {
        return new Character(value);
    }

    private native Short getShortProperty(JavaScriptObject object, String name) /*-{
    if( object[name] == null || object[name] == undefined ){
        return null;
    } else {
        return @com.totsp.gwittir.mvc.jsni.JavaScriptObjectDecorator::newShort(S)( object[ name ] );
    }
    }-*/;

    private static Boolean newBoolean(boolean value) {
        return new Boolean(value);
    }

    private static Short newShort(short value) {
        return new Short(value);
    }

    private native void setStringProperty(
        JavaScriptObject object, String name, String value) /*-{
        object[ name ] = value;
    }-*/;

    private native String getStringProperty(JavaScriptObject object, String name) /*-{
    if( object[ name ] == null || object[ name ] == undefined ){
        return null;
    } else {
        return object[ name ];
    }
    }-*/;
    
    public Set<String> getProperties(){
    	HashSet<String> props = new HashSet<String>();
    	this.getProperties(this.object, props);
    	return props;
    }
    
    private native void getProperties(JavaScriptObject object, Set toFill)/*-{
    	for(x in object){
    		toFill.@java.util.Set::add(Ljava/lang/Object;)(x.toString());
    	}	
    }-*/;
}
