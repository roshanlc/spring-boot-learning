package com.roshan.todoapp.controllers;

import com.roshan.todoapp.models.TodoItem;
import com.roshan.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.ClassUtils.isPresent;

@Controller
public class TodoItemController {

    private TodoRepository todoRepository;

    // constructor injection
    public TodoItemController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        List<TodoItem> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "index";
    }

    @PostMapping("/todos")
    public String addTodo(TodoItem todoItem, RedirectAttributes redirectAttributes) {
        try {
            todoRepository.save(todoItem);
            redirectAttributes.addFlashAttribute("message", "The todo has been added!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/todos/{id}/complete")
    public String markCompleted(@PathVariable(value = "id", required = true) Integer id, RedirectAttributes redirectAttributes) {
        if (id <= 0) {
            redirectAttributes.addFlashAttribute("message", "Provide a valid id.");
            return "redirect:/";
        }

        try {
            Optional<TodoItem> temp = todoRepository.findById(id.longValue());

            if (!temp.isPresent()) {
                redirectAttributes.addFlashAttribute("message", "Provide a valid id.");
                return "redirect:/";
            }
            TodoItem todo = temp.get();
            todo.setCompleted(true);
            todoRepository.save(todo);
            redirectAttributes.addFlashAttribute("message", "The todo has been marked as complete!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }


    @GetMapping("/todos/{id}/delete")
    public String removeTodo(@PathVariable(value = "id", required = true) Integer id, RedirectAttributes redirectAttributes) {
        if (id <= 0) {
            redirectAttributes.addFlashAttribute("message", "Provide a valid id.");
            return "redirect:/";
        }

        try {
           boolean isPresent  = todoRepository.existsById(id.longValue());

            if (!isPresent) {
                redirectAttributes.addFlashAttribute("message", "Provide a valid id.");
                return "redirect:/";
            }

            todoRepository.deleteById(id.longValue());
            redirectAttributes.addFlashAttribute("message", "The todo has been deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

}
