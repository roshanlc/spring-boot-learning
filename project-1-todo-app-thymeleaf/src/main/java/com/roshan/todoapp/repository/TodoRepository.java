package com.roshan.todoapp.repository;

import com.roshan.todoapp.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {

}
