package com.sample.spring.util;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class TestUtils {

	public File[] listCsvPaths(String fileRootPath) {
		File dir = new File(fileRootPath);
		File[] files = dir.listFiles();
		return files;
	}

}
