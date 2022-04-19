package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "select u from User u where u.id = :id and u.isRemoved = false", nativeQuery = false)
    Optional<User> findById(@Param("id") Long id);
}
