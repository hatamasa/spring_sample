package com.sample.spring.util;

import java.io.FileOutputStream;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;

public class DTDExporter {
	/** エクスポートするファイル名 */
	private static final String EXPORT_FILENAME = "tables.dtd";
	/** 接続するデータベースへの情報(MySQL用) */
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/SPRING_TEST";
	private static final String JDBC_USER = "TEST";
	private static final String JDBC_PASS = "test";

	public static void main(String[] args) throws Exception {
		// データベースに接続する。
		Class.forName(JDBC_DRIVER);
		IDatabaseConnection con = new DatabaseConnection(DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS));
		// Dtdファイルを作成する
		FlatDtdDataSet.write(con.createDataSet(), new FileOutputStream(EXPORT_FILENAME));
	}
}