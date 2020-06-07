package com.example.concetto.services;

import com.example.concetto.api.v1.mapper.UserMapper;
import com.example.concetto.api.v1.model.UserDTO;
import com.example.concetto.exception.NotFoundException;
import com.example.concetto.models.User;
import com.example.concetto.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()){
            throw new NotFoundException(("User not found."));
        }

        return user.get();
    }

    @Override
    public UserDTO getUserDtoByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()){
            throw new NotFoundException("UserDTO not found.");
        }

        return user.map(userMapper::userToUserDTO).get();
    }



    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        BigInteger exists = userRepository.existsByEmail(email);
        return exists.intValue() != 0; //Convert the BigInteger to a boolean
    }

    /**
     * //TODO: Where should this method live? Here or in UserService or should UserService.
     * //TODO: Not sure I should check if a user exists check every time this method is called, because this is called alot. (via Angular router).
     * <p>
     * Given an email from OAuth2Authentication detail properties. Check to see if a User already exists in the database.
     * If so create a new user.
     *
     * @param email
     * @return true - if a new user was create. false - if a user already exists in the database.
     */
    public boolean createUserIfNotFound(String email) {
        //Check to see if user exits in database.
        if (!existsByEmail(email)) {
            //If  not we create a User in the db.
            User user = new User();
            user.setEmail(email);
            save(user);
            return true;
        }
        return false;
    }

}
