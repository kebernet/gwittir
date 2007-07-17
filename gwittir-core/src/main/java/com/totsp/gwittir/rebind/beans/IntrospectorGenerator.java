/*
 * IntrospectorGenerator.java
 *
 * Created on July 15, 2007, 2:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.rebind.beans;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import java.io.PrintWriter;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author cooper
 */
public class IntrospectorGenerator extends Generator {
    String implementationName = com.totsp.gwittir.client.beans.Introspector.class
        .getSimpleName() + "_Impl";
    String packageName = com.totsp.gwittir.client.beans.Introspector.class.getCanonicalName()
                                                                          .substring(0,
            com.totsp.gwittir.client.beans.Introspector.class.getCanonicalName()
                                                             .lastIndexOf("."));

    /** Creates a new instance of IntrospectorGenerator */
    public IntrospectorGenerator() {
    }

    private boolean box(Class type, SourceWriter writer) {
        if(type == Integer.TYPE) {
            writer.print("new Integer( ");

            return true;
        }

        if(type == Long.TYPE) {
            writer.print("new Long( ");

            return true;
        }

        if(type == Float.TYPE) {
            writer.print("new Float( ");

            return true;
        }

        if(type == Double.TYPE) {
            writer.print("new Double( ");

            return true;
        }

        if(type == Character.TYPE) {
            writer.print("new Character( ");

            return true;
        }

        if(type == Byte.TYPE) {
            writer.print("new Byte( ");

            return true;
        }

        if(type == Boolean.TYPE) {
            writer.print("new Boolean( ");

            return true;
        }

        return false;
    }

    private int find(MethodHashHolder[] search, MethodHashHolder match) {
        for(int i = 0; i < search.length; i++) {
            if(search[i].equals(match)) {
                return i;
            }
        }

        System.out.println("NO MATCH FOR: " + match.toString());
        throw new RuntimeException(match.toString());
    }

    private MethodHashHolder[] findMethods(TreeLogger logger,
        List introspectables) {
        HashSet methods = new HashSet();

        for(Iterator it = introspectables.iterator(); it.hasNext();) {
            JClassType type = (JClassType) it.next();
            logger.branch(logger.INFO,
                "Method Scanning: " + type.getQualifiedSourceName(), null);

            try {
                BeanInfo info = java.beans.Introspector.getBeanInfo(Class
                        .forName(type.getQualifiedSourceName()));

                if((info.getPropertyDescriptors() == null)
                        || (info.getPropertyDescriptors().length == 1)) {
                    continue;
                }

                PropertyDescriptor[] pds = info.getPropertyDescriptors();

                for(int i = 0; i < pds.length; i++) {
                    if(pds[i].getName().equals("class")) {
                        continue;
                    }

                    if(pds[i].getReadMethod() != null) {
                        methods.add(new MethodHashHolder(pds[i].getReadMethod()));
                    }

                    if(pds[i].getWriteMethod() != null) {
                        methods.add(new MethodHashHolder(
                                pds[i].getWriteMethod()));
                    }
                }
            } catch(ClassNotFoundException nfe) {
                logger.log(logger.ERROR,
                    "Unable for find class. Is bytecode on GWTComplie classpath?",
                    nfe);
            } catch(IntrospectionException ine) {
                logger.log(logger.ERROR,
                    "Unable to introspect class. Is class a bean?", ine);
            }
        }

        MethodHashHolder[] results = new MethodHashHolder[methods.size()];
        Iterator it = methods.iterator();

        for(int i = 0; it.hasNext(); i++) {
            results[i] = (MethodHashHolder) it.next();
        }

        return results;
    }

    public String generate(TreeLogger logger, GeneratorContext context,
        String typeName) throws UnableToCompleteException {
        ClassSourceFileComposerFactory cfcf = new ClassSourceFileComposerFactory(packageName,
                implementationName);
        cfcf.addImplementedInterface(typeName);
        cfcf.addImport("java.util.HashMap");
        cfcf.addImport(com.totsp.gwittir.client.beans.Method.class
            .getCanonicalName());
        cfcf.addImport(com.totsp.gwittir.client.beans.Property.class
            .getCanonicalName());
        cfcf.addImport(com.totsp.gwittir.client.beans.BeanDescriptor.class
            .getCanonicalName());

        List introspectables = this.getIntrospectableTypes(logger,
                context.getTypeOracle());
        PrintWriter printWriter = context.tryCreate(logger, packageName,
                implementationName);

        if(printWriter == null) {
            return packageName + "." + implementationName;
        }

        SourceWriter writer = cfcf.createSourceWriter(context, printWriter);
        writer.println("public static final HashMap LOOKUPS = new HashMap();");

        MethodHashHolder[] methods = this.findMethods(logger, introspectables);
        this.writeMethodsArray(logger, methods, writer);
        this.writeIntrospectables(logger, introspectables, methods, writer);
        this.writeResolver(logger, introspectables, writer);
        writer.println("public BeanDescriptor getDescriptor( Class clazz ){ ");
        writer.indent();
        writer.println("return (BeanDescriptor) LOOKUPS.get( clazz ); ");
        writer.outdent();
        writer.println("}");
        writer.println("public BeanDescriptor getDescriptor( Object object ){ ");
        writer.indent();
        writer.println(
            "return (BeanDescriptor) LOOKUPS.get( this.resolveClass( object) ); ");
        writer.outdent();
        writer.println("}");
        writer.outdent();
        writer.println("}");

        context.commit(logger, printWriter);

        return packageName + "." + implementationName;
    }

    private List getIntrospectableTypes(TreeLogger logger, TypeOracle oracle) {
        ArrayList results = new ArrayList();

        try {
            JClassType[] types = oracle.getTypes();
            JClassType introspectable = oracle.getType(com.totsp.gwittir.client.beans.Introspectable.class
                    .getCanonicalName());

            for(int i = 0; i < types.length; i++) {
                logger.log(logger.INFO,
                    types[i] + " is assignable to " + introspectable + " "
                    + types[i].isAssignableTo(introspectable)
                    + " isInterface = " + types[i].isInterface(), null);

                if(types[i].isAssignableTo(introspectable)
                        && (types[i].isInterface() == null)) {
                    results.add(types[i]);
                }
            }
        } catch(Exception e) {
            logger.log(logger.ERROR, "Unable to finad Introspectable types.", e);
        }

        return results;
    }

    private boolean unbox(Class type, String reference, SourceWriter writer) {
        if(type == Integer.TYPE) {
            writer.print("((Integer) " + reference + ").intValue()");

            return true;
        }

        if(type == Long.TYPE) {
            writer.print("((Long) " + reference + ").longValue()");

            return true;
        }

        if(type == Float.TYPE) {
            writer.print("((Float) " + reference + ").floatValue()");

            return true;
        }

        if(type == Double.TYPE) {
            writer.print("((Double) " + reference + ").doubleValue()");

            return true;
        }

        if(type == Character.TYPE) {
            writer.print("((Character) " + reference + ").charValue()");

            return true;
        }

        if(type == Byte.TYPE) {
            writer.print("((Byte) " + reference + ").byteValue()");

            return true;
        }

        if(type == Boolean.TYPE) {
            writer.print("((Boolean) " + reference + ").booleanValue()");

            return true;
        }

        writer.print("(" + type.getCanonicalName() + ") " + reference);

        return false;
    }

    private void writeBeanDescriptor(TreeLogger logger, BeanInfo info,
        MethodHashHolder[] methods, SourceWriter writer) {
        writer.println("new BeanDescriptor() { ");
        writer.indent();
        writer.println("private HashMap lookup;");
        writer.println("private Property[] properties;");
        writer.println("public Property[] getProperties(){");
        writer.indent();

        {
            writer.println("if( this.properties != null ) ");
            writer.indentln("return this.properties;");
            writer.println("this.properties = new Property["
                + (info.getPropertyDescriptors().length - 1) + "];");

            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            logger.log(logger.INFO, "" + (pds == null), null);

            boolean foundClass = false;

            if(pds != null) {
                for(int i = 0; i < pds.length; i++) {
                    if(pds[i].getName().equals("class")) {
                        foundClass = true;

                        continue;
                    }

                    writer.println("{");
                    writer.indent();

                    writer.print("Method readMethod = ");

                    if(pds[i].getReadMethod() == null) {
                        writer.println("null;");
                    } else {
                        writer.println("METHODS["
                            + this.find(methods,
                                new MethodHashHolder(pds[i].getReadMethod()))
                            + "];");
                    }

                    writer.print("Method writeMethod = ");

                    if(pds[i].getWriteMethod() == null) {
                        writer.println("null;");
                    } else {
                        writer.println("METHODS["
                            + this.find(methods,
                                new MethodHashHolder(pds[i].getWriteMethod()))
                            + "];");
                    }

                    logger.log(logger.INFO,
                        pds[i].getName() + " " + pds[i].getPropertyType(), null);
                    writer.println("this.properties["
                        + (foundClass ? (i - 1) : i) + "] = new Property( \""
                        + pds[i].getName() + "\", "
                        + ((pds[i].getPropertyType() != null)
                        ? pds[i].getPropertyType().getCanonicalName() : "Object")
                        + ".class,  readMethod, writeMethod );");
                    writer.outdent();
                    writer.println("}");
                }
            }

            writer.println("return this.properties;");
        }

        writer.outdent();
        writer.println("} //end getProperties()");
        writer.println("public Property getProperty( String name ) {");
        writer.indent();
        writer.println("Property p = null;");
        writer.println("if( this.lookup != null ) {");
        writer.indentln("p = (Property) lookup.get(name); ");
        writer.println("} else {");
        writer.indent();
        writer.println("this.lookup = new HashMap();");
        writer.println("Property[] props = this.getProperties(); ");
        writer.println("for( int i=0; i < props.length; i++ ) {");
        writer.indent();
        writer.println("this.lookup.put( props[i].getName(), props[i] );");
        writer.outdent();
        writer.println("}");
        writer.println("p = (Property) this.lookup.get(name);");
        writer.outdent();
        writer.println("}");
        writer.println(
            "if( p == null ) throw new RuntimeException(\"Couldn't find property \"+name );");
        writer.println("else return p;");
        writer.outdent();
        writer.println("}");

        writer.outdent();
        writer.print("}");
    }

    private void writeIntrospectables(TreeLogger logger, List introspectables,
        MethodHashHolder[] methods, SourceWriter writer) {
        writer.println("static { ");
        writer.indent();

        for(Iterator it = introspectables.iterator(); it.hasNext();) {
            JClassType type = (JClassType) it.next();
            logger.branch(logger.INFO,
                "Introspecting: " + type.getQualifiedSourceName(), null);

            try {
                BeanInfo info = java.beans.Introspector.getBeanInfo(Class
                        .forName(type.getQualifiedSourceName()));

                if((info.getPropertyDescriptors() == null)
                        || (info.getPropertyDescriptors().length == 1)) {
                    continue;
                }

                writer.print("LOOKUPS.put( " + type.getQualifiedSourceName()
                    + ".class, ");
                this.writeBeanDescriptor(logger, info, methods, writer);
                writer.println(" );");
            } catch(ClassNotFoundException nfe) {
                logger.log(logger.ERROR,
                    "Unable for find class. Is bytecode on GWTComplie classpath?",
                    nfe);
            } catch(IntrospectionException ine) {
                logger.log(logger.ERROR,
                    "Unable to introspect class. Is class a bean?", ine);
            }
        }

        writer.outdent();
        writer.println("}");
    }

    private void writeMethod(TreeLogger logger, Method method,
        SourceWriter writer) {
        writer.println("new Method(){ ");
        writer.indent();
        writer.println("public String getName() {");
        writer.indentln("return \"" + method.getName() + "\";");
        writer.println(" }");
        writer.println(
            "public Object invoke( Object target, Object[] args ) throws Exception {");
        writer.indent();
        writer.println(method.getDeclaringClass().getCanonicalName()
            + " casted =");
        writer.println("(" + method.getDeclaringClass().getCanonicalName()
            + ") target;");
        logger.log(logger.DEBUG,
            "Method: " + method.getName() + " "
            + method.getReturnType().getCanonicalName(), null);

        if(!method.getReturnType().getCanonicalName().equals("void")) {
            writer.print("return ");
        }

        boolean boxed = this.box(method.getReturnType(), writer);
        writer.print("casted." + method.getName() + "(");

        if(method.getParameterTypes() != null) {
            for(int j = 0; j < method.getParameterTypes().length; j++) {
                this.unbox(method.getParameterTypes()[j], "args[" + j + "]",
                    writer);

                if(j != (method.getParameterTypes().length - 1)) {
                    writer.print(", ");
                }
            }
        }

        writer.print(")");

        if(boxed) {
            writer.print(")");
        }

        writer.println(";");

        if(method.getReturnType().getCanonicalName().equals("void")) {
            writer.println("return null;");
        }

        writer.outdent();
        writer.println("}");
        writer.outdent();
        writer.println("};");
    }

    private void writeMethodsArray(TreeLogger logger,
        MethodHashHolder[] methods, SourceWriter writer) {
        writer.println("static final Method[] METHODS = new Method["
            + methods.length + "];");
        writer.println("static{");
        writer.indent();

        for(int i = 0; i < methods.length; i++) {
            writer.print("METHODS[" + i + "] =");
            writeMethod(logger, methods[i].m, writer);
        }

        writer.outdent();
        writer.println("}//end methods");
    }

    private void writeResolver(TreeLogger logger, List introspectables,
        SourceWriter writer) {
        writer.println("public Class resolveClass(Object object){");
        writer.indent();

        for(Iterator it = introspectables.iterator(); it.hasNext();) {
            JClassType type = (JClassType) it.next();
            writer.println("if( object instanceof "
                + type.getQualifiedSourceName() + " ) return "
                + type.getQualifiedSourceName() + ".class;");
        }

        writer.println(
            "throw new RuntimeException( \"Object \"+object+\"could not be resolved.\" );");
        writer.outdent();
        writer.println("}");
    }

    private static class MethodHashHolder {
        Method m;
        String s1;

        MethodHashHolder(Method m) {
            this.m = m;
            s1 = m.getDeclaringClass().getCanonicalName() + m.getName()
                + m.getReturnType().getCanonicalName();
        }

        public boolean equals(Object o) {
            return (o != null) && (o.hashCode() == this.hashCode());
        }

        public int hashCode() {
            return s1.hashCode();
        }

        public String toString() {
            return s1;
        }
    }
}
