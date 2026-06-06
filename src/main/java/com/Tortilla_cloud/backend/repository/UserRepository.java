package com.Tortilla_cloud.backend.repository;

import com.Tortilla_cloud.backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User , Long> {
    User findByUsername(String username);
}
