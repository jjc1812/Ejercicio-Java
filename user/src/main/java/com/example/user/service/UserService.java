package com.example.user.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.DTO.UserDTO;
import com.example.user.model.UserModel;

@Service
public class UserService {

    @Autowired
    private UserDTO userDTO;

    public UserModel getUserById(String id){
        UserModel user = userDTO.getUserById(id);
        return user;
    }

    public UserModel postUser(UserModel newUser){
        if (isValidEmail(newUser.getEmail())) {
            return newUser;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
}
