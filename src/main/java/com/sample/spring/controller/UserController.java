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

	@RequestMapping(value = "/userList")
	public String selectAll(Model model) {
		// userFilePath配下にあるcsvファイルの中身を表示する
		String userCsvPath = properties.getInputPath() + "\\" + properties.getUser();
		List<String[]> userFileList = service.csvToStringList(userCsvPath);
		model.addAttribute("userFileList", userFileList);

		// userテーブルのユーザを表示する
		List<User> userList = service.selectAllUsers();
		model.addAttribute("userList", userList);
		return "user/userList";
	}

	@RequestMapping(value = "/insert")
	public String insert(Model model) {
		String filePath = properties.getInputPath() + "\\" + properties.getUser();
		List<String[]> list = service.csvToStringList(filePath);
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
		return "forward:userList";
	}

	@RequestMapping(value = "/outPutCsv")
	public String outPutCsv(Model model) {
		String fileOutputRootPath = properties.getOutputPath() + "\\";
		// 出力処理を実装
		service.csvOutPut(fileOutputRootPath);
		return "forward:userList";
	}

	@RequestMapping(value = "/DeleteUser")
	public String deleteUser(Model model, @RequestParam int userId) {
		service.delete(userId);
		return "forward:userList";
	}

}