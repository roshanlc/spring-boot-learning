package com.roshan.taskmgmt.controllers;

import com.roshan.taskmgmt.entities.Task;
import com.roshan.taskmgmt.entities.TaskStatus;
import com.roshan.taskmgmt.entities.Users;
import com.roshan.taskmgmt.repositories.TaskRepository;
import com.roshan.taskmgmt.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    private TaskRepository taskRepository;
    private UsersRepository usersRepository;

    public TaskController(TaskRepository taskRepository, UsersRepository usersRepository) {
        this.taskRepository = taskRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/home")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Welcome, " + authentication.getName();
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> tasksHandler() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(taskRepository.findAllByUsers_Email(email));
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTaskHandler(@RequestBody Task task) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> user = usersRepository.findByEmail(email);
        // set task to null
        user.get().setTask(null);
        task.setUsers(user.get());
        var saved = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
