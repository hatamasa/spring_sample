package com.sample.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.spring.entity.User;
import com.sample.spring.properties.CsvProperties;
import com.sample.spring.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private CsvProperties properties;

	@RequestMapping(value = "/selectAll")
	public String selectAll(Model model) {
		String filePath = properties.getInputPath() + "\\" + properties.getUser();
		model.addAttribute("inputList", service.csvReader(filePath));
		model.addAttribute("userList", service.selectAll());
		return "user/selectAll";
	}

	@RequestMapping(value = "/insert")
	public String insert(Model model) {
		String filePath = properties.getInputPath() + "\\" + properties.getUser();
		List<String[]> list = service.csvReader(filePath);
		ArrayList<User> userList = new ArrayList<User>();
		for (String[] strings : list) {
			User user = new User();
			user.setBumon(strings[0]);
			user.setUserName(strings[1]);
			user.setSex(strings[2]);
			userList.add(user);
		}
		service.insert(userList);
		service.renameFile(filePath);
		return "redirect:http://localhost:8080/user/selectAll";
	}

	@RequestMapping(value = "/outPutCsv")
	public String outPutCsv(Model model) {
		// 出力処理を実装

		return "redirect:http://localhost:8080/user/selectAll";
	}

	@RequestMapping(value = "/DeleteUser")
	public String deleteUser(Model model, @RequestParam int userId) {
		service.delete(userId);
		return "redirect:http://localhost:8080/user/selectAll";
	}

}