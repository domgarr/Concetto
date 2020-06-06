package com.example.concetto.services;

import com.example.concetto.api.v1.model.UserDTO;
import com.example.concetto.models.User;

public interface UserService {
    UserDTO getByEmail(String email);

    User save(User user);

    boolean exists(Long id);

    boolean existsByEmail(String email);

    boolean createUserIfNotFound(String email);

}
