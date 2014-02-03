package com.totsp.gwittir.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the JSONCodec should read the value of "discriminator" from
 * the incoming JSON to select a subclass of this for desierialization.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONSubclassed {
    String discriminator();
}
