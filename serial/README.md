Serial
======

This module is intended as a base for various serialization support based on
Gwittir introspection. Right now it just includes JSON.


Howto
-----

1.  Make your bean @Intrspectable
2.  Define a MyCodec interface that extends JSONCodect<MyClass>
3.  GWT.create(MyCodec.class)
4.  Call serialize/deserialize on the codec class.


Customizing Behavior
--------------------

There are a few annotations you can use to customize the serialization/deserialization.

    @JSONField("fieldName")
    int getSomeField();

Will change the JSON field the someField property is mapped to.

    @JSONOmit
    int getSomeField();

Will omit the someField property from the JSON.

    @Introspectable
    @JSONSubclassed(discriminator="type")
    public class Person {

Will tell the serializer that the JSON field type will determine which subclass of
Person should be deserialized when "type" is present.

    @JSONDiscriminatorValue("employee")
    public class Employee extends Person {

Well tell the serializer that the descriminator value matching the parents discriminator
field in the JSON maps to this class.


