package com.example.user.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String created;
    private String last_login;
    private String token;
    private boolean is_active;
    private String name;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_number")
    private PhoneModel phone;

        public String getId() {
            return id;
        }
    
        public void setId(String id) {
            this.id = id;
        }

        public String getCreated() {
            return created;
        }
    
        public void setCreated(String created) {
            this.created = created;
        }

        public String getLastLogin() {
            return last_login;
        }
    
        public void setLastLogin(String last_login) {
            this.last_login = last_login;
        }

        public String getToken() {
            return token;
        }
    
        public void setToken(String token) {
            this.token = token;
        }
    
        public boolean getIsActive() {
            return is_active;
        }
    
        public void setIsActive(int is_active) {
            this.is_active = is_active != 0;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getEmail() {
            return email;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    
        public PhoneModel getPhone() {
            return phone;
        }
    
        public void setPhone(PhoneModel phone) {
            this.phone = phone;
        }
        
}
