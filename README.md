Gwittir
=======

Gwittir is a set of tools for GWT applications. This project represents a refactoring
of the original code into a set of isolated modules that are interdependent. The main
project is Gwittir MVC, which is an MVC-style application framework for your applications
but it is built up from several, now independent, projects that can also be used in isolation.

Introspection
-------------

This is the core API that started the project. This allows limited Java Beans type introspection
within GWT code.

Usage:

    <inherits name="com.totsp.gwittir.Introspection" />

Binding
-------

This is built on the introspection code and allows two way data binding between objects.

Usage:

    <inherits name="com.totsp.gwittir.Binding" />

FX
--

Is built on the introspection framework and has support property animation and other "pretty" effects.

Usage:

    <inherits name='com.totsp.gwittir.FX' />

Serialization
-------------

Uses the introspector to support two way serialization of Java Beans to JSON.

Usage:

    <inherits name="com.totsp.gwittir.JSON" />

Util
----

Utility classes that are independent of the rest of the project.

Usage:

    <inherits name="com.totsp.gwittir.Util" />


Emulation
---------

GWT compatible implementations of core Java classes culled from a number of other sources.

Usage:

    <inherits name="com.totsp.gwittir.Emul"/>

MVC
---------

A set of widget patterns, binding, application flow, etc built from all the other modules.

Usage:

    <inherits name="com.totsp.gwittir.MVC"/>


License
-------

Gwittir is available under your choice of the LGPL v2 or the ASL v2.