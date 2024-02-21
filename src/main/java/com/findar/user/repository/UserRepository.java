package com.findar.user.repository;

import com.findar.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select  u from User u join fetch u.role r join fetch r.permissions where u.username= :s")
    Optional<User> fetchUserAndRole(String s);
}
