package com.loginregister.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User userModel) {
        return userRepository.save(userModel);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void delete(User userModel) {
        userRepository.delete(userModel);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
