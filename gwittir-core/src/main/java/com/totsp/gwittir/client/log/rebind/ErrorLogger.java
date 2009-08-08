/*
 * ErrorLogger.java
 *
 * Created on October 5, 2007, 11:20 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.log.rebind;

import com.totsp.gwittir.client.log.Level;
import com.totsp.gwittir.client.log.Logger;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class ErrorLogger extends Logger {
    protected ErrorLogger() {
        super();
    }

    protected int getMaxLevel() {
        return Level.ERROR;
    }
}
