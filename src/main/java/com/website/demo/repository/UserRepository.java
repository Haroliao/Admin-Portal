package com.website.demo.repository;

//data access layer
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	// allows us to use findByUsername in our service or controller to look up users by their username
	Optional<User> findByUsername(String username);
}
