/*
 * LogService.java
 *
 * Created on October 5, 2007, 9:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.log.remote;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface LogService extends RemoteService {

    public void log(int level, String logger, String message, String exceptionMessage);
}