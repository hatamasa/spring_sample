package com.sample.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}