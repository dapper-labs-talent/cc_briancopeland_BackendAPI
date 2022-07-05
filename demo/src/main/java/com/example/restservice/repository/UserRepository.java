package com.example.restservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.restservice.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
	List<User> findAll();
}