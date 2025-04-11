package com.appdev.allin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Integer countByBalanceGreaterThan(Integer balance);
}
