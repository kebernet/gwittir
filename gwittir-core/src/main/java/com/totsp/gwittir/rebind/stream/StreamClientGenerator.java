/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.rebind.stream;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import com.totsp.gwittir.client.stream.StreamControl;
import com.totsp.gwittir.client.stream.StreamServiceCallback;
import com.totsp.gwittir.client.stream.impl.StreamingServiceStub;
import com.totsp.gwittir.client.stream.StreamServiceIterator;

import java.io.PrintWriter;


/**
 *
 * @author kebernet
 */
public class StreamClientGenerator extends Generator {
    JType objectType;
    JClassType streamServiceIterator;

    @Override
    public String generate(TreeLogger logger, GeneratorContext context,
        String typeName) throws UnableToCompleteException {
        JClassType streamServiceType = null;

        try {
            this.objectType = context.getTypeOracle().getType("java.lang.Object");
            streamServiceType = context.getTypeOracle().getType(typeName);
            streamServiceIterator = context.getTypeOracle()
                                           .getType(StreamServiceIterator.class.getCanonicalName());
        } catch (NotFoundException ex) {
            logger.log(TreeLogger.ERROR, typeName, ex);

            return null;
        }

        this.generateRemoteService(logger, context, streamServiceType);
        this.generateRemoteServiceAsync(logger, context, streamServiceType);

        String className =  this.generateImplementation(logger, context, streamServiceType);
        logger.log( TreeLogger.Type.WARN, "Rebound: "+className);
        return className;
    }

    protected String generateImplementation(TreeLogger logger,
        GeneratorContext context, JClassType type) throws UnableToCompleteException {
        String packageName = type.getQualifiedSourceName() + "_impls";
        String className = type.getSimpleSourceName() + "_StreamServiceImpl";
        String remoteServiceName = type.getSimpleSourceName() + "_RemoteService";
        JClassType asyncType = null;
        try {
            asyncType = context.getTypeOracle().getType(type.getQualifiedSourceName() + "Async");
        } catch (NotFoundException ex) {
            logger.log( TreeLogger.Type.ERROR, "Unable to find async type for "+type, ex);
            throw new UnableToCompleteException();
        }

        ClassSourceFileComposerFactory mcf = new ClassSourceFileComposerFactory(packageName,
                className);
        mcf.setSuperclass(StreamingServiceStub.class.getCanonicalName());
        mcf.addImplementedInterface(type.getQualifiedSourceName() + "Async");
        mcf.addImport( SerializationStreamFactory.class.getCanonicalName() );
        mcf.addImport( GWT.class.getCanonicalName() );
        mcf.addImport( StreamServiceCallback.class.getCanonicalName() );
        mcf.addImport( ClientSerializationStreamWriter.class.getCanonicalName() );
        mcf.addImport( SerializationException.class.getCanonicalName() );
        PrintWriter pw = context.tryCreate(logger, packageName, className);
        if(pw == null){
             return packageName +"."+className;
        }
        SourceWriter sw = mcf.createSourceWriter(context, pw);

        String generatedRemoteService = type.getQualifiedSourceName() + "_impls"
                + "." +type.getSimpleSourceName() + "_RemoteService";
        sw.print( "private static final SerializationStreamFactory SERIALIZER =");
        sw.print( "(SerializationStreamFactory) GWT.create(");
        sw.print( generatedRemoteService );
        sw.println( ".class);");
        sw.print ( "private static final String REMOTE_SERVICE_INTERFACE_NAME = \"");
        sw.print ( packageName );
        sw.print (".");
        sw.print ( remoteServiceName );
        sw.println( "\";");
        sw.println("public SerializationStreamFactory getStreamFactory() { return SERIALIZER; }");
        for (JMethod method : type.getMethods()) {
        	writeMethod(logger, sw, asyncType, method);
        }

        sw.outdent();
        sw.println("}");
        context.commit(logger, pw);

        return packageName + "." + className;
    }

    protected void writeMethod(TreeLogger logger, SourceWriter sw, JClassType asyncType, JMethod method ) throws UnableToCompleteException{
    	if (!(method.getReturnType() instanceof JClassType)) {
            logger.log(TreeLogger.Type.ERROR,
                method.getReturnType().getQualifiedSourceName() +
                " is not a class type.", null);
            throw new UnableToCompleteException();
        }

        JClassType returnType = (JClassType) method.getReturnType();

        if (!returnType.isAssignableTo(this.streamServiceIterator)) {
            logger.log(TreeLogger.Type.ERROR,
                returnType.getQualifiedSourceName() +
                " is not assignable to " +
                this.streamServiceIterator.getQualifiedSourceName());
            throw new UnableToCompleteException();
        }

//        String parameterType = "java.io.Serializable";
//        JGenericType generic = returnType.isGenericType();
//
//        if ((generic != null) && (generic.getTypeParameters().length > 1)) {
//            logger.log(TreeLogger.Type.ERROR, "WTF scoob?");
//            throw new UnableToCompleteException();
//        } else if (generic != null) {
//            parameterType = generic.getTypeParameters()[0].getQualifiedSourceName();
//        }

        sw.println("public "+StreamControl.class.getCanonicalName()+" " + method.getName() + " (");

        boolean first = true;

        for (JParameter param : method.getParameters()) {
            if (!first) {
                sw.print(", ");
            } else {
                first = false;
            }

            sw.print(param.getType().getQualifiedSourceName());
            sw.print(" ");
            sw.print(param.getName());
        }
        sw.println(" , StreamServiceCallback callback");
        sw.println(") {");
        sw.indent();
        sw.println("try{");
            sw.indent();
        sw.println( "ClientSerializationStreamWriter streamWriter = (ClientSerializationStreamWriter) SERIALIZER.createStreamWriter();");
        sw.println( "streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);");
        sw.println( "streamWriter.writeString(\""+ method.getName() +"\");");
        sw.println( "streamWriter.writeInt("+method.getParameters().length+");" );
        for(JParameter param : method.getParameters() ){
            sw.println( "streamWriter.writeString(\""+ this.getJSNIType(param.getType())+"\");");
        }
        for(JParameter param : method.getParameters() ){
            sw.println("streamWriter."+ this.getWriteCallName(param.getType()) +"("+param.getName()+");");
        }
        sw.outdent();
        try{
            JMethod asyncMethod = this.getAsyncMethod(asyncType, method);
            String callbackParam = asyncMethod.getParameters()[asyncMethod.getParameters().length-1].getName();
            
            sw.println("return invoke(streamWriter.toString(),"+callbackParam+");");
            sw.outdent();
            sw.println("}catch(Exception se){");
            sw.indent();
            sw.println("throw new RuntimeException(se);");
            sw.outdent();
            sw.println("}");
        } catch(UnableToCompleteException e){
            logger.log(TreeLogger.Type.ERROR, "Unabel to find matching async method for "+ method.getReadableDeclaration());
            throw e;
        }
        sw.println("}");
    }
    
    
    protected JMethod getAsyncMethod( JClassType asyncType, JMethod syncMethod ) throws UnableToCompleteException{
        for(JMethod sourceMethod : asyncType.getMethods() ){

            if( sourceMethod.getName(). equals(syncMethod.getName() )
                    && sourceMethod.getParameters().length == syncMethod.getParameters().length + 1){
                    boolean notIt = false;
                    for( int i=0; i < syncMethod.getParameters().length; i++ ){
                        if( !syncMethod.getParameters()[i].getType().equals( sourceMethod.getParameters()[i].getType() ) ){
                            notIt = true;
                            break;
                        }
                    }
                    if( notIt ){
                        continue;
                    } else {
                        return sourceMethod;
                    }
            }
        }
        throw new UnableToCompleteException();

    }

    private String getJSNIType(JType type){
       if (type.isArray() != null ){
            return type.getJNISignature().replaceAll("/", ".");
       } else if( type.getJNISignature().length() > 1 ){
           return type.getQualifiedSourceName();
       } else {
           return type.getJNISignature();
       }
    }

    private String getWriteCallName(JType type){
        if( getJSNIType(type).length() == 1){
            String typeName = type.toString();
            return "write"+typeName.substring(0,1).toUpperCase() + typeName.substring(1, typeName.length() );
        } else if( getJSNIType(type).equals("java.lang.String")  ){
            return "writeString";
        } else {
            return "writeObject";
        }
    }

    private void generateRemoteService(TreeLogger logger,
        GeneratorContext context, JClassType type)
        throws UnableToCompleteException {
        String packageName = type.getQualifiedSourceName() + "_impls";
        String className = type.getSimpleSourceName() + "_RemoteService";
        ClassSourceFileComposerFactory mcf = new ClassSourceFileComposerFactory(packageName,
                className);
        mcf.makeInterface();
        mcf.addImplementedInterface(
            "com.google.gwt.user.client.rpc.RemoteService");

        PrintWriter pw = context.tryCreate(logger, packageName, className);
        if( pw == null ){
           return;
        }
        SourceWriter sw = mcf.createSourceWriter(context, pw);

        for (JMethod method : type.getMethods()) {
            if (!(method.getReturnType() instanceof JClassType)) {
                logger.log(TreeLogger.Type.ERROR,
                    method.getReturnType().getQualifiedSourceName() +
                    " is not a class type.", null);
                throw new UnableToCompleteException();
            }

            JClassType returnType = (JClassType) method.getReturnType();

            if (!returnType.isAssignableTo(this.streamServiceIterator)) {
                logger.log(TreeLogger.Type.ERROR,
                    returnType.getQualifiedSourceName() +
                    " is not assignable to " +
                    this.streamServiceIterator.getQualifiedSourceName());
                throw new UnableToCompleteException();
            }

            String parameterType = "java.io.Serializable";
//            JGenericType generic = returnType.isGenericType();
//
//            if ((generic != null) && (generic.getTypeParameters().length > 1)) {
//                logger.log(TreeLogger.Type.ERROR, "WTF scoob?");
//                throw new UnableToCompleteException();
//            } else if (generic != null) {
//                parameterType = generic.getTypeParameters()[0].getQualifiedSourceName();
//            }


            parameterType = this.determineParameterType(returnType);
            sw.println("public " + parameterType + " " + method.getName() + " ( ");

            boolean first = true;

            for (JParameter param : method.getParameters()) {
                if (!first) {
                    sw.print(", ");
                } else {
                    first = false;
                }

                sw.print(param.getType().getQualifiedSourceName());
                sw.print(" ");
                sw.print(param.getName());
            }

            sw.println(" )");

            if ((method.getThrows() != null) &&
                    (method.getThrows().length > 0)) {
                sw.print(" throws ");
                first = false;

                for (JType throwable : method.getThrows()) {
                    if (!first) {
                        sw.print(", ");
                    } else {
                        first = false;
                    }

                    sw.print(throwable.getQualifiedSourceName());
                }
            }

            sw.println(";");
            sw.outdent();
            sw.println("}");
            context.commit(logger, pw);
        }
    }

    private String determineParameterType(JType type){
        String parameterType = type.toString();;
        parameterType = parameterType.substring( parameterType.indexOf("<")+1, parameterType.lastIndexOf(">"));
        return parameterType;
    }

    private void generateRemoteServiceAsync(TreeLogger logger,
        GeneratorContext context, JClassType type)
        throws UnableToCompleteException {
        String packageName = type.getQualifiedSourceName() + "_impls";
        String className = type.getSimpleSourceName() + "_RemoteServiceAsync";
        ClassSourceFileComposerFactory mcf = new ClassSourceFileComposerFactory(packageName,
                className);
        mcf.makeInterface();
        PrintWriter pw = context.tryCreate(logger, packageName, className);
        if(pw == null){
            return;
        }
        SourceWriter sw = mcf.createSourceWriter(context, pw);

        for (JMethod method : type.getMethods()) {
            if (!(method.getReturnType() instanceof JClassType)) {
                logger.log(TreeLogger.Type.ERROR,
                    method.getReturnType().getQualifiedSourceName() +
                    " is not a class type.", null);
                throw new UnableToCompleteException();
            }

            JClassType returnType = (JClassType) method.getReturnType();

            if (!returnType.isAssignableTo(this.streamServiceIterator)) {
                logger.log(TreeLogger.Type.ERROR,
                    returnType.getQualifiedSourceName() +
                    " is not assignable to " +
                    this.streamServiceIterator.getQualifiedSourceName());
                throw new UnableToCompleteException();
            }

            //String parameterType = this.determineParameterType(returnType);
            /*JGenericType generic = returnType.isGenericType();

            logger.log(TreeLogger.Type.WARN, "Generic Type: "+generic);
            if ((generic != null) && (generic.getTypeParameters().length > 1)) {
                logger.log(TreeLogger.Type.ERROR, "WTF scoob?");
                throw new UnableToCompleteException();
            } else if (generic != null) {
                parameterType = generic.getTypeParameters()[0].getQualifiedSourceName();
            }
            */
            sw.println("public void " + method.getName() + " (");

            boolean first = true;

            for (JParameter param : method.getParameters()) {
                if (!first) {
                    sw.print(", ");
                } else {
                    first = false;
                }

                sw.print(param.getType().getQualifiedSourceName());
                sw.print(" ");
                sw.print(param.getName());
            }

            sw.print(", " + AsyncCallback.class.getCanonicalName() + " cb" +
                System.currentTimeMillis());
            sw.print(" ) ");

            sw.println(";");
            sw.outdent();
            sw.println("}");
            context.commit(logger, pw);
        }
    }
}
