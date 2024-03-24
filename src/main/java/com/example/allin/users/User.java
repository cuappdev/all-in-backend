package com.example.allin.users;

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
