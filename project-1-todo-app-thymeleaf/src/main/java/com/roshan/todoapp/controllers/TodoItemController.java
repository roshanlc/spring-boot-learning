package com.roshan.todoapp.controllers;

import com.roshan.todoapp.models.TodoItem;
import com.roshan.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoItemController {

    private TodoRepository todoRepository;

    // constructor injection
    public TodoItemController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/")
    public String indexPage(Model model) {

        List<TodoItem> todos = new ArrayList<>();
        for(int i =0;i<5;i++){
            todos.add(new TodoItem("Todo item #"+i, false));
        }

        model.addAttribute("todos",todos);
        return "index";
    }


}
