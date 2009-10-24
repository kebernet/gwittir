/*
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.totsp.gwittir.rest.server;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/** This filter looks for X-REST-Method headers and adapts the Request for use with standard REST services.
 *
 * @author <a href="mailto:kebernet@gmail.com">Robert Cooper</a>
 */
public class XRESTFilter implements Filter {

    /** "X-REST-Method" */
    public static final String X_REST_METHOD_HEADER = "X-REST-Method";


    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String xRestMethod = request.getHeader(X_REST_METHOD_HEADER);
        if(xRestMethod != null){
            request = new RequestWrapper(request);
        }
        chain.doFilter(request, res);
    }

    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static class RequestWrapper extends HttpServletRequestWrapper {

        private HttpServletRequest request;
        RequestWrapper(HttpServletRequest request ){
            super(request);
            this.request = request;
        }

        @Override
        public String getMethod(){
            return request.getHeader(X_REST_METHOD_HEADER);
        }

    }

}
