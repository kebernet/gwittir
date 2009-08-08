/*
 * WarnLogger.java
 *
 * Created on October 5, 2007, 11:24 AM
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
public class WarnLogger extends Logger{
    
    /** Creates a new instance of WarnLogger */
    public WarnLogger() {
        super();
    }
    
    protected int getMaxLevel() {
        return Level.WARN;
    }
    
}
