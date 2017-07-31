package com.sample.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.spring.entity.User;
import com.sample.spring.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService service;

	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public String selectAll(Model model) {
		List<User> userList = service.selectAll();
		model.addAttribute("userList", userList);
		return "user/selectAll";
	}
}