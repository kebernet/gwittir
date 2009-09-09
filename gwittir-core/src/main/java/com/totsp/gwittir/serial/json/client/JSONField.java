/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.serial.json.client;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation that can be placed on the getter method for a property to customize
 * its JSON field value.
 * @author kebernet
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONField {
    String value();
}
