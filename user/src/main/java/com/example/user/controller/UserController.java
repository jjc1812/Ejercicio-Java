package com.example.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import com.example.user.mapper.UserMapper;
import com.example.user.model.ResponseError;
import com.example.user.model.UserModel;
import com.example.user.service.UserService;
import com.nimbusds.oauth2.sdk.ErrorResponse;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<UserModel> getUserbyID(@RequestBody Map<String, Object> requestBody) {
        if (requestBody.containsKey("id_user")) {
            String id = (String) requestBody.get("id_user");
            UserModel user = userService.getUserById(id);
    
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> requestBody) {
        List<ResponseError.ErrorDetails> errorList = new ArrayList<>();
        try {
            UserModel newUser = UserMapper.mapToModel(requestBody);
            UserModel user = userService.postUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            int codigo = HttpStatus.BAD_REQUEST.value();
            String detail = "Invalid request: " + e.getMessage();
            ResponseError.ErrorDetails errorDetails = new ResponseError.ErrorDetails(timestamp, codigo, detail);

            errorList.add(errorDetails);
            ResponseError errorResponse = new ResponseError(errorList);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
