Introspection
=============


Gwittir's introspection module allows you to access Java Beans style properties from GWT code. This is a subset of the
Beans and Java Reflection APIs and is named so as to avoid confusion -- it doesn't pretend to be an emulation layer.
However, you can use the Gwittir introspection from your regular Java SE/EE code as well, as it will fall down to using
the native beans and reflection APIs to continue to work.

Usage
-----

The simplest way to use the introspector is to annotate a class with the @Introspectable annotation. This will cause the
metadata for that class to be generated at compile time. Any properties you don't wish to be introspectalbe you can
annotate with the @Omit annotation.

The another way you can make a class introspectable is to include it in the /gwittir-introspection.properties file. This
is useful for classes where you can't alter the source code. This is a properties file in the format of {FQN}={comma
separated list of properties} for exmaple:

    com.totsp.gwittir.client.testmodel.TestFileDeclaredIntrospection=stringProperty,intProperty

Finally you can also have your class implement the SelfDescribed interface. This requires your class implement the:

    BeanDescriptor __descriptor();

method so that each instance can provide its own metadata to the introspector.

To access the metadata about a class you might do something like:

    BeanDescriptor db = Instrospector.INSTANCE.getDescriptor(myObjectInstance);
    String firstName = (String) db.getProperty("firstName")
                                 .getAccessorMethod()
                                 .invoke(myObjectInstance, null);

Pro Tip
-------

When generating the metadata, Gwittir is pretty intelligent about polymorphism. If you can, it is best to make sure
you have some common interfaces around your classes with common property sets. That is, HasName with firstName and
lastName if you have multiple classes (User, Contact, etc) or a common base class like Person, will can save you
a lot of space in your final output size. Even if HasName is not, itself, introspectable, Gwittir will find it as the
common base class for your introspectable classes. Also, classes that are not reachabe will be successfully pruned from the
metadata, so don't worry about introspectable classes in library code you are not directly using. It will not affect your
application.

