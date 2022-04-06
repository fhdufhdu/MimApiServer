package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
