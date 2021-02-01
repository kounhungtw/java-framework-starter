package com.example.demo.dto;

import java.util.Date;

import com.example.demo.entity.TodoItem;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class TodoItemDTO {

	private Integer id;
	
	private Boolean isChecked;
	
    private String content;
    
    private Date createdDtm;
    
    @JsonBackReference
    private TodoListDTO todoList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDtm() {
		return createdDtm;
	}

	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}

	public TodoListDTO getTodoList() {
		return todoList;
	}

	public void setTodoList(TodoListDTO todoList) {
		this.todoList = todoList;
	}
	
	public TodoItem convertDTO2Pojo() {
		TodoItem pojo = new TodoItem();
		pojo.setId(id);
		pojo.setIsChecked(isChecked);
		pojo.setContent(content);
		pojo.setCreatedDtm(createdDtm);
		return pojo;
	}
	
	public static TodoItemDTO convertPojo2DTO(TodoItem pojo) {
		TodoItemDTO dto = new TodoItemDTO();
		dto.setId(pojo.getId());
		dto.setIsChecked(pojo.getIsChecked());
		dto.setContent(pojo.getContent());
		dto.setCreatedDtm(pojo.getCreatedDtm());
		return dto;
	}
}
