/*
 * Log4JLogService.java
 *
 * Created on Oct 5, 2007, 4:13:39 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.service;

//import com.totsp.gwittir.service.ServerLogService;
import java.util.Hashtable;
import org.apache.log4j.Logger;
import java.rmi.RemoteException;
import com.totsp.gwittir.client.log.Level;

/**
 *
 * @author rcooper
 */
public class Log4JLogService implements ServerLogService{

    Hashtable<String, Logger> loggers = new Hashtable<String, Logger>();

    public void log(final int level, final String logger, final String message, final String exceptionMessage) {
        Logger log = loggers.get(logger);
        if (log == null) {
            log = Logger.getLogger(logger);
            loggers.put(logger, log);
        }
        switch (level) {
            case Level.ERROR:
                log.error(message, exceptionMessage != null ? new RemoteException(exceptionMessage) : null);
                break;
            case Level.WARN:
                log.warn(message, exceptionMessage != null ? new RemoteException(exceptionMessage) : null);
                break;
            case Level.INFO:
                log.info(message, exceptionMessage != null ? new RemoteException(exceptionMessage) : null);
                break;
            case Level.DEBUG:
                log.debug(message, exceptionMessage != null ? new RemoteException(exceptionMessage) : null);
                break;
            case Level.SPAM:
                log.debug(message, exceptionMessage != null ? new RemoteException(exceptionMessage) : null);
                break;
            default:
                log.fatal(message, exceptionMessage != null ? new RemoteException(exceptionMessage) : null);
        }
    }
}