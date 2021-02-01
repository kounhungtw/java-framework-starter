package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TodoListDTO;
import com.example.demo.entity.TodoItem;
import com.example.demo.entity.TodoList;
import com.example.demo.repository.TodoListRepository;
import com.system.enums.ErrorMessageEnum;
import com.system.exception.BusinessException;
import com.system.exception.ResourceNotFoundException;

@Service
public class TodoListService {
	
	@Autowired
	private TodoListRepository todoListRepository;
	
	private TodoList get(Integer id) throws ResourceNotFoundException {
		return todoListRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cannot find TodoList with id [%d]", id)));
	}
	
	private TodoList getWithTodoItem(Integer id) throws ResourceNotFoundException {
		return todoListRepository.findWithTodoItemById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cannot find TodoList with id [%d]", id)));
	}
	
	private boolean validate(TodoList todoList) {
		if (todoList.getId() == null
				&& todoList.getTodoItemList().stream().filter(i -> i.getId() != null).count() != 0) {
			return false;
		} else {
			return true;
		}
	}

	public TodoListDTO saveTodoList(TodoListDTO todoListDTO) {
		/* Convert DTO to POJO */
		TodoList todoList = todoListDTO.convertDTO2Pojo(); // convert todoListDTO to pojo
		
		/* Update common info */
		todoList.updateCommonInfo();
		
		/* Save TodoList along with TodoItem */
		TodoList savedTodoList = todoListRepository.save(todoList);
		
		/* Convert POJO to DTO */
		TodoListDTO savedTodoListDTO = TodoListDTO.convertPojo2DTO(savedTodoList);
		
		return savedTodoListDTO;
	}

	public TodoListDTO getTodoListById(Integer id) throws ResourceNotFoundException {
		TodoList todoList = getWithTodoItem(id);
		TodoListDTO todoListDTO = TodoListDTO.convertPojo2DTO(todoList);
		return todoListDTO;
	}
	
	// TODO Fix the bug: Cannot update version field in TodoItem
	public TodoListDTO updateTodoList(TodoListDTO todoListDTO) throws ResourceNotFoundException, BusinessException {
		/* Convert DTO to POJO */
		TodoList newTodoList = todoListDTO.convertDTO2Pojo();
		
		if (validate(newTodoList) == false) {
			throw new BusinessException(ErrorMessageEnum.Update_A_New_Parent_That_References_Existing_Children,
					TodoList.class.getSimpleName(), TodoItem.class.getSimpleName());
		}
		
		/* Get the TodoList from db */
		TodoList todoListFromDb = todoListDTO.getId() != null ? get(todoListDTO.getId()) : new TodoList();
		
		/* Update properties */
		todoListFromDb.setName(newTodoList.getName());
		for (TodoItem item : newTodoList.getTodoItemList()) {
			item.setTodoList(todoListFromDb); // set a back reference from TodoItem(Child) to TodoList(Parent)
		}
		todoListFromDb.setTodoItemList(newTodoList.getTodoItemList());
		
		/* Update common info */
		todoListFromDb.updateCommonInfo();
		
		/* Save TodoList along with TodoItem */
		TodoList savedTodoList = todoListRepository.save(todoListFromDb);
		
		/* Convert POJO to DTO */
		TodoListDTO savedTodoListDTO = TodoListDTO.convertPojo2DTO(savedTodoList);
		
		return savedTodoListDTO;
	}
	
	public void deleteTodoList(Integer id) {
		todoListRepository.deleteById(id);
	}
}
