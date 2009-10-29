/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.rest.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author kebernet
 */
public class RESTProxyServlet extends HttpServlet {
    /** "X-Proxy-Location" */
    public static final String X_PROXY_LOCATION_HEADER = "X-Proxy-Location";

    /** "X-REST-Method" */
    public static final String X_REST_METHOD_HEADER = "X-REST-Method";
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * Copies the data from an InputStream object to an OutputStream object.
     *
     * @param sourceStream
     *            The input stream to be read.
     * @param destinationStream
     *            The output stream to be written to.
     * @return int value of the number of bytes copied.
     * @exception IOException
     *                from java.io calls.
     */
    public static int copyStream(InputStream sourceStream, OutputStream destinationStream)
        throws IOException {
        int bytesRead = 0;
        int totalBytes = 0;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

        while (bytesRead >= 0) {
            bytesRead = sourceStream.read(buffer, 0, buffer.length);

            if (bytesRead > 0) {
                destinationStream.write(buffer, 0, bytesRead);
            }

            totalBytes += bytesRead;
        }

        destinationStream.flush();
        destinationStream.close();

        return totalBytes;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
        try {
            String proxyUrl = request.getHeader(X_PROXY_LOCATION_HEADER);

            if (proxyUrl == null) {
                throw new ServletException("No " + X_PROXY_LOCATION_HEADER + " header present.");
            }

            String method = request.getHeader(proxyUrl);

            if (method == null) {
                method = request.getMethod();
            }

            URL url = new URL(proxyUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            boolean doInput = method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT");
            connection.setDoInput(doInput);
            connection.setDoOutput(true);
            connection.setRequestMethod(method.toUpperCase());

            // Duplicate headers
            for (Enumeration enu = request.getHeaderNames(); enu.hasMoreElements();) {
                String headerName = (String) enu.nextElement();

                if (headerName.equals(X_REST_METHOD_HEADER) || headerName.equals(X_PROXY_LOCATION_HEADER)) {
                    continue;
                }

                connection.setRequestProperty(
                    headerName,
                    request.getHeader(headerName));
            }

            if (doInput) {
                copyStream(
                    request.getInputStream(),
                    connection.getOutputStream());
            }

            response.setStatus(connection.getResponseCode());

            //Pass back headers
            Map<String, List<String>> responseHeaders = connection.getHeaderFields();

            for (Entry<String, List<String>> entry : responseHeaders.entrySet()) {
                response.setHeader(
                    entry.getKey(),
                    concatComma(entry.getValue()));
            }

            copyStream(
                connection.getInputStream(),
                response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(RESTProxyServlet.class.getName())
                  .log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    private static String concatComma(List<String> strings) {
        Iterator<String> it = strings.iterator();
        StringBuilder sb = new StringBuilder(it.next());

        while (it.hasNext()) {
            sb = sb.append(",")
                   .append(it.next());
        }

        return sb.toString();
    }
}
