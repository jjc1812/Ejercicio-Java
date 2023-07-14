package com.example.user.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.user.DTO.UserDAO;
import com.example.user.model.ResponseUser;
import com.example.user.model.UserModel;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserModel getUserById(String id){
        UserModel user = userDAO.getUserById(id);
        return user;
    }

    public ResponseUser postUser(UserModel newUser) {
        try {
            validateEmail(newUser.getEmail());
            validatePassword(newUser.getPassword());
            ResponseUser responseUser = userDAO.insertUser(newUser);
            return responseUser;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid format: " + e.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error inserting user into the database", e);
        }
    }
    
    private void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
    
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    private void validatePassword(String password) {
        if (password.length() < 8 || password.length() > 12) {
            throw new IllegalArgumentException("Password length must be between 8 and 12 characters");
        }
    
        int uppercaseCount = 0;
        int numberCount = 0;
    
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isDigit(c)) {
                numberCount++;
            }
        }
    
        if (uppercaseCount != 1 || numberCount < 2) {
            throw new IllegalArgumentException("Invalid password format");
        }
    }    
    
}
