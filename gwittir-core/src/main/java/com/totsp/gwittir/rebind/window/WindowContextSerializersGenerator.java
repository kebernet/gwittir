/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.rebind.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import com.totsp.gwittir.client.util.WindowContextItem;

import com.totsp.gwittir.client.util.impl.WindowContextSerializers;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 *
 * @author kebernet
 */
public class WindowContextSerializersGenerator extends Generator {
    
    @Override
    public String generate(TreeLogger logger, GeneratorContext context,
        String typeName) throws UnableToCompleteException {
        List<JClassType> contextItems = null;

        

        String packageName = "com.totsp.gwittir.client.util.impl";
        String className = "WindowContextSerializers_Impl";
        ClassSourceFileComposerFactory mcf = new ClassSourceFileComposerFactory(packageName,
                className);
        mcf.addImport(Map.class.getCanonicalName());
        mcf.addImport(HashMap.class.getCanonicalName());
        mcf.addImport(GWT.class.getCanonicalName());
        mcf.addImport(AsyncCallback.class.getCanonicalName());
        mcf.addImport(SerializationStreamFactory.class.getCanonicalName());
        mcf.addImplementedInterface(WindowContextSerializers.class.getCanonicalName());
        PrintWriter pw = context.tryCreate(logger, packageName, className);
        String finalName =new StringBuilder(packageName).append(".").append(className)
                                             .toString();
        if (pw == null) {
            return finalName;
        }

        SourceWriter sw = mcf.createSourceWriter(context, pw);

        try {
            contextItems = this.getWindowContextItemTypes(context);

            for (JClassType type : contextItems) {
                this.generateServices(logger, context, sw,
                    type.getQualifiedSourceName());
            }
        } catch (NotFoundException ex) {
            logger.log(TreeLogger.ERROR, "Unexpected not found exception", ex);
            throw new UnableToCompleteException();
        }

        sw.println( "public WindowContextSerializers_Impl(){super();}");
        sw.println(
            "private final static Map<Class, SerializationStreamFactory> FACTORIES = new HashMap<Class, SerializationStreamFactory>();");
        sw.println(
            "public SerializationStreamFactory getFactory(Object object){");
        sw.indent();

        for (JClassType type : contextItems) {
            sw.print("if( object instanceof ");
            sw.print(type.getQualifiedSourceName());
            sw.print("){");
            sw.indent();
            sw.print("Class clazz = ");
            sw.print(type.getQualifiedSourceName());
            sw.println(".class;");
            sw.println(
                "SerializationStreamFactory factory = FACTORIES.get(clazz);");
            sw.println("if(factory == null) {");
            sw.indent();
            sw.print("factory = getFactory(clazz);");
            sw.println("}");
            sw.println("return factory;");
            sw.outdent();
            sw.println("}");
        }

        sw.println("return null;");
        sw.outdent();
        sw.println("}");

        sw.println("public SerializationStreamFactory getFactory(Class clazz){");
        sw.indent();

        for (JClassType type : contextItems) {
            String serviceClass = type.getQualifiedSourceName().replaceAll("\\.", "_") + "_RemoteService";
            sw.print("if( clazz == ");
            sw.print(type.getQualifiedSourceName());
            sw.print(".class){");
            sw.indent();
            sw.println(
                "SerializationStreamFactory factory = FACTORIES.get(clazz);");
            sw.println("if(factory == null) {");
            sw.indent();
            sw.print("factory = (SerializationStreamFactory) GWT.create( ");
            sw.print(serviceClass);
            sw.println(".class);");
            sw.println("FACTORIES.put(clazz, factory);");
            sw.outdent();
            sw.println("}");
            sw.println("return factory;");
            sw.outdent();
            sw.println("}");
        }

        sw.println("return null;");
        sw.outdent();
        sw.println("}");

        sw.outdent();
        sw.println("}");
        context.commit(logger, pw);
        
        logger.log( TreeLogger.WARN, "Generated "+finalName);
        return finalName;
    }

    private List<JClassType> getWindowContextItemTypes(GeneratorContext context)
        throws NotFoundException {
        HashSet<JClassType> types = new HashSet<JClassType>();
        JClassType wciType = context.getTypeOracle()
                                    .getType(WindowContextItem.class.getCanonicalName());

        for (JClassType type : context.getTypeOracle().getTypes()) {
            if (type.isAssignableTo(wciType) && type.isInterface() == null) {
                types.add(type);
            }
        }

        ArrayList<JClassType> results = new ArrayList<JClassType>(types);
        boolean swap = true;

        while (swap) {
            swap = false;

            for (int i = results.size() - 1; i >= 0; i--) {
                JClassType type = results.get(i);

                for (int j = i - 1; j >= 0; j--) {
                    JClassType check = (JClassType) results.get(j);

                    if (type.isAssignableTo(check)) {
                        results.set(i, check);
                        results.set(j, type);

                        if (type.toString().equals(check.toString())) {
                            results.remove(i);
                        }

                        swap = true;
                    }
                }
            }
        }

        return results;
    }


    public String generateServices(TreeLogger logger, GeneratorContext context, SourceWriter sw, String typeName) throws UnableToCompleteException {
        try {
            JClassType type = context.getTypeOracle().getType(typeName);
            this.generateRemoteService(logger, sw, type);
            this.generateAsyncService(logger, sw, type);
        } catch (NotFoundException ex) {
            logger.log(TreeLogger.Type.WARN, typeName+" not found.");
            return typeName;
        }

        return null;
    }

    private void generateRemoteService(TreeLogger logger, SourceWriter sw, JClassType type){
        String className = type.getQualifiedSourceName().replaceAll("\\.", "_") + "_RemoteService";
        
        sw.println("public static interface "+className+" extends com.google.gwt.user.client.rpc.RemoteService {");
        sw.print( "public ");
        sw.print( type.getQualifiedSourceName() );
        sw.print( " get(");
        sw.print( type.getQualifiedSourceName() );
        sw.println( " param); ");
        sw.println("}");
    }

    private void generateAsyncService(TreeLogger logger, SourceWriter sw, JClassType type){
        String className = type.getQualifiedSourceName().replaceAll("\\.", "_") + "_RemoteServiceAsync";
        sw.println("public static interface "+className+"{");
        sw.print( "public void get(");
        sw.print( type.getQualifiedSourceName() );
        sw.print( " param, AsyncCallback<");
        sw.print( type.getQualifiedSourceName() );
        sw.println( "> callback );");
        sw.println("}");
    }
}
