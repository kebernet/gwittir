/*
 * InfoLogger.java
 *
 * Created on October 5, 2007, 11:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.log.rebind;

import com.totsp.gwittir.mvc.log.Level;
import com.totsp.gwittir.mvc.log.Logger;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class InfoLogger extends Logger{
    
    /** Creates a new instance of InfoLogger */
    public InfoLogger() {
      super();
    }

    protected int getMaxLevel() {
        return Level.INFO;
    }
}