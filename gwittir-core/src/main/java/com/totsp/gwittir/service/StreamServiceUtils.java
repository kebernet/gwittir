/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.service;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamReader;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamWriter;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author kebernet
 */
public class StreamServiceUtils {
    private static final HashMap<String, Class<?>> TYPE_NAMES;

    /**
     * Maps primitive wrapper classes to their corresponding primitive class.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS = new HashMap<Class<?>, Class<?>>();

    /**
     * Static map of classes to sets of interfaces (e.g. classes). Optimizes
     * lookup of interfaces for security.
     */
    private static Map<Class<?>, Set<String>> serviceToImplementedInterfacesMap;

    static {
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Boolean.class, Boolean.TYPE);
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Byte.class, Byte.TYPE);
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Character.class, Character.TYPE);
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Double.class, Double.TYPE);
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Float.class, Float.TYPE);
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Integer.class, Integer.TYPE);
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Long.class, Long.TYPE);
        PRIMITIVE_WRAPPER_CLASS_TO_PRIMITIVE_CLASS.put(Short.class, Short.TYPE);

        TYPE_NAMES = new HashMap<String, Class<?>>();
        TYPE_NAMES.put("Z", boolean.class);
        TYPE_NAMES.put("B", byte.class);
        TYPE_NAMES.put("C", char.class);
        TYPE_NAMES.put("D", double.class);
        TYPE_NAMES.put("F", float.class);
        TYPE_NAMES.put("I", int.class);
        TYPE_NAMES.put("J", long.class);
        TYPE_NAMES.put("S", short.class);

        serviceToImplementedInterfacesMap = new HashMap<Class<?>, Set<String>>();
    }

    public static RPCRequest decodeRequest(
        String encodedRequest, Class<?> type, SerializationPolicyProvider serializationPolicyProvider) {
        if (encodedRequest == null) {
            throw new NullPointerException("encodedRequest cannot be null");
        }

        if (encodedRequest.length() == 0) {
            throw new IllegalArgumentException("encodedRequest cannot be empty");
        }

        ClassLoader classLoader = Thread.currentThread()
                                        .getContextClassLoader();

        try {
            ServerSerializationStreamReader streamReader = new ServerSerializationStreamReader(
                    classLoader, serializationPolicyProvider);
            streamReader.prepareToRead(encodedRequest);

            // Read the name of the RemoteService interface
            String serviceIntfName = streamReader.readString();

            //            if (type != null) {
            //                if (!implementsInterface(type, serviceIntfName)) {
            //                    // The service does not implement the requested interface
            //                    throw new IncompatibleRemoteServiceException(
            //                        "Blocked attempt to access interface '" +
            //                        serviceIntfName + "', which is not implemented by '" +
            //                        printTypeName(type) +
            //                        "'; this is either misconfiguration or a hack attempt");
            //                }
            //            }
            SerializationPolicy serializationPolicy = streamReader.getSerializationPolicy();

            //            Class<?> serviceIntf;

            //            try {
            //                serviceIntf = getClassFromSerializedName(serviceIntfName,
            //                        classLoader);
            //
            //                if (!RemoteService.class.isAssignableFrom(serviceIntf)) {
            //                    // The requested interface is not a RemoteService interface
            //                    throw new IncompatibleRemoteServiceException(
            //                        "Blocked attempt to access interface '" +
            //                        printTypeName(serviceIntf) +
            //                        "', which doesn't extend RemoteService; this is either misconfiguration or a hack attempt");
            //                }
            //            } catch (ClassNotFoundException e) {
            //                throw new IncompatibleRemoteServiceException(
            //                    "Could not locate requested interface '" + serviceIntfName +
            //                    "' in default classloader", e);
            //            }
            String serviceMethodName = streamReader.readString();

            int paramCount = streamReader.readInt();
            Class<?>[] parameterTypes = new Class[paramCount];

            for (int i = 0; i < parameterTypes.length; i++) {
                String paramClassName = streamReader.readString();

                try {
                    parameterTypes[i] = getClassFromSerializedName(paramClassName, classLoader);
                } catch (ClassNotFoundException e) {
                    throw new IncompatibleRemoteServiceException(
                        "Parameter " + i + " of is of an unknown type '" + paramClassName + "'", e);
                }
            }

            try {
                Method method = type.getMethod(serviceMethodName, parameterTypes);

                Object[] parameterValues = new Object[parameterTypes.length];

                for (int i = 0; i < parameterValues.length; i++) {
                    parameterValues[i] = streamReader.deserializeValue(parameterTypes[i]);
                }

                return new RPCRequest(method, parameterValues, serializationPolicy);
            } catch (NoSuchMethodException e) {
                throw new IncompatibleRemoteServiceException(
                    formatMethodNotFoundErrorMessage(type, serviceMethodName, parameterTypes));
            }
        } catch (SerializationException ex) {
            throw new IncompatibleRemoteServiceException(ex.getMessage(), ex);
        }
    }

    //
    /**
    * Returns a string that encodes the results of an RPC call. Private overload
    * that takes a flag signaling the preamble of the response payload.
    *
    * @param object the object that we wish to send back to the client
    * @param wasThrown if true, the object being returned was an exception thrown
    *          by the service method; if false, it was the result of the service
    *          method's invocation
    * @return a string that encodes the response from a service method
    * @throws SerializationException if the object cannot be serialized
    */
    public static String encodeResponse(
        Class<?> responseClass, Object object, boolean wasThrown, SerializationPolicy serializationPolicy)
        throws SerializationException {
        ServerSerializationStreamWriter stream = new ServerSerializationStreamWriter(serializationPolicy);

        stream.prepareToWrite();

        if (responseClass != void.class) {
            stream.serializeValue(object, responseClass);
        }

        String bufferStr = (wasThrown ? "//EX"
                                      : "//OK") + stream.toString();

        return bufferStr;
    }

    /**
    * Returns the {@link Class} instance for the named class or primitive type.
    *
    * @param serializedName the serialized name of a class or primitive type
    * @param classLoader the classLoader used to load {@link Class}es
    * @return Class instance for the given type name
    * @throws ClassNotFoundException if the named type was not found
    */
    private static Class<?> getClassFromSerializedName(String serializedName, ClassLoader classLoader)
        throws ClassNotFoundException {
        Class<?> value = TYPE_NAMES.get(serializedName);

        if (value != null) {
            return value;
        }

        return Class.forName(serializedName, false, classLoader);
    }

    private static String formatMethodNotFoundErrorMessage(
        Class<?> serviceIntf, String serviceMethodName, Class<?>[] parameterTypes) {
        StringBuffer sb = new StringBuffer();

        sb.append("Could not locate requested method '");
        sb.append(serviceMethodName);
        sb.append("(");

        for (int i = 0; i < parameterTypes.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }

            sb.append(printTypeName(parameterTypes[i]));
        }

        sb.append(")'");

        sb.append(" in interface '");
        sb.append(printTypeName(serviceIntf));
        sb.append("'");

        return sb.toString();
    }

    /**
    * Used to determine whether the specified interface name is implemented by
    * the service class. This is done without loading the class (for security).
    */
    private static boolean implementsInterface(Class<?> service, String intfName) {
        synchronized (serviceToImplementedInterfacesMap) {
            // See if it's cached.
            //
            Set<String> interfaceSet = serviceToImplementedInterfacesMap.get(service);

            if (interfaceSet != null) {
                if (interfaceSet.contains(intfName)) {
                    return true;
                }
            } else {
                interfaceSet = new HashSet<String>();
                serviceToImplementedInterfacesMap.put(service, interfaceSet);
            }

            if (!service.isInterface()) {
                while ((service != null) && !RemoteServiceServlet.class.equals(service)) {
                    Class<?>[] intfs = service.getInterfaces();

                    for (Class<?> intf : intfs) {
                        if (implementsInterfaceRecursive(intf, intfName)) {
                            interfaceSet.add(intfName);

                            return true;
                        }
                    }

                    // did not find the interface in this class so we look in the
                    // superclass
                    //
                    service = service.getSuperclass();
                }
            } else {
                if (implementsInterfaceRecursive(service, intfName)) {
                    interfaceSet.add(intfName);

                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Only called from implementsInterface().
     */
    private static boolean implementsInterfaceRecursive(Class<?> clazz, String intfName) {
        assert (clazz.isInterface());

        if (clazz.getName()
                     .equals(intfName)) {
            return true;
        }

        // search implemented interfaces
        Class<?>[] intfs = clazz.getInterfaces();

        for (Class<?> intf : intfs) {
            if (implementsInterfaceRecursive(intf, intfName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Straight copy from
     * {@link com.google.gwt.dev.util.TypeInfo#getSourceRepresentation(Class)} to
     * avoid runtime dependency on gwt-dev.
     */
    private static String printTypeName(Class<?> type) {
        // Primitives
        //
        if (type.equals(Integer.TYPE)) {
            return "int";
        } else if (type.equals(Long.TYPE)) {
            return "long";
        } else if (type.equals(Short.TYPE)) {
            return "short";
        } else if (type.equals(Byte.TYPE)) {
            return "byte";
        } else if (type.equals(Character.TYPE)) {
            return "char";
        } else if (type.equals(Boolean.TYPE)) {
            return "boolean";
        } else if (type.equals(Float.TYPE)) {
            return "float";
        } else if (type.equals(Double.TYPE)) {
            return "double";
        }

        // Arrays
        //
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();

            return printTypeName(componentType) + "[]";
        }

        // Everything else
        //
        return type.getName()
                   .replace('$', '.');
    }
}
