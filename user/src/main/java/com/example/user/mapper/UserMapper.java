package com.example.user.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import java.security.*;

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
            
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = currentDate.format(formatter);

            userDTO.setLastLogin(currentDateTime);

            String token = generateJwtToken();
            userDTO.setToken(token);
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
        
        String token = generateJwtToken();
        UUID userId = UUID.randomUUID();
        userDTO.setId(userId.toString());

        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = currentDate.format(formatter);

        userDTO.setCreated(currentDateTime);
        userDTO.setLastLogin(currentDateTime);

        userDTO.setToken(token);
        userDTO.setIsActive(1);

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

    private static String generateJwtToken() {
        KeyPair keyPair = generateKeyPair();

        String token = Jwts.builder()
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();

        return token;
    }

    private static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            keyPairGenerator.initialize(256, secureRandom);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
