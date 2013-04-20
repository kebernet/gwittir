/*
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
package com.totsp.gwittir.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import com.totsp.gwittir.rebind.introspection.BeanResolver;
import com.totsp.gwittir.rebind.introspection.IntrospectorGenerator;
import com.totsp.gwittir.rebind.introspection.RProperty;
import com.totsp.gwittir.serial.client.SerializationException;
import com.totsp.gwittir.json.client.JSONCodec;
import com.totsp.gwittir.json.client.JSONField;
import com.totsp.gwittir.json.client.JSONOmit;

import java.io.PrintWriter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * @author kebernet
 */
public class JSONCodecGenerator extends IntrospectorGenerator {
    private static final HashSet<String> CORE_TYPES = new HashSet<String>();

    static {
        CORE_TYPES.add(String.class.getCanonicalName());
        CORE_TYPES.add(Double.class.getCanonicalName());
        CORE_TYPES.add(double.class.getCanonicalName());
        CORE_TYPES.add(Integer.class.getCanonicalName());
        CORE_TYPES.add(int.class.getCanonicalName());
        CORE_TYPES.add(Float.class.getCanonicalName());
        CORE_TYPES.add(float.class.getCanonicalName());
        CORE_TYPES.add(Long.class.getCanonicalName());
        CORE_TYPES.add(long.class.getCanonicalName());
        CORE_TYPES.add(Boolean.class.getCanonicalName());
        CORE_TYPES.add(boolean.class.getCanonicalName());
        CORE_TYPES.add(Short.class.getCanonicalName());
        CORE_TYPES.add(short.class.getCanonicalName());
        CORE_TYPES.add(Character.class.getCanonicalName());
        CORE_TYPES.add(char.class.getCanonicalName());
    }

    private HashSet<BeanResolver> children = new HashSet<BeanResolver>();
    private JClassType collectionType;
    private JClassType numberType;
    private JType listType;
    private JType setType;
    private List<BeanResolver> types;

    public String fromType(JType type, String innerExpression) {
        if (type.getQualifiedSourceName().equals(String.class.getCanonicalName())) {
            return innerExpression + ".isString().stringValue()";
        } else if (type.getQualifiedSourceName()
                           .equals(Double.class.getCanonicalName()) ||
                ((type.isPrimitive() != null) &&
                JPrimitiveType.DOUBLE.equals(type.isPrimitive()))) {
            return innerExpression + ".isNumber().doubleValue()";
        } else if (type.getQualifiedSourceName()
                           .equals(Float.class.getCanonicalName()) ||
                ((type.isPrimitive() != null) &&
                JPrimitiveType.FLOAT.equals(type.isPrimitive()))) {
            return " (float) " + innerExpression + ".isNumber().doubleValue()";
        } else if (type.getQualifiedSourceName()
                           .equals(Integer.class.getCanonicalName()) ||
                ((type.isPrimitive() != null) &&
                JPrimitiveType.INT.equals(type.isPrimitive()))) {
            return "Double.valueOf(" + innerExpression +
            ".isNumber().doubleValue()) .intValue()";
        } else if (type.getQualifiedSourceName()
                           .equals(Short.class.getCanonicalName()) ||
                ((type.isPrimitive() != null) &&
                JPrimitiveType.SHORT.equals(type.isPrimitive()))) {
            return "Short.valueOf(" + innerExpression +
            ".isNumber().doubleValue()) .shortValue()";
        }  else if (type.getQualifiedSourceName()
                           .equals(Character.class.getCanonicalName()) ||
                ((type.isPrimitive() != null) &&
                JPrimitiveType.CHAR.equals(type.isPrimitive()))) {
            return "" + innerExpression +
            ".isString().stringValue().charAt(0)";
        } else if (type.getQualifiedSourceName()
                           .equals(Long.class.getCanonicalName()) ||
                ((type.isPrimitive() != null) &&
                JPrimitiveType.LONG.equals(type.isPrimitive()))) {
            return "Double.valueOf( " + innerExpression +
            ".isNumber().doubleValue()).longValue()";
        } else if (type.getQualifiedSourceName()
                           .equals(Boolean.class.getCanonicalName()) ||
                ((type.isPrimitive() != null) &&
                JPrimitiveType.BOOLEAN.equals(type.isPrimitive()))) {
            return innerExpression + ".isBoolean().booleanValue() ";
        }

        BeanResolver child = findType(type);

        if (child != null) {
            this.children.add(child);

            return "CODEC_" +
            child.getType().getQualifiedSourceName().replaceAll("\\.", "_") +
            ".deserializeFromJSONObject(" + innerExpression + ".isObject())";
        }

        throw new RuntimeException(""+type);
    }

    @Override
    public String generate(TreeLogger logger, GeneratorContext context,
        String typeName) throws UnableToCompleteException {
        logger.log(Type.INFO, "Generating codec for " + typeName);

        JClassType type = null;
        JClassType jsonCodec = null;

        try {
            type = context.getTypeOracle().getType(typeName);
            jsonCodec = context.getTypeOracle()
                               .getType(JSONCodec.class.getCanonicalName());
            this.collectionType = context.getTypeOracle()
                                         .getType(Collection.class.getCanonicalName());
            this.listType = context.getTypeOracle()
                                   .getType(List.class.getCanonicalName());
            this.setType = context.getTypeOracle()
                                  .getType(Set.class.getCanonicalName());
            this.numberType = context.getTypeOracle()
                                     .getType(Number.class.getCanonicalName());
        } catch (NotFoundException ex) {
            logger.log(TreeLogger.ERROR, typeName, ex);

            return null;
        }

        if (type.isClass() != null) { //Don't regenerate from Impls.

            return type.getQualifiedSourceName();
        }

        JClassType subtype = type.asParameterizationOf((JGenericType) jsonCodec)
                                 .getTypeArgs()[0];
        this.types = this.getIntrospectableTypes(logger, context.getTypeOracle());

        BeanResolver thisType = null;

        for (BeanResolver r : this.types) {
            if (r.getType().equals(subtype)) {
                thisType = r;

                break;
            }
        }

        if (thisType == null) {
            logger.log(Type.ERROR,
                "Unable to find introspectable type " + subtype);
            throw new UnableToCompleteException();
        }

        this.writeClassSerializer(logger, context, thisType);

        this.writeTopSerializer(logger, context, type, subtype);

        return type.getQualifiedSourceName() + "_Impl";
    }

    public void writeReader(SourceWriter writer, RProperty prop) {
        if(prop.getWriteMethod() == null ){
            return;
        }

        JSONField field = prop.getReadMethod() == null ? null : prop.getReadMethod().getBaseMethod()
                              .getAnnotation(JSONField.class);
        JSONOmit omit = prop.getReadMethod() == null ? null : prop.getReadMethod().getBaseMethod()
                            .getAnnotation(JSONOmit.class);
        System.out.println( prop.getName() + " omit "+omit + " field "+field );
        if (omit != null) {
            return;
        }

        String fieldName = (field == null) ? prop.getName() : field.value();

        try {
            writer.println("if(root.containsKey(\"" + fieldName + "\")){");

            if (prop.getType().isPrimitive() == null) {
                writer.println("if(root.get(\"" + fieldName +
                    "\").isNull() != null) {");
                writer.println(this.setterPrefix(prop) + "null);");
                writer.println("} else {");
            }

            if(prop.getType().isArray() != null){
                JArrayType arrayType = prop.getType().isArray();
                JType paramType = arrayType.getComponentType();
                writer.println("JSONArray array = root.get(\"" + fieldName +
                    "\").isArray();");
                writer.println( paramType.getQualifiedSourceName()+"[] value = new "+
                        paramType.getQualifiedSourceName()+"[ array.size() ];");
                writer.println("for(int i=0; i<array.size(); i++){");
                writer.indent();

                writer.println(" value[i] = " +
                    this.fromType(paramType, "array.get(i)") + ";");

                writer.outdent();
                writer.println("}"); //endfor
                writer.println(this.setterPrefix(prop) + " value );");
            } else if (prop.getType() instanceof JClassType &&
                    ((JClassType) prop.getType()).isAssignableTo(
                        this.collectionType)) {
                // get the parameter type
                JClassType propType = (JClassType) prop.getType();
                JType paramType = propType.asParameterizationOf((JGenericType) this.collectionType)
                                          .getTypeArgs()[0];
                writer.println("JSONArray array = root.get(\"" + fieldName +
                    "\").isArray();");
                writer.println(propType.getParameterizedQualifiedSourceName() +
                    " col = " + this.newCollectionExpression(propType) + ";");
                writer.println("for(int i=0; i<array.size(); i++){");
                writer.indent();

                writer.println(" col.add(" +
                    this.fromType(paramType, "array.get(i)") + ");");

                writer.outdent();
                writer.println("}"); //endfor
                writer.println(this.setterPrefix(prop) + " col );");
            } else {
                writer.println(setterPrefix(prop) +
                    fromType(prop.getType(), "root.get(\"" + fieldName + "\")") +
                    ");");
            }

            if (prop.getType().isPrimitive() == null) {
                writer.println("}"); //end null else
            }

            writer.println("}"); //end contains key
        } catch (Exception e) {
            System.out.println("Exception on prop " + prop);
            throw new RuntimeException(e);
        }
    }

    private boolean isCoreType(JType type) {
        return CORE_TYPES.contains(type.getQualifiedSourceName());
    }

    private BeanResolver findType(JType type) {
        for (BeanResolver r : this.types) {
            if (r.getType().equals(type)) {
                return r;
            }
        }

        return null;
    }

    private String newCollectionExpression(JClassType type) {
        if (type.isParameterized().getBaseType().equals(this.listType)) {
            return "new java.util.ArrayList()";
        } else if (type.isParameterized().getBaseType().equals(this.setType)) {
            return "new java.util.HashSet()";
        } else {
            return "new " + type.getParameterizedQualifiedSourceName() + "()";
        }
    }

    private String setterPrefix(RProperty prop) {
        return "destination." +
        prop.getWriteMethod().getBaseMethod().getName() + "( ";
    }

    private String toType(JType type, String innerExpression) {
        //System.out.println("toType " + type);
        if ((type.isPrimitive() == JPrimitiveType.DOUBLE) ||
                (type.isPrimitive() == JPrimitiveType.FLOAT) ||
                (type.isPrimitive() == JPrimitiveType.LONG) ||
                (type.isPrimitive() == JPrimitiveType.INT) ||
                (type.isPrimitive() == JPrimitiveType.SHORT)) {
            return " new JSONNumber( (double) " + innerExpression + ")";
        } else if (type.isPrimitive() == JPrimitiveType.BOOLEAN) {
            return " JSONBoolean.getInstance( " + innerExpression + " ) ";
        } else if (type.isPrimitive() == JPrimitiveType.CHAR ){
            return " new JSONString( Character.toString("+ innerExpression +") )";
        }

        StringBuilder sb = new StringBuilder(innerExpression +
                " == null ? JSONNull.getInstance() : ");

        if (type.getQualifiedSourceName().equals("java.lang.String")) {
            sb = sb.append(" new JSONString( " + innerExpression + " ) ");
        } else if(type.getQualifiedSourceName().equals("java.lang.Character")){
            sb = sb.append(" new JSONString( Character.toString(" + innerExpression + ") ) ");
        }else if (type.isClassOrInterface() != null && type.isClassOrInterface().isAssignableTo(this.numberType)) {
            sb = sb.append("new JSONNumber( ((Number) " + innerExpression +
                    ").doubleValue())");
        } else if (type.getQualifiedSourceName().equals("java.lang.Boolean")) {
            sb.append(" JSONBoolean.getInstance( " + innerExpression + " ) ");
        } else {
       
            BeanResolver child = findType(type);
            if (child == null) {
                throw new RuntimeException(type+" is not introspectable!");
            }
            this.children.add(child);
            sb = sb.append("CODEC_" +
                    type.getQualifiedSourceName().replaceAll("\\.", "_") +
                    ".serializeToJSONObject( " + innerExpression + " ) ");
        }

        return sb.toString();
    }

    private void writeClassSerializer(TreeLogger logger,
        GeneratorContext context, BeanResolver type) {
        logger.log(Type.INFO, "Creating JSON Serializer for " + type.getType());

        String classTypeName = type.getType().getSimpleSourceName() +
            "_JSONCodec";
        ClassSourceFileComposerFactory mcf = new ClassSourceFileComposerFactory(type.getType()
                                                                                    .getPackage()
                                                                                    .getName(),
                classTypeName);
        mcf.addImport(JSONParser.class.getCanonicalName());
        mcf.addImport(JSONObject.class.getCanonicalName());
        mcf.addImport(JSONArray.class.getCanonicalName());
        mcf.addImport(JSONBoolean.class.getCanonicalName());
        mcf.addImport(JSONNumber.class.getCanonicalName());
        mcf.addImport(JSONString.class.getCanonicalName());
        mcf.addImport(JSONNull.class.getCanonicalName());
        mcf.addImport(GWT.class.getCanonicalName());
        mcf.addImport(SerializationException.class.getCanonicalName());
        mcf.addImplementedInterface(JSONCodec.class.getCanonicalName() + "<" +
            type.getType().getQualifiedSourceName() + ">");

        PrintWriter printWriter = context.tryCreate(logger,
                type.getType().getPackage().getName(), classTypeName);

        if (printWriter == null) {
            logger.log(Type.INFO, "Already genned " + classTypeName);

            return;
        }

        SourceWriter writer = mcf.createSourceWriter(context, printWriter);

        writeDeserializer(writer, type);
        writeSerializer(writer, type);

        HashSet<BeanResolver> childrenCopy = new HashSet<BeanResolver>(this.children);
        this.children.clear();

        for (BeanResolver child : childrenCopy) {
            writer.println(" private static final " +
                JSONCodec.class.getCanonicalName() + "<" +
                child.getType().getQualifiedSourceName() + "> CODEC_" +
                child.getType().getQualifiedSourceName().replaceAll("\\.", "_") +
                " = GWT.create(" + child.getType().getQualifiedSourceName() +
                "_JSONCodec.class);");
            writeClassSerializer(logger, context, child);
        }

        writer.println(" public String getMimeType() { return MIME_TYPE; }");
        writer.println("}"); // close the class

        context.commit(logger, printWriter);
    }

    private void writeDeserializer(SourceWriter writer, BeanResolver r) {
        writer.println("public " + r.toString() +
            " deserialize(String data) throws SerializationException { ");
        writer.indent();
        writer.println("try {");
        writer.indent();
        writer.println("JSONObject root = JSONParser.parse(data).isObject(); ");
        writer.println(" return this.deserializeFromJSONObject(root);");
        writer.println("} catch (Exception e) { ");
        writer.indent();
        writer.println("throw new SerializationException(e);");
        writer.outdent();
        writer.println("}");
        writer.println("}");

        writer.println("public " + r.toString() +
            " deserializeFromJSONObject(JSONObject root) throws SerializationException {");
        writer.indent();
        writer.println(" if(root == null) return null;");
        writer.println("try {");
        writer.indent();

        writer.println(r.getType().getQualifiedSourceName() +
            " destination = new " + r.getType().getQualifiedSourceName() +
            "();");

        for (RProperty p : r.getProperties().values()) {
            if (p.getWriteMethod() != null) {
                writeReader(writer, p);
            }
        }

        writer.println(" return destination;");
        writer.outdent();
        writer.println("} catch (Exception e) { ");
        writer.indent();
        writer.println("throw new SerializationException(e);");
        writer.outdent();
        writer.println("}");
        writer.outdent();
        writer.println("}");
    }

    private void writeSerializer(SourceWriter writer, BeanResolver r) {
        writer.println("public JSONObject serializeToJSONObject( " +
            r.getType().getQualifiedSourceName() +
            " source ) throws SerializationException { ");
        writer.indent();
        writer.println(" JSONObject destination = new JSONObject();");

        for (RProperty prop : r.getProperties().values()) {
            if (prop.getName().equals("class") || prop.getReadMethod() == null) {
                continue;
            }

            JSONField field = prop.getReadMethod().getBaseMethod()
                                  .getAnnotation(JSONField.class);
            JSONOmit omit = prop.getReadMethod().getBaseMethod()
                                .getAnnotation(JSONOmit.class);
            System.out.println(" ws \t "+prop.getName() +" "+ prop.getReadMethod().getBaseMethod().getEnclosingType()+ prop.getReadMethod().getBaseMethod().getReadableDeclaration()  + " "+ omit +" "+field );
            if (omit != null) {
                continue;
            }

            String fieldName = (field == null) ? prop.getName() : field.value();

            if (prop.getReadMethod() != null) {
                JClassType classType = prop.getType().isClassOrInterface();
                JArrayType arrayType = prop.getType().isArray();
                System.out.println(prop.getName()+ "  ArrayType "+arrayType +" :: "+((arrayType == null ? "" : ""+arrayType.getComponentType())));
                if ((classType != null) &&
                        (classType.isAssignableTo(this.collectionType)) ||
                        arrayType != null) {
                    JType subType = (arrayType != null)
                        ? arrayType.getComponentType()
                        : classType.asParameterizationOf(this.collectionType.isGenericType())
                                   .getTypeArgs()[0];
                    writer.println();
                    writer.println(" if( source." +
                        prop.getReadMethod().getBaseMethod().getName() +
                        "() == null ){");
                    writer.println("destination.put(\"" + fieldName +
                        "\", JSONNull.getInstance());");
                    writer.println(" } else { ");
                    writer.println(
                        "int i=0; JSONArray value = new JSONArray();");
                    writer.println("for( " + subType.getQualifiedSourceName() +
                        " o : source." +
                        prop.getReadMethod().getBaseMethod().getName() +
                        "()){");
                    writer.println("   value.set(i++, " +
                        toType(subType, " o ") + ");");
                    writer.println("}");
                    writer.println("destination.put(\"" + fieldName +
                        "\", value);"); //TODO JSONField
                    writer.println("}");
                } else {
                    writer.print("destination.put( \"" + fieldName + "\", "); //TODO JSONField
                    writer.print(toType(prop.getType(),
                            " source." +
                            prop.getReadMethod().getBaseMethod().getName() +
                            "() ") + ");");
                }
            }
        }

        writer.outdent();
        writer.println("return destination;");
        writer.println("}");

        writer.println("public String serialize(" +
            r.getType().getQualifiedSourceName() +
            " source ) throws SerializationException { ");
        writer.println("   return serializeToJSONObject(source).toString();");
        writer.println("}");
    }

    private void writeTopSerializer(TreeLogger logger,
        GeneratorContext context, JClassType typeFor, JClassType subType) {
        ClassSourceFileComposerFactory mcf = new ClassSourceFileComposerFactory(typeFor.getPackage()
                                                                                       .getName(),
                typeFor.getSimpleSourceName() + "_Impl");
        mcf.addImplementedInterface(typeFor.getParameterizedQualifiedSourceName());
        mcf.setSuperclass(subType.getParameterizedQualifiedSourceName() +
            "_JSONCodec");

        PrintWriter printWriter = context.tryCreate(logger,
                typeFor.getPackage().getName(),
                typeFor.getSimpleSourceName() + "_Impl");

        if (printWriter == null) {
            logger.log(Type.INFO,
                "Already genned " + typeFor.getSimpleSourceName() + "_Impl");

            return;
        }

        SourceWriter writer = mcf.createSourceWriter(context, printWriter);
        writer.println(" public String getMimeType() { return MIME_TYPE; }");
        writer.println("}");
        context.commit(logger, printWriter);
    }
}
