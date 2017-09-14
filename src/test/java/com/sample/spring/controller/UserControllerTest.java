package com.sample.spring.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.sample.spring.properties.CsvProperties;
import com.sample.spring.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSource")
public class UserControllerTest {
	@Autowired
	WebApplicationContext wac;
	MockMvc mockMvc;

	@Autowired
	private CsvProperties properties;

	@Autowired
	private TestUtils utils;

	@Before
	public void 事前処理() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@After
	public void 事後処理() {
		String fileRootPath = properties.getOutputPath() + "\\";
		File[] files = utils.listCsvPaths(fileRootPath);
		for (File file : files) {
			file.delete();
		}
	}

	@Test
	public void Test001_SelectAll() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/user/userList"));

		// レスポンスを確認する
		 resultActions.andExpect(status().isOk())
		 	.andExpect(model().hasNoErrors())
		 	.andExpect(content().string(containsString("登録済みユーザ")));
		// ModelMap modelMap =
		// resultActions.andReturn().getModelAndView().getModelMap();
	}

	@Test
	public void Test002_Insert() throws Exception {
//		ResultActions resultActions = mockMvc.perform(get("/user/insert"));
//
//		// レスポンスを確認する
//		 resultActions.andExpect(status().isOk())
//		 	.andExpect(model().hasNoErrors())
//		 	.andExpect(content().string(containsString("登録済みユーザ")));
	}

	@Test
	public void Test003_ExportCsv() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/user/exportCsv"));

		// レスポンスを確認する
		 resultActions.andExpect(status().isOk())
		 	.andExpect(model().hasNoErrors());
	}

	@Test
	@DatabaseSetup("/com/sample/spring/controller/004_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/controller/004_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void Test004_DeleteUser() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/user/exportCsv").param("userId", "1"));

		// レスポンスを確認する
		 resultActions.andExpect(status().isOk())
		 	.andExpect(model().hasNoErrors());
	}
}
