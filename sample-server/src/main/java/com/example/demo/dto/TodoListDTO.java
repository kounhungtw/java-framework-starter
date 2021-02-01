package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.TodoItem;
import com.example.demo.entity.TodoList;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class TodoListDTO {
	
	private Integer id;
	
    private String name;
    
    private Date createdDtm;
    
    @JsonManagedReference
    private List<TodoItemDTO> todoItemList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDtm() {
		return createdDtm;
	}

	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}

	public List<TodoItemDTO> getTodoItemList() {
		return todoItemList;
	}

	public void setTodoItemList(List<TodoItemDTO> todoItemList) {
		this.todoItemList = todoItemList;
	}
    
	public TodoList convertDTO2Pojo() {
		TodoList pojo = new TodoList();
		pojo.setId(id);
		pojo.setName(name);
		pojo.setCreatedDtm(createdDtm);
		
		List<TodoItem> todoItemList = new ArrayList<>();
		if (getTodoItemList() != null) { // convert todoItemDTO to pojo
			todoItemList = getTodoItemList().stream().map(i -> i.convertDTO2Pojo())
					.collect(Collectors.toList());
		}
		
		for (TodoItem item : todoItemList) {
			item.setTodoList(pojo); // set a back reference from ToDoItem(Child) to ToDoList(Parent)
		}
		pojo.setTodoItemList(todoItemList);
		
		return pojo;
	}
	
	public static TodoListDTO convertPojo2DTO(TodoList pojo) {
		if (pojo == null) {
			return null;
		}
		TodoListDTO dto = new TodoListDTO();
		dto.setId(pojo.getId());
		dto.setName(pojo.getName());
		dto.setCreatedDtm(pojo.getCreatedDtm());
		List<TodoItemDTO> todoItemDTOList = new ArrayList<>();
		if (pojo.getTodoItemList() != null) {
			todoItemDTOList = pojo.getTodoItemList().stream().map(i -> TodoItemDTO.convertPojo2DTO(i)).collect(Collectors.toList());
		}
		dto.setTodoItemList(todoItemDTOList);
		return dto;
	}
}
