/*
 * LogServiceServlet.java
 *
 * Created on October 5, 2007, 11:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.totsp.gwittir.client.log.remote.LogService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class LogServiceServlet extends RemoteServiceServlet implements LogService {
    private List<ServerLogService> services;

    /** Creates a new instance of LogServiceServlet */
    public LogServiceServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.log("LogServiceServlet init.");

        Enumeration<URL> enu;

        try {
            enu = LogServiceServlet.class.getClassLoader()
                                         .getResources("META-INF/services/com.totsp.gwittir.service.ServerLogService");

            ArrayList<String> providers = new ArrayList<String>();

            while ((enu != null) && enu.hasMoreElements()) {
                providers.addAll(this.readProviders(enu.nextElement().openStream()));
            }

            ArrayList<ServerLogService> services = new ArrayList();

            for (String className : providers) {
                try {
                    services.add((ServerLogService) Class.forName(className).newInstance());
                } catch (Exception e) {
                    this.log("Exception creating " + className, e);
                }
            }

            this.services = services;
        } catch (IOException ex) {
            this.log("Unable to read ServerLogService providers", ex);
        }
    }

    public void log(int level, String logger, String message, String exceptionMessage) {
        if ((services != null) && (services.size() > 0)) {
            for (ServerLogService service : services) {
                service.log(level, logger, message, exceptionMessage);
            }
        } else {
            this.log(message, new RemoteException(exceptionMessage));
        }
    }

    private List<String> readProviders(InputStream is)
        throws IOException {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        ArrayList<String> providers = new ArrayList<String>();

        while (line != null) {
            if (line.indexOf("#") != -1) {
                line = line.substring(0, line.indexOf("#"));
            }

            line = line.trim();

            if (line.length() > 0) {
                providers.add(line);
                this.log("Found Log SPI " + line);
            }

            line = br.readLine();
        }

        br.close();

        return providers;
    }
}
