package com.cs371group2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HTTP Filter for ensuring authorized-access only
 * Created on 2017-01-29.
 */
public class LTCRequestValidatorFilter implements Filter {
    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ValidationResult.class.getName() );

    /**
     * Initializes the filter
     *
     * @param filterConfig Configuration for the filter
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //LOGGER.log(Level.WARNING, "Filtering initialized!");
    }

    /**
     * Performs filtering in a chain by validating firebase tokens to ensure authorized access
     *
     * @param request The servlet request
     * @param response The servlet response
     * @param chain The next filter in the chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //LOGGER.log(Level.WARNING, "doFilter reached!");

        boolean isValidRequest = false;

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            //TODO VALIDATE THE REQUEST HERE AND SET isValidRequest TO THE RESULT
        }

        if(isValidRequest) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
        // Nothing to do
    }
}
