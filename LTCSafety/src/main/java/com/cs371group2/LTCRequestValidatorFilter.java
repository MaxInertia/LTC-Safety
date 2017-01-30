package com.cs371group2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Brandon on 2017-01-29.
 */
public class LTCRequestValidatorFilter implements Filter {
    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ValidationResult.class.getName() );

    //private RequestValidator requestValidator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //requestValidator = new RequestValidator(ApiKeys.TWILIO_AUTH_TOKEN);
        System.out.println("Ayyy");
        LOGGER.log(Level.WARNING, "Filtering reached!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Ayyy");
        LOGGER.log(Level.WARNING, "Filtering reached!");
        boolean isValidRequest = false;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            //TwilioRequestValidator validator = new TwilioRequestValidator(requestValidator);
            //isValidRequest = validator.validate(httpRequest);
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
