package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TodoList;

public interface TodoListRepository extends JpaRepository<TodoList, Integer> {
	@EntityGraph(attributePaths = { "todoItemList" })
	Optional<TodoList> findWithTodoItemById(Integer id);
}