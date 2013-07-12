Binding
=======

This is a system for doing two way databinding on objects that (a) are @Introspectable and (b) implement
SourcesPropertyChangeEvents or can be adapted to do so (that is, they implement SourcesChangeEvents, or
are simply introspectable and will allow read/write but not change notifications.

Introduction
------------
The BindingBuilder is a utility class to simplify building of a large binding structure. It follows a quasi-"literate programming" format to make you binding code easy to read and understand.

Part of the historical problem with using Gwittir's bindings is you end up with a lot of ugly code. The constructors are large and complicated and even for an experienced developer can require a great deal of referring to the docs. The BindingBuilder simplifies this by providing a clear order for configuring bindings. It also provides a basis for automatically including adapters in the binding operation without the developer having to worry about them.

Getting Started
---------------

The BindingBuilder exposes a number of static methods that can be used directly or imported as static. The general pattern works like this:

    Binding b =  BindingBuilder.bind(leftObject])
        .onLeftProperty("firstName")
        .toRight(rightObject])
        .onRightProperty("firstName")
        // When done:
        .toBinding();
    //You can also replace toBinding() with .and() to do additional bindings:

        .and()
        .bindLeft(johnny)
        .onLeftProperty("firstName")
        .toRight(delaney)
        .onRightProperty("firstName")
        .convertRightWith(
            new Converter<String, String>() {
                public String convert(String original) {
                    return original.toUpperCase();
                }
            })
         .toBinding();

At each set, the method calls return a limited interface that only exposes the next possible steps.
This makes IDE autocomplete support much more useful.

The important thing to remember is when you are appending bindings, to finish with .toBinding().
This commits the binding being constructed and performs the append.

The binding builder supports Converters and Validators and all the other features of the binding
system in a much easier to use form.

