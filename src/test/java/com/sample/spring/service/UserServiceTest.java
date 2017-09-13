package com.sample.spring.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.sample.spring.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSource")
public class UserServiceTest {

	@Autowired
	UserService userService;

	// @Autowired
	// WebApplicationContext wac;
	// MockMvc mockMvc;

	@Before
	public void 事前処理() throws Exception {
		// mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@DatabaseSetup("/com/sample/spring/service/001_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/service/001_setup.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase("/com/sample/spring/service/001_expected.xml")
	public void Test_SelectAllUsers() throws Exception {
		// 遷移を確認する
		// mockMvc.perform(get("/user/userList"))
		// .andExpect(status().isOk())
		// .andExpect(model().hasNoErrors());
	}

	@Test
	@DatabaseSetup("/com/sample/spring/service/002_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/service/002_setup.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase("/com/sample/spring/service/002_expected.xml")
	public void Test_Delete() throws Exception {
		userService.delete(1);
	}

	@Test
	@DatabaseSetup("/com/sample/spring/service/003_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/service/003_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void Test_Insert() throws Exception {
		// ユーザデータ登録
		List<User> userList =  new ArrayList<User>();
		setUpUserList(userList, "テスト四郎", "男", "テスト部門");
		setUpUserList(userList, "テスト五郎", "男", "テスト部門");

		userService.insert(userList);

		List<User> userListAfter = userService.selectAllUsers();
		assertEquals(userListAfter.size(), 5);
	}

	@Test
	public void Test_CsvToStringList() throws Exception{
		String testCsvPath = "C:\\Program Files\\pleiades\\workspace\\spring_sample\\csv\\test\\input\\user_Test_CsvToStringList.csv";
		List<String[]> userFileList = userService.csvToStringList(testCsvPath);

		assertEquals(userFileList.size(), 2);
		assertThat(userFileList.get(0), arrayContaining("テスト部門","テスト四郎", "男"));
		assertThat(userFileList.get(1), arrayContaining("テスト部門","テスト五郎", "男"));
	}

	@Test
	public void Test_CsvOutPut() throws Exception{
		String fileRootPath = "C:\\Program Files\\pleiades\\workspace\\spring_sample\\csv\\test\\output";
		// TODO
	}

	private void setUpUserList(List<User> userList, String userName, String sex, String bumon) {
		User user = new User();
		user.setUserName(userName);
		user.setSex(sex);
		user.setBumon(bumon);
		userList.add(user);
	}
}
