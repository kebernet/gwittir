/*
 * DevNullLogger.java
 *
 * Created on November 13, 2007, 2:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.log.rebind;

import com.totsp.gwittir.client.log.Logger;


/**
 *
 * @author rcooper
 */
public class DevNullLogger extends Logger {
    /** Creates a new instance of DevNullLogger */
    public DevNullLogger() {
    }

    protected int getMaxLevel() {
        return Integer.MIN_VALUE;
    }
}
