package com.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByLoginId(String loginId);
}
