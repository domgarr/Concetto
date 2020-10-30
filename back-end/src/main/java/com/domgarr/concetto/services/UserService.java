package com.domgarr.concetto.services;

import com.domgarr.concetto.api.v1.model.UserDTO;
import com.domgarr.concetto.models.User;

public interface UserService {
    UserDTO getUserDtoByEmail(String email);
    User getUserByEmail(String email);

    User save(User user);

    boolean exists(Long id);

    boolean existsByEmail(String email);

    boolean createUserIfNotFound(String email);

}
