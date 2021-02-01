package com.example.demo.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.demo.dto.TodoListDTO;
import com.example.demo.service.TodoListService;
import com.system.exception.BusinessException;
import com.system.exception.ResourceNotFoundException;

@Path("/todolist")
public class TodoListRest {

	@Autowired
	private TodoListService todoListService;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addToDoList(TodoListDTO todoListDTO) {
		TodoListDTO dto = todoListService.saveTodoList(todoListDTO);
		return Response.ok(dto).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToDoList(@PathParam("id") Integer id) {
		TodoListDTO dto;
		try {
			dto = todoListService.getTodoListById(id);
		} catch (ResourceNotFoundException exception) {
			String errorMessage = "Cannot find TodoList"; // TODO hardcode temporarily
			throw new NotFoundException(errorMessage, exception);
		}
		return Response.ok(dto).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTodoList(TodoListDTO todoListDTO) {
		TodoListDTO dto;
		try {
			dto = todoListService.updateTodoList(todoListDTO);
		} catch (ResourceNotFoundException exception) {
			String errorMessage = "Cannot find TodoList"; // TODO hardcode temporarily
			throw new NotFoundException(errorMessage, exception);
		} catch (BusinessException exception) {
			throw new WebApplicationException(exception, 422); // TODO define an enum for 422
		}
		return Response.ok(dto).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTodoList(@PathParam("id") Integer id) {
        try {
        	todoListService.deleteTodoList(id);
        } catch (EmptyResultDataAccessException exception) {
        	String errorMessage = "Cannot find TodoList"; // TODO hardcode temporarily
        	throw new NotFoundException(errorMessage, exception);
        }
        return Response.noContent().build();
    }
}
