package com.roshan.todoapp.services;

import com.roshan.todoapp.models.TodoItem;
import com.roshan.todoapp.repository.TodoRepository;

public class TodoService {

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoItem saveNewTodo(String title, boolean completed){
        return todoRepository.save(new TodoItem(title,completed));
    }

}
