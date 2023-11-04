package com.roshan.taskmgmt.controllers;

import com.roshan.taskmgmt.entities.Task;
import com.roshan.taskmgmt.entities.TaskStatus;
import com.roshan.taskmgmt.entities.Users;
import com.roshan.taskmgmt.repositories.TaskRepository;
import com.roshan.taskmgmt.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
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

    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

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

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTaskHandler(@PathVariable(value = "id") long id, @RequestBody Task body) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Task data = taskRepository.findByUsers_EmailAndId(email, id);
        // no task could be found
        if (data == null) {
            throw new EntityNotFoundException("requested task id could not found be found for this user.");
        }

        // modify the task as per the request body
        if(body.getStatus()!=null && !body.getStatus().equals("")){
            // update status
            data.setStatus(body.getStatus());
        }
        if(body.getTitle()!=null && !body.getTitle().equals("")){
            // update title
            data.setTitle(body.getTitle());
        }

        var updated = taskRepository.save(data);
        return ResponseEntity.ok(updated);
    }
    // TODO:
    // 1.[x] Update task
    // 2.[ ] Delete task
    // 3.[ ] Get a single task details (should work for the current user's task only)
    // 4.[] Get tasks based on ongoing, in_progress, done
    // 4.[ ] Rate limiting ????
    // 5.[ ] CORS

}
