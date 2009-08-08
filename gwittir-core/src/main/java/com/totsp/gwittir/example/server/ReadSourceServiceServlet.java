package com.totsp.gwittir.example.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.totsp.gwittir.example.client.ReadSourceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ReadSourceServiceServlet extends RemoteServiceServlet implements ReadSourceService {
    /**
     *
     */
    private static final long serialVersionUID = -381949208081439767L;

    public String getSource(String classname) {
        try {
            if (classname.startsWith("class ")) {
                classname = classname.substring(6);
            }

            String path = "/" + classname.replaceAll("\\.", "/") + ".java";
            this.log(path);

            InputStream is = ReadSourceServiceServlet.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb = sb.append(line)
                       .append("\n");
                line = reader.readLine();
            }

            return sb.toString();
        } catch (IOException ioe) {
            this.log("IOException", ioe);
            throw new RuntimeException(ioe);
        }
    }
}
