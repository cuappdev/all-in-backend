package com.example.allin.user;

public class User {

    // id integer [primary key, increment]
    private Long id;
    // user email string [not null]
    private String email;
    // user_password string [not null]
    private String hashed_password;
    // user image string
    private String image;
    // user balance double [not null]
    private double balance;
    // user session token string [not null]
    private String session_token;
    // user refresh token string [not null]
    private String refresh_token;
    // whether or not user is admin boolean [not null]
    private boolean is_admin;

    public User() {  }

    public User(String email, String hasned_password, String image, double balance, 
      String session_token, String refresh_token, boolean is_admin) {
        this.email = email;
        this.hashed_password = hasned_password;
        this.image = image;
        this.balance = balance;
        this.session_token = session_token;
        this.refresh_token = refresh_token;
        this.is_admin = is_admin;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void set(String email) {
        this.email = email;
    }

    public String getPassword() {
        return hashed_password;
    }

    public void setPassword(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(double balance) {
        this.balance = balance;
    }

    public String getSessionToken() {
        return session_token;
    }

    public void setSessionToken(String session_token) {
        this.session_token = session_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public boolean getAdmin() {
        return is_admin;
    }

    public void setAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    @Override
    public String toString() {
        return "user(id=" + this.id + ", email=" + this.email + ", password=" 
        + this.hashed_password + ", image=" + this.image + ", balance=" + this.balance 
        + ", session_token=" + this.session_token + ", refresh_token=" + this.refresh_token 
        + ", is_admin=" + this.is_admin + ")";
    }
}


/**
 * package com.example.allin.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;;

@Entity
@Table(name = "user")
public class User {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "hashed_password", nullable = false)
    private String hashed_password;

    @Column(name = "image")
    private String image;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Column(name = "session_token", nullable = false)
    private String session_token;

    @Column(name = "refresh_token", nullable = false)
    private String refresh_token;

    @Column(name = "is_admin", nullable = false)
    private boolean is_admin;

    public User() {  }

    public User(String email, String hasned_password, String image, double balance) {
        
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

 */