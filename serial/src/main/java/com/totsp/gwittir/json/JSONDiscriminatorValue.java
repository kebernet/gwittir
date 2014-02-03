package com.totsp.gwittir.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Sets the value read from the JSONSubclassed descriminator for the class.
 */
@Target({ElementType.TYPE})
public @interface JSONDiscriminatorValue {
    String value();
}
