package com.coffeeandsoftware.api.services;

import com.coffeeandsoftware.api.dto.UserDTO;
import com.coffeeandsoftware.api.model.User;
import com.coffeeandsoftware.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setU_name(userDTO.getName());
        user.setPhotoURL(userDTO.getPhotoURL());

        return userRepository.save(user);
    }

    public User getUserById(UUID id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isPresent()) user = u.get();
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
