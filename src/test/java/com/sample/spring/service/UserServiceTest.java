package com.sample.spring.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
import com.sample.spring.properties.CsvProperties;
import com.sample.spring.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dataSource")
public class UserServiceTest {

	@Autowired
	UserService service;

	@Autowired
	private CsvProperties properties;

	@Autowired
	private TestUtils utils;

	@After
	public void 事後処理() {
		String fileRootPath = properties.getOutputPath() + "\\";
		File[] files = utils.listCsvPaths(fileRootPath);
		for (File file : files) {
			file.delete();
		}
	}

	@Test
	@DatabaseSetup("/com/sample/spring/service/001_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/service/001_setup.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase("/com/sample/spring/service/001_expected.xml")
	public void Test001_SelectAllUsers() throws Exception {
	}

	@Test
	@DatabaseSetup("/com/sample/spring/service/002_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/service/002_setup.xml", type = DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase("/com/sample/spring/service/002_expected.xml")
	public void Test002_Delete() throws Exception {
		service.delete(1);
	}

	@Test
	@DatabaseSetup("/com/sample/spring/service/003_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/service/003_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void Test003_Insert() throws Exception {
		// ユーザデータ登録
		List<User> userList = new ArrayList<User>();
		addUserList(userList, "テスト四郎", "男", "テスト部門");
		addUserList(userList, "テスト五郎", "男", "テスト部門");

		service.insert(userList);

		List<User> userListAfter = service.selectAllUsers();
		assertEquals(userListAfter.size(), 5);
	}

	@Test
	public void Test004_CsvToStringList() throws Exception {
		String testCsvPath = properties.getInputPath() + "\\" + properties.getUser();
		List<String[]> userFileList = service.csvToStringList(testCsvPath);

		assertEquals(userFileList.size(), 2);
		assertThat(userFileList.get(0), arrayContaining("テスト部門", "テスト四郎", "男"));
		assertThat(userFileList.get(1), arrayContaining("テスト部門", "テスト五郎", "男"));
	}

	@Test
	@DatabaseSetup("/com/sample/spring/service/005_setup.xml")
	@DatabaseTearDown(value = "/com/sample/spring/service/005_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void Test005_ExportUserCsvEachBumon() throws Exception {
		String fileRootPath = properties.getOutputPath() + "\\";
		service.exportUserCsvEachBumon(fileRootPath);
		File[] files = utils.listCsvPaths(fileRootPath);
			assertEquals(files.length, 3);
			userCsvChecker(files[0].toString(), 0, "1", "流通・システムサービス開発第1部", "宮元　武蔵", "男");
			userCsvChecker(files[1].toString(), 0, "2", "流通・システムサービス開発第2部", "佐藤　次郎", "男");
			userCsvChecker(files[2].toString(), 0, "3", "流通・システムサービス開発第3部", "山田　花子", "女");
	}

	private void userCsvChecker(String filePath, int listIndex, String id, String bumon, String name, String sex) {
		List<String[]> userFileList = service.csvToStringList(filePath);
		String[] line = userFileList.get(listIndex);
		assertEquals(line[0], id);
		assertEquals(line[1], bumon);
		assertEquals(line[2], name);
		assertEquals(line[3], sex);
	};

	private void addUserList(List<User> userList, String userName, String sex, String bumon) {
		User user = new User();
		user.setUserName(userName);
		user.setSex(sex);
		user.setBumon(bumon);
		userList.add(user);
	}
}
