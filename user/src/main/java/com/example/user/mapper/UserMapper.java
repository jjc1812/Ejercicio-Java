package com.example.user.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.example.user.model.PhoneModel;
import com.example.user.model.UserModel;

public class UserMapper {
        public static UserModel mapToDTO(ResultSet rs) throws SQLException {
        UserModel userDTO = new UserModel();
        PhoneModel phoneNumber = new PhoneModel();
        if (rs.next()) {
            userDTO.setId(rs.getString("id_user"));

            java.sql.Date createdDate = rs.getDate("created");
            if (createdDate != null) {
                String createdFormatted = dateToString(createdDate);
                userDTO.setCreated(createdFormatted);
            }
            
            java.sql.Date lastLoginDate = rs.getDate("last_login");
            if (lastLoginDate != null) {
                String lastLoginFormatted = dateToString(lastLoginDate);
                userDTO.setLastLogin(lastLoginFormatted);
            }

            userDTO.setToken(rs.getString("token"));
            userDTO.setIsActive(rs.getInt("is_active"));
            userDTO.setName(rs.getString("name"));
            userDTO.setEmail(rs.getString("email"));
            userDTO.setPassword(rs.getString("password"));
            phoneNumber.setNumber(rs.getLong("phone_number"));
            phoneNumber.setCityCode(rs.getInt("phone_city_code"));
            phoneNumber.setCountryCode(rs.getString("phone_country_code"));
            userDTO.setPhone(phoneNumber);
        }
        return userDTO;
    }

    public static UserModel mapToModel (Map<String, Object> requestBody){
        UserModel userDTO = new UserModel();
        PhoneModel phoneNumber = new PhoneModel();
        userDTO.setCreated((String) requestBody.get("created"));
        userDTO.setLastLogin((String) requestBody.get("lastLogin"));
        userDTO.setToken((String) requestBody.get("token"));

        boolean isActive = (boolean) requestBody.get("isActive");
        int isActiveInt = isActive ? 1 : 0;
        userDTO.setIsActive(isActiveInt);

        userDTO.setName((String) requestBody.get("name"));
        userDTO.setEmail((String) requestBody.get("email"));
        userDTO.setPassword((String) requestBody.get("password"));

        Map<String, Object> phoneData = (Map<String, Object>) (requestBody.get("phone"));
        if (phoneData != null) {
            phoneNumber.setNumber((long) (int) phoneData.get("number"));
            phoneNumber.setCityCode((int) phoneData.get("citycode"));
            phoneNumber.setCountryCode((String) phoneData.get("contrycode"));
        }
        userDTO.setPhone(phoneNumber);

        return userDTO;
    }

    private static String dateToString(java.sql.Date date){
        Date datejava = new Date(date.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
        String Formatted = formatter.format(datejava);
        return Formatted;
    }

}
