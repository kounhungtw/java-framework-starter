package com.system.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class RestResponseFilter implements ContainerResponseFilter {

	@Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        responseContext.getHeaders().putSingle("Access-Control-Max-Age", "900");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization, Token, currentLocale");
        
    }

}
