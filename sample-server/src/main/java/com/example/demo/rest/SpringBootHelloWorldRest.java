package com.example.demo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/helloWorld")
public class SpringBootHelloWorldRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response welcome() {
		return Response.ok("Welcome to Spring Boot Hello World").build();
	}

}
