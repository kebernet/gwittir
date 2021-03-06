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
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.totsp.gwittir.json.JSONCodec;
import com.totsp.gwittir.json.JSONDiscriminatorValue;
import com.totsp.gwittir.json.JSONField;
import com.totsp.gwittir.json.JSONOmit;
import com.totsp.gwittir.json.JSONSubclassed;
import com.totsp.gwittir.rebind.introspection.BeanResolver;
import com.totsp.gwittir.rebind.introspection.IntrospectorGenerator;
import com.totsp.gwittir.rebind.introspection.RProperty;
import com.totsp.gwittir.serial.SerializationException;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
    private List<BeanResolver> subtypes = new LinkedList<BeanResolver>();



    public String fromType(JType type, String innerExpression) {


        if(type.isEnum() != null) {
           return type.getQualifiedSourceName()+".valueOf("+innerExpression+".isString().stringValue())";
        } else if (type.getQualifiedSourceName().equals(String.class.getCanonicalName())) {
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

    public void emitDynamicReadMethod(SourceWriter writer){
        writer.println("public <T extends java.io.Serializable> T dynamicValue(JSONValue value){\n" +
                "        if(value instanceof JSONNull){\n" +
                "            return null;\n" +
                "        } else if(value instanceof JSONString){\n" +
                "            return  (T) ((JSONString) value).stringValue();\n" +
                "        } else if(value instanceof JSONBoolean ){\n" +
                "            return  (T) Boolean.valueOf(((JSONBoolean) value).booleanValue());\n" +
                "        }else if(value instanceof JSONObject){\n" +
                "            JSONObject object = ((JSONObject) value);\n" +
                "            JSONString qualifier = (JSONString) object.get(\"t\");\n" +
                "            JSONValue read = object.get(\"v\");\n" +
                "            if(qualifier.stringValue().equals(\"I\")){\n" +
                "                return (T) (Integer) Double.valueOf(((JSONNumber) read).doubleValue()).intValue();\n" +
                "            } else if(qualifier.stringValue().equals(\"L\")){\n" +
                "                return  (T) (Long) Double.valueOf(((JSONNumber) read).doubleValue()).longValue();\n" +
                "            } else if(qualifier.stringValue().equals(\"D\")){\n" +
                "                return  (T) (Double) Double.valueOf(((JSONNumber) read).doubleValue());\n" +
                "            } else if(qualifier.stringValue().equals(\"F\")){\n" +
                "                return  (T) (Float) Double.valueOf(((JSONNumber) read).doubleValue()).floatValue();\n" +
                "            } else if(qualifier.stringValue().equals(\"T\")){\n" +
                "                return  (T) new java.util.Date(Long.parseLong(((JSONString) read).stringValue()));\n" +
                "            }\n" +
                "        }\n" +
                "        throw new IllegalArgumentException(\"Unknown type \"+value);\n" +
                "    }\n");
    }
    private void emitDynamicTypeMethod(SourceWriter writer){
        writer.println("public JSONValue dynamicType(Object value){\n" +
                "            if(value instanceof String){\n" +
                "                return new JSONString((String) value);\n" +
                "            } else if(value instanceof Boolean ){" +
                "                return JSONBoolean.getInstance((Boolean) value);"+
                "            } else if(value instanceof Number){\n" +
                "                JSONObject o = new JSONObject();\n" +
                "                o.put(\"v\", new JSONNumber(((Number) value).doubleValue()));\n" +
                "                if(value instanceof Integer){\n" +
                "                    o.put(\"t\", new JSONString(\"I\"));\n" +
                "                } else if(value instanceof Long){\n" +
                "                    o.put(\"t\", new JSONString(\"L\"));\n" +
                "                }  else if(value instanceof Double){\n" +
                "                    o.put(\"t\", new JSONString(\"D\"));\n" +
                "                }  else if(value instanceof Float){\n" +
                "                    o.put(\"t\", new JSONString(\"F\"));\n" +
                "                }\n" +
                "                return o;\n" +
                "            } else if(value instanceof java.util.Date){\n" +
                "                JSONObject o = new JSONObject();\n" +
                "                o.put(\"t\", new JSONString(\"T\"));\n" +
                "                o.put(\"v\", new JSONString(\"\"+((java.util.Date) value).getTime()));\n" +
                "                return o;\n" +
                "            }\n" +
                "            throw new IllegalArgumentException(value.getClass()+\" is unknown!\");\n" +
                "        }");

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

        BeanResolver thisType = findType(subtype);

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
        //System.out.println( prop.getName() + " omit "+omit + " field "+field );
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

            } else if(prop.getType().getQualifiedSourceName().equals(Serializable.class.getCanonicalName())){
                writer.println(setterPrefix(prop)+"("+prop.getType().getQualifiedSourceName() +") dynamicValue( root.get(\""+fieldName+"\") ) );");
            } else if(prop.getType().isTypeParameter() != null ){
                String castName = prop.getType().isTypeParameter().getBounds()[0].getQualifiedSourceName();
                writer.println(setterPrefix(prop)+"("+castName +") dynamicValue( root.get(\""+fieldName+"\") ) );");
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
        return type.isEnum() != null || CORE_TYPES.contains(type.getQualifiedSourceName());
    }

    private BeanResolver findType(JType type) {
        for (BeanResolver r : this.types) {
            if (r.getType().getQualifiedSourceName().equals(type.getQualifiedSourceName())) {
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
        if(type.isEnum() != null){
            sb = sb.append(" new JSONString(( (Enum) "+innerExpression+").name()) ");
        } else if (type.getQualifiedSourceName().equals("java.lang.String")) {
            sb = sb.append(" new JSONString( " + innerExpression + " ) ");
        } else if(type.getQualifiedSourceName().equals("java.lang.Character")){
            sb = sb.append(" new JSONString( Character.toString(" + innerExpression + ") ) ");
        }else if (type.isClassOrInterface() != null && type.isClassOrInterface().isAssignableTo(this.numberType)) {
            sb = sb.append("new JSONNumber( ((Number) " + innerExpression +
                    ").doubleValue())");
        } else if (type.getQualifiedSourceName().equals("java.lang.Boolean")) {
            sb.append(" JSONBoolean.getInstance( " + innerExpression + " ) ");
        } else if(type.getQualifiedSourceName().equals(Serializable.class.getCanonicalName())){
            sb.append(" dynamicType("+innerExpression+" )");
        } else {
       
            BeanResolver child = findType(type);
            if (child == null) {
                if(type.isTypeParameter() != null){
                    boolean found = false;
                    for(JClassType bound : type.isTypeParameter().getBounds()){
                        if(bound.getQualifiedSourceName().equals(Serializable.class.getCanonicalName())){
                            sb.append(" dynamicType("+innerExpression+" ) ");
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        throw new RuntimeException(type+" could not be mapped to JSON.");
                    }
                }else {
                    throw new RuntimeException(type+" is not introspectable!");
                }
            } else {
                this.children.add(child);
                sb = sb.append("CODEC_" +
                        type.getQualifiedSourceName().replaceAll("\\.", "_") +
                        ".serializeToJSONObject( " + innerExpression + " ) ");
            }
        }

        return sb.toString();
    }

    private void writeClassSerializer(TreeLogger logger,
        GeneratorContext context, BeanResolver type) {
        logger.log(Type.INFO, "Creating JSON Serializer for " + type.getType());

        String classTypeName = type.getType().getSimpleSourceName() +
            "_JSONCodec";
        logger.log(Type.INFO, "Creating JSON Serializer" + classTypeName);
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
        mcf.addImport(JSONCodec.class.getCanonicalName());
        mcf.addImport(JSONValue.class.getCanonicalName());
        mcf.addImport(GWT.class.getCanonicalName());
        mcf.addImport(HashMap.class.getCanonicalName());
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

        this.emitDynamicTypeMethod(writer);
        this.emitDynamicReadMethod(writer);
        HashSet<String> includedCodecs = new HashSet<String>();


        if(type.getType().getAnnotation(JSONSubclassed.class) != null || type.getType().getAnnotation(JSONDiscriminatorValue.class) != null){
            logger.log(Type.INFO, type.getType().getQualifiedSourceName()+" is subclassed");
            writer.println("static final HashMap<String, JSONCodec> subclasses = new HashMap<String, JSONCodec>();");
            for(JClassType subtype : type.getType().getSubtypes()){
                logger.log(Type.INFO, type.getType().getQualifiedSourceName()+" subtype "+subtype.getQualifiedSourceName());
                while(!subtype.getSuperclass().getQualifiedSourceName().equals(type.getType().getQualifiedSourceName())){
                    logger.log(Type.INFO, "\t"+subtype.getSuperclass().getQualifiedSourceName() +" isn't "+type.getType().getQualifiedSourceName());
                    subtype = subtype.getSuperclass();
                }
                if(includedCodecs.contains(subtype.getQualifiedSourceName())){
                    continue;
                }
                includedCodecs.add(subtype.getQualifiedSourceName());

                logger.log(Type.INFO, subtype.getQualifiedSourceName()+" is a subclass of "+type.getType().getQualifiedSourceName());
                String instanceName = "CODEC_" + subtype.getQualifiedSourceName().replaceAll("\\.", "_");
                writer.println(" private static final " +
                        JSONCodec.class.getCanonicalName() + "<" +
                        subtype.getQualifiedSourceName() + "> "+ instanceName +
                        " = GWT.create(" + subtype.getQualifiedSourceName() +
                        "_JSONCodec.class);");
                JSONDiscriminatorValue discriminatorValue = subtype.getAnnotation(JSONDiscriminatorValue.class);
                if(discriminatorValue == null){
                    throw new RuntimeException("Class "+subtype.getQualifiedSourceName()+" is a subclass of "+type.getType().getQualifiedSourceName()+" but doesn't have a JSONDisciminiatorValue.");
                }
                writeSubdescriminators(logger, writer, subtype, instanceName, type);
                BeanResolver sub = findType(subtype);
                subtypes.add(sub);
                writeClassSerializer(logger.branch(Type.INFO, "Writing child serializer "+sub.getType().getQualifiedSourceName()), context,sub);

            }
        } else {
            logger.log(Type.INFO, type.getType().getQualifiedSourceName()+" is not subclassed");
        }

        writeSerializer(logger, writer, type);
        writeDeserializer(logger, writer, type);


        HashSet<BeanResolver> childrenCopy = new HashSet<BeanResolver>(this.children);
        this.children.clear();

        for (BeanResolver child : childrenCopy) {
            if(child == null || includedCodecs.contains(child.getType().getQualifiedSourceName())){
                continue;
            }
            writer.println(" private static final " +
                    JSONCodec.class.getCanonicalName() + "<" +
                    child.getType().getQualifiedSourceName() + "> CODEC_" +
                    child.getType().getQualifiedSourceName().replaceAll("\\.", "_") +
                    " = GWT.create(" + child.getType().getQualifiedSourceName() +
                    "_JSONCodec.class);");
            writeClassSerializer(logger.branch(Type.INFO, "Writing included property serializer"), context, child);
            includedCodecs.add(child.getType().getQualifiedSourceName());

        }

        writer.println(" public String getMimeType() { return MIME_TYPE; }");
        writer.println("}"); // close the class

        logger.log(Type.INFO, "Finishing "+classTypeName);
        context.commit(logger, printWriter);
    }

    private void writeSubdescriminators(TreeLogger logger, SourceWriter writer, JClassType subtype, String instanceName, BeanResolver type){
        JSONDiscriminatorValue discriminatorValue = subtype.getAnnotation(JSONDiscriminatorValue.class);
        if(discriminatorValue == null){
            throw new RuntimeException("Class "+subtype.getQualifiedSourceName()+" is a subclass of "+type.getType().getQualifiedSourceName()+" but doesn't have a JSONDisciminiatorValue.");
        }
        logger.log(Type.INFO, (" Writing descriminiator for subtype "+subtype.getQualifiedSourceName()+" "+discriminatorValue.value() +" to instancename "+instanceName));
        writer.print("static { subclasses.put(\""+discriminatorValue.value()+"\", "+instanceName+"); }");
        for(JClassType child : subtype.getSubtypes()){
            if(child.getSuperclass().getQualifiedSourceName().equals(subtype.getQualifiedSourceName())){
                writeSubdescriminators(logger, writer, child, instanceName, type);
            }
        }
    }

    private void writeDeserializer(TreeLogger logger, SourceWriter writer, BeanResolver r) {

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

        String subclassed = findDiscriminatorField(r.getType());
        JSONDiscriminatorValue thisTypeDiscriminator = r.getType().getAnnotation(JSONDiscriminatorValue.class);
        String thisTDString = null;
        if(thisTypeDiscriminator != null){
            thisTDString = thisTypeDiscriminator.value();
        }
        if(subclassed != null){
            logger.log(Type.INFO, "Subclassed "+subclassed);
            writer.println("{");
            writer.println("JSONString discriminator = (JSONString) root.get(\""+subclassed+"\");");
            //writer.println("System.out.println(\"disc: \"+discriminator);");
            writer.println("if(discriminator != null && !discriminator.stringValue().equals(\""+thisTDString+"\")) return ( "+r.getType().getQualifiedSourceName()+") subclasses.get(discriminator.stringValue()).deserializeFromJSONObject(root);");
            writer.println("}");
        }
        if(r.getType().isAbstract() || r.getType().isInterface() != null){
            writer.println("throw new IllegalArgumentException(\"Abstract type.\");");
        } else {
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
        }
        writer.println("} catch (Exception e) { ");
        writer.indent();
        writer.println("throw new SerializationException(e);");
        writer.outdent();
        writer.println("}");
        writer.outdent();
        writer.println("}");
    }

    private String findDiscriminatorField(JClassType type){
        JSONSubclassed subclassed = type.getAnnotation(JSONSubclassed.class);
        if(subclassed != null){
            return subclassed.discriminator();
        }

        return type.getSuperclass() == null ? null : findDiscriminatorField(type.getSuperclass());
    }

    private void writeSerializer(TreeLogger logger, SourceWriter writer, BeanResolver r) {
        writer.println("public JSONObject serializeToJSONObject( " +
                r.getType().getQualifiedSourceName() +
                " source ) throws SerializationException { ");
        writer.indent();
        logger.log(Type.INFO, "WRITE SERIALIZER " + r.getType() + " " + r.getType().getAnnotation(JSONSubclassed.class) + " " + r.getType().getAnnotation(JSONDiscriminatorValue.class));
        if(r.getType().getAnnotation(JSONSubclassed.class) != null || r.getType().getAnnotation(JSONDiscriminatorValue.class) != null){
            logger.log(Type.INFO, " Checking subtypes "+r.getType().getQualifiedSourceName()+" "+this.subtypes.size());
            for(BeanResolver subtype : this.subtypes){
                if(!subtype.getType().getSuperclass().getQualifiedSourceName().equals(r.getType().getQualifiedSourceName())){
                    continue;
                }
                writer.print("if(source instanceof "+subtype.getType().getQualifiedSourceName()+") ");
                String instanceName = "CODEC_" + subtype.getType().getQualifiedSourceName().replaceAll("\\.", "_");
                writer.println(" return "+instanceName+".serializeToJSONObject( ("+subtype.getType().getQualifiedSourceName()+") source);");
            }
        }

        writer.println(" JSONObject destination = new JSONObject();");

        JSONDiscriminatorValue discriminatorValue = r.getType().getAnnotation(JSONDiscriminatorValue.class);
        if(discriminatorValue != null){
            writer.println("destination.put(\""+findDiscriminatorField(r.getType())+"\", new JSONString(\""+discriminatorValue.value()+"\"));");
        }
        for (RProperty prop : r.getProperties().values()) {
            if (prop.getName().equals("class") || prop.getReadMethod() == null) {
                continue;
            }

            JSONField field = prop.getReadMethod().getBaseMethod()
                                  .getAnnotation(JSONField.class);
            JSONOmit omit = prop.getReadMethod().getBaseMethod()
                                .getAnnotation(JSONOmit.class);
            //System.out.println(" ws \t "+prop.getName() +" "+ prop.getReadMethod().getBaseMethod().getEnclosingType()+ prop.getReadMethod().getBaseMethod().getReadableDeclaration()  + " "+ omit +" "+field );
            if (omit != null) {
                continue;
            }

            String fieldName = (field == null) ? prop.getName() : field.value();

            if (prop.getReadMethod() != null) {
                JClassType classType = prop.getType().isClassOrInterface();
                JArrayType arrayType = prop.getType().isArray();
                //System.out.println(prop.getName()+ "  ArrayType "+arrayType +" :: "+((arrayType == null ? "" : ""+arrayType.getComponentType())));
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
