/*
 * ServerLogService.java
 *
 * Created on October 5, 2007, 11:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.service;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface ServerLogService {

    public void log( int level, String logger, String message, String exceptionMessage );


}
