package com.sample.spring.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class TestDataSourceConfig {
	private final String URL = "jdbc:mysql://localhost:3306/SPRING_TEST";
	private final String USERNAME = "TEST";
	private final String PASSWORD = "test";
	private final String DRIVERCLASSNAME = "com.mysql.jdbc.Driver";
	private final DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

	TestDataSourceConfig() {
		this.dataSource.setDriverClassName(DRIVERCLASSNAME);
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
