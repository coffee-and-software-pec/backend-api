package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.dto.UserDTO;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPhotoURL(userDTO.getPhotoURL());

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
