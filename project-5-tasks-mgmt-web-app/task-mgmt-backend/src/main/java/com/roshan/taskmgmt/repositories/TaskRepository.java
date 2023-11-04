package com.roshan.taskmgmt.repositories;

import com.roshan.taskmgmt.entities.Task;
import com.roshan.taskmgmt.entities.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByUsers_Email(String email);
    Task findByUsers_EmailAndId(String email, long taskId);
    List<Task> findAllByUsers_EmailAndStatus(String email, TaskStatus status);
}
