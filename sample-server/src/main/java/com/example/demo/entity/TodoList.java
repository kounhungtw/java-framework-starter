package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.system.entity.BasePojo;

@Entity
@Table(name="to_do_list")
public class TodoList extends BasePojo {
	
	@Nationalized
    private String name;
    
    @OneToMany(mappedBy="todoList", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<TodoItem> todoItemList = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TodoItem> getTodoItemList() {
		return todoItemList;
	}

	public void setTodoItemList(List<TodoItem> todoItemList) {
		this.todoItemList = todoItemList;
	}
	
	@Override
	public void updateCommonInfo() {
		super.updateCommonInfo();
        todoItemList.stream().forEach(item -> item.updateCommonInfo());
	}
}