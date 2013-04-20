/*
 * LogServiceAsync.java
 *
 * Created on October 5, 2007, 10:05 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.log.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface LogServiceAsync {
    public void log( int level, String logger, String message, String exceptionMessage, AsyncCallback callback );
    
}
