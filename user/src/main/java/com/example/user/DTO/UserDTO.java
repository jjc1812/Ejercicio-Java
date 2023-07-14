package com.example.user.DTO;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.user.mapper.UserMapper;
import com.example.user.model.UserModel;

@Component
public class UserDTO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserModel getUserById(String id){
        String sql = "SELECT * FROM poo.user WHERE id_user = '"+id+"';";
        UserModel user = jdbcTemplate.query(sql, (ResultSet rs) -> UserMapper.mapToDTO(rs));
        return user;
    }
    
}
