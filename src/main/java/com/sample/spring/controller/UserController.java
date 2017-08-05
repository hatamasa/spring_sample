package com.sample.spring.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public String selectAll(Model model) {
		String filePath = properties.getInputPath() + "\\" + properties.getUser();
		model.addAttribute("inputList", csvReader(filePath));
		model.addAttribute("userList", service.selectAll());
		return "user/selectAll";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(Model model) {
		String filePath = properties.getInputPath() + "\\" + properties.getUser();
		List<String[]> list = csvReader(filePath);
		ArrayList<User> userList = new ArrayList<User>();
		for (String[] strings : list) {
			User user = new User();
			user.setBumon(strings[0]);
			user.setUserName(strings[1]);
			user.setSex(strings[2]);
			userList.add(user);
			}
			service.insert(userList);
		return "user/selectAll";
	}

	/*
	 * csvを読み込みリストを返却する
	 */
	private List<String[]> csvReader(String filePath) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			// ファイルを読み込む
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			while ((line = br.readLine()) != null) {
				String[] lineList = line.split(",");
				list.add(lineList);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}