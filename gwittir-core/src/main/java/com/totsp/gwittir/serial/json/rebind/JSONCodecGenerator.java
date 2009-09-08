/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.serial.json.rebind;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.totsp.gwittir.rebind.beans.BeanResolver;
import com.totsp.gwittir.rebind.beans.IntrospectorGenerator;
import com.totsp.gwittir.rebind.beans.Property;
import com.totsp.gwittir.serial.client.SerializationException;
import com.totsp.gwittir.serial.json.client.JSONCodec;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author kebernet
 */
public class JSONCodecGenerator extends IntrospectorGenerator {

     private JType objectType;
     private List<BeanResolver> types;

    @Override
     public String generate(TreeLogger logger, GeneratorContext context, String typeName)
        throws UnableToCompleteException {
        System.out.println("Generating codec for "+typeName);
        JClassType type = null;
        JClassType jsonCodec = null;
        try {
            this.objectType = context.getTypeOracle().getType("java.lang.Object");
            type = context.getTypeOracle().getType(typeName);
            jsonCodec = context.getTypeOracle().getType(JSONCodec.class.getCanonicalName() );


        } catch (NotFoundException ex) {
            logger.log(TreeLogger.ERROR, typeName, ex);
            return null;
        }
        JType subtype = type.asParameterizationOf((JGenericType) jsonCodec).getTypeArgs()[0];
        System.out.println("Got subtype "+subtype);

        



        this.types = this.getIntrospectableTypes(logger, context.getTypeOracle() );
        BeanResolver thisType = null;
        for(BeanResolver r: this.types ){
            if(r.getType().equals(subtype)){
                thisType = r;
                break;
            }
        }
        if(thisType == null ){
            logger.log(Type.ERROR, "Unable to find introspectable type "+subtype );
            throw new UnableToCompleteException();
        }

        System.out.println("Got BeanResolver "+thisType);
        String implName = type.getSimpleSourceName()+"_Impl";


        this.writeClassSerializer(logger, context, thisType);





        //String className = typeName+"_Impl";
        return null;
     }
    
    private void writeClassSerializer(TreeLogger logger, GeneratorContext context, BeanResolver type ){
         String classTypeName = type.getType().getSimpleSourceName()+"_JSONCodec";
         ClassSourceFileComposerFactory mcf = new ClassSourceFileComposerFactory(
                type.getType().getPackage().getName(),
                classTypeName
            );
        mcf.addImport(JSONParser.class.getCanonicalName() );
        mcf.addImport(JSONObject.class.getCanonicalName() );
        mcf.addImport(JSONArray.class.getCanonicalName() );
        mcf.addImport(JSONBoolean.class.getCanonicalName() );
        mcf.addImport(JSONNumber.class.getCanonicalName() );
        mcf.addImport(JSONString.class.getCanonicalName() );
        mcf.addImport(SerializationException.class.getCanonicalName() );
        
        PrintWriter printWriter = context.tryCreate(logger, type.getType().getPackage().getName(),
                classTypeName);
        
        SourceWriter writer = mcf.createSourceWriter(context, printWriter);

        writeDeserializer(writer, type);


        context.commit(logger, printWriter);
    }



    private void writeDeserializer(SourceWriter writer, BeanResolver r){
        writer.println( "public "+r.toString() +" deserialize(String data) throws SerializationException { ");
        writer.indent();
        writer.println("try {");
        writer.indent();
        writer.println( "JSONObject root = JSONParser.parse(data).isObject(); ");
        writer.println( r.getType().getQualifiedSourceName()+" destination = new "+r.getType().getQualifiedSourceName()+"();");
        for(Property p : r.getProperties().values() ){
            writeReader(writer, p);
        }
        writer.println(" return destination;");
        writer.outdent();
        writer.println("} catch (Exception e) { ");
        writer.indent();
        writer.println("throw new SerializationException(e);");
        writer.outdent();
        writer.println("}");
        writer.outdent();
    }

    public void writeReader(SourceWriter writer, Property prop ){
        if(isCoreType(prop.getType())){
            writer.println( "if(root.containsKey(\""+prop.getName()+"\") )");
            writer.println( setterPrefix(prop)+ fromType(prop.getType(), "root.get(\""+prop.getName()+"\")" )+");");
        } 
    }

    private static final HashSet<String> CORE_TYPES = new HashSet<String>();
    static{
        CORE_TYPES.add(String.class.getCanonicalName() );
        CORE_TYPES.add(Double.class.getCanonicalName() );
        CORE_TYPES.add(double.class.getCanonicalName() );
        CORE_TYPES.add(Integer.class.getCanonicalName() );
        CORE_TYPES.add(int.class.getCanonicalName() );
        CORE_TYPES.add(Float.class.getCanonicalName() );
        CORE_TYPES.add(float.class.getCanonicalName() );
        CORE_TYPES.add(Long.class.getCanonicalName() );
        CORE_TYPES.add(long.class.getCanonicalName() );
        CORE_TYPES.add(Boolean.class.getCanonicalName() );
        CORE_TYPES.add(boolean.class.getCanonicalName() );
    }
    private boolean isCoreType(JType type){
        return CORE_TYPES.contains(type.getQualifiedSourceName());
    }

    public String fromType(JType type, String innerExpression ){
        if(type.getQualifiedSourceName().equals(String.class.getCanonicalName())){
           return innerExpression+".isString().stringValue()";
        } else if(type.getQualifiedSourceName().equals(Double.class.getCanonicalName() ) || JPrimitiveType.DOUBLE.equals(type.isPrimitive())){
            return innerExpression+".isNumber().doubleValue()";
        } else if(type.getQualifiedSourceName().equals(Float.class.getCanonicalName() ) || JPrimitiveType.FLOAT.equals(type.isPrimitive())){
            return " (float) "+innerExpression+".isNumber().doubleValue()";
        } else if(type.getQualifiedSourceName().equals(Integer.class.getCanonicalName() ) || JPrimitiveType.INT.equals(type.isPrimitive())){
            return "Double.valueOf("+innerExpression+".isNumber().doubleValue()) .intValue()";
        } else if(type.getQualifiedSourceName().equals(Long.class.getCanonicalName() ) || JPrimitiveType.LONG.equals(type.isPrimitive())){
            return "Double.valueOf( "+innerExpression+".isNumber().doubleValue()).longValue()";
        } else if(type.getQualifiedSourceName().equals(Boolean.class.getCanonicalName() ) || JPrimitiveType.BOOLEAN.equals(type.isPrimitive())){
            return innerExpression+".isBoolean().booleanValue() ";
        }
        throw new RuntimeException();
    }

    private String setterPrefix( Property prop ){
        return "destination."+prop.getWriteMethod().getBaseMethod().getName()
                    +"( ";
    }

}
