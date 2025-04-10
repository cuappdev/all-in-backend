package com.appdev.allin.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
  Optional<User> findByUid(String uid);

  User findByUsername(String username);

  User findByEmail(String email);
}
