package com.sample.spring.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection="dataSource")
public class UserServiceTest {

	@Autowired
	UserService userService;

	@Test
	@DatabaseSetup("/com/sample/spring/service/001_setup.xml")
	@DatabaseTearDown(value="/com/sample/spring/service/001_setup.xml",type=DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase("/com/sample/spring/service/001_expected.xml")
	public void testSelectAll() throws Exception {
		}

	@Test
	@DatabaseSetup("/com/sample/spring/service/002_setup.xml")
	@DatabaseTearDown(value="/com/sample/spring/service/002_setup.xml",type=DatabaseOperation.DELETE_ALL)
	@ExpectedDatabase("/com/sample/spring/service/002_expected.xml")
	public void testDelete() throws Exception {
		userService.delete(1);
		}

	@Test
	@DatabaseSetup("/com/sample/spring/service/003_setup.xml")
	@DatabaseTearDown(value="/com/sample/spring/service/003_setup.xml",type=DatabaseOperation.DELETE_ALL)
	public void testInsert() throws Exception {
		ArrayList<User> userList = new ArrayList<User>();
		User user = new User();
		user.setUserName("テスト四郎");
		user.setSex("男");
		user.setBumon("テスト部門");
		userList.add(user);
		userService.insert(userList);

		List<User> userList2 = userService.selectAll();
		assertEquals(userList2.size(), 4);
		}
}
