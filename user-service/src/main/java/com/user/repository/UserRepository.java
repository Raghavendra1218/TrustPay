package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	User findByUserName(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByUserNameAndPassword(String userName, String password);

}
