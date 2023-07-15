package com.example.user.DAO;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.user.mapper.UserMapper;
import com.example.user.model.ResponseUser;
import com.example.user.model.UserModel;

@Component
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserModel getUserById(String id){
        String sql = "SELECT * FROM poo.user WHERE id_user = '"+id+"';";
        String updateSql = "UPDATE user SET last_login = ?, token = ? WHERE id_user = ?";
        UserModel user = jdbcTemplate.query(sql, (ResultSet rs) -> UserMapper.mapToDTO(rs));

        if (user != null) {        
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date lastLoginDate;

            try {
                lastLoginDate = dateFormat.parse(user.getLastLogin());
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format for 'DATE'");
            }
            jdbcTemplate.update(updateSql, lastLoginDate, user.getToken(), user.getId());
        }

        return user;
    }

    public ResponseUser insertUser(UserModel userMap) {
        String sql = "INSERT INTO user (id_user, created, last_login, token, is_active, name, email, password, phone_number, phone_city_code, phone_country_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
        if (userMap.getId() == null || userMap.getCreated() == null || userMap.getLastLogin() == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date createdDate;
        java.util.Date lastLoginDate;

        try {
            createdDate = dateFormat.parse(userMap.getCreated());
            lastLoginDate = dateFormat.parse(userMap.getLastLogin());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format for 'DATE'");
        }

        Timestamp createdTimestamp = new Timestamp(createdDate.getTime());
        Timestamp lastLoginTimestamp = new Timestamp(lastLoginDate.getTime());

        Object[] params = {
            userMap.getId(),
            createdTimestamp,
            lastLoginTimestamp,
            userMap.getToken(),
            userMap.getIsActive() ? 1 : 0,
            userMap.getName(),
            userMap.getEmail(),
            userMap.getPassword(),
            userMap.getPhone().getNumber(),
            userMap.getPhone().getCityCode(),
            userMap.getPhone().getCountryCode()
        };
            
        try {
            jdbcTemplate.update(sql, params);
                
            ResponseUser responseUser = new ResponseUser();
            responseUser.setIdUser(userMap.getId());
            responseUser.setCreated(userMap.getCreated());
            responseUser.setLastLogin(userMap.getLastLogin());
            responseUser.setToken(userMap.getToken());
            responseUser.setIsActive(userMap.getIsActive());
                
            return responseUser;

        } catch (DataAccessException e) {
            throw new RuntimeException("Error inserting user into the database", e);
        }
    }
    
}
