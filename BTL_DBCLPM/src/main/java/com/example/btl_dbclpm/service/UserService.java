package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.User;
import com.example.btl_dbclpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public List<String> getAllFullNames() {
        return userRepository.findAllFullNames();
    }
}
