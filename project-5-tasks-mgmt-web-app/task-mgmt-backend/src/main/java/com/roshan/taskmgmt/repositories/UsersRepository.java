package com.roshan.taskmgmt.repositories;

import com.roshan.taskmgmt.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

}
