package com.system.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.exception.WebApplicationErrorMessage;
import com.system.jwt.JwtService;

@Provider
public class RestRequestFilter implements ContainerRequestFilter {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final static List<String> PERMIT_ALL_URI = Arrays.asList("/rest/user/login");
	
	@Autowired
	private JwtService jwtService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String requestPath = requestContext.getUriInfo().getRequestUri().getPath();
		if (!isPermitAllUri(requestPath)) {
			String header = requestContext.getHeaderString(HEADER);
			if (header == null || !header.startsWith(PREFIX)) {
				String msg = "There is no JWT in the request headers";
				requestContext.abortWith(buildResponse(msg, Response.Status.UNAUTHORIZED, requestPath));
			} else {
				String token = header.substring(PREFIX.length());
				if (!jwtService.validateUserJWT(token)) {
					 String msg = "Invalid JWT";
					 requestContext.abortWith(buildResponse(msg, Response.Status.UNAUTHORIZED, requestPath));
				}
			}
		}
	}
	
	private Response buildResponse(String msg, Response.Status statusCode, String path) {
		WebApplicationErrorMessage errorMessage = new WebApplicationErrorMessage(
				new WebApplicationException(msg, statusCode), path);
		return Response.status(statusCode).type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
	}
	
	private boolean isPermitAllUri(String uri) {
		if (PERMIT_ALL_URI.contains(uri)) {
			return true;
		} else {
			return false;
		}
	}
}
