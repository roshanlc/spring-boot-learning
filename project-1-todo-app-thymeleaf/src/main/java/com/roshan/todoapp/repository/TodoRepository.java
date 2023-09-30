package com.roshan.todoapp.repository;

import com.roshan.todoapp.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public class TodoRepository extends JpaRepository<TodoItem, Long> {
}
