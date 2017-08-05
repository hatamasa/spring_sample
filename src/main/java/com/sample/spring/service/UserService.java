package com.sample.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.spring.entity.User;
import com.sample.spring.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository repository;

	public List<User> selectAll() {
		return repository.findAll(new Sort(Sort.Direction.ASC, "userId"));
	}

	public void insert(List<User> userList) {
		for (User user : userList) {
			repository.save(user);
		}
	}

}