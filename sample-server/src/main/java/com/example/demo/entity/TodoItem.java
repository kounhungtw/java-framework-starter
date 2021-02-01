package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.system.entity.BasePojo;

@Entity
@Table(name="to_do_item")
public class TodoItem extends BasePojo {
	
	private Boolean isChecked;
	
	@Nationalized
    private String content;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="list_id", updatable=false, nullable=false, foreignKey=@ForeignKey(name="fk_to_do_list_id"))
    private TodoList todoList;

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

	public TodoList getTodoList() {
		return todoList;
	}

	public void setTodoList(TodoList todoList) {
		this.todoList = todoList;
	}
}
