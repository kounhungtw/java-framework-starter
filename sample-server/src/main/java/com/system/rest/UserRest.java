package com.system.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.dto.LoginInfoDTO;
import com.system.dto.LoginResultDTO;
import com.system.exception.BusinessException;
import com.system.exception.RequiredFieldEmptyException;
import com.system.exception.ResourceNotFoundException;
import com.system.service.UserService;

@Path("/user")
public class UserRest {
	@Autowired
	private UserService userService;
	
	@POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginInfoDTO loginInfoDTO) {
		LoginResultDTO dto;
		try {
			dto = userService.login(loginInfoDTO);
		} catch (ResourceNotFoundException e) {
			String error = "Cannot find User";
			throw new NotFoundException(error, e);
		} catch (BusinessException | RequiredFieldEmptyException e) {
			throw new WebApplicationException(e.getMessage(), e, 422);
		}
		return Response.ok(dto).build();
	}
}
