/*
 * Logger.java
 *
 * Created on October 5, 2007, 9:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.totsp.gwittir.client.log.remote.LogService;
import com.totsp.gwittir.client.log.remote.LogServiceAsync;
import java.util.HashMap;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public abstract class Logger {
    private static final LogServiceAsync SERVICE = (LogServiceAsync) GWT.create( LogService.class );
    private static final HashMap LOGGERS = new HashMap();
    private static final String ANON = "Anon" + Math.random();
    private static final AsyncCallback CALLBACK = new AsyncCallback(){
        public void onSuccess(Object result) {
        }
        
        public void onFailure(Throwable caught) {
            GWT.log( "Unable to log to remote serivce.", caught );
            
        }
        
    };
    
    static{
        ((ServiceDefTarget) SERVICE).setServiceEntryPoint( GWT.getModuleBaseURL()+"LogService");
    }
    
    private String name;
    
    /** Creates a new instance of Logger */
    protected Logger() {
        super();
    }
    
    public static Logger getLogger(String name){
        if( name == null ){
            throw new NullPointerException("Logger name cannot be null.");
        }
        Logger logger = (Logger) LOGGERS.get(name);
        if( logger == null ){
            logger = (Logger) GWT.create( Logger.class );
            logger.name = name;
            LOGGERS.put( name, logger );
        }
        return logger;
    }
    
    public static Logger getAnonymousLogger(){
        return getLogger( Logger.ANON );
    }
    
    public void log( final int level, final String message, Throwable caught ){
        if( level <= this.getMaxLevel() ){
            if( !GWT.isScript() ){
                String line = "[";
                switch( level ){
                    case Level.ERROR:
                        line+= "ERROR";
                        break;
                    case Level.WARN:
                        line+= "WARN";
                        break;
                    case Level.INFO:
                        line+= "INFO";
                        break;
                    case Level.DEBUG:
                        line+= "DEBUG";
                        break;
                    case Level.SPAM:
                        line+= "SPAM";
                        break;
                    default:
                        break;
                }
                line +="] "+ this.name+" "+ message;
                GWT.log( line, caught );
            } else {
            String caughtString = caught == null ? null : caught.toString();
            while( caught !=null && caught.getCause() != null ){
                caughtString +="\n caused by: " + caught.getCause().toString();
                caught = caught.getCause();
            }
            Logger.SERVICE.log( level, this.name, message, caughtString, Logger.CALLBACK );
            }
        }
    }
    
    protected abstract int getMaxLevel();
}
