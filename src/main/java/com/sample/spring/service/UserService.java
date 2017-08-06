package com.sample.spring.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.spring.entity.User;
import com.sample.spring.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<User> selectAll() {
		return userRepository.findAll(new Sort(Sort.Direction.ASC, "userId"));
	}

	public void insert(List<User> userList) {
		userRepository.save(userList);
	}

	public void delete(int userId) {
		userRepository.delete(userId);
	}

	/*
	 * csvを読み込みリストを返却する
	 */
	public List<String[]> csvReader(String filePath) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			// ファイルを読み込む
			File file = new File(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "SJIS");
			BufferedReader br = new BufferedReader(inputStreamReader);
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

	/*
	 * file をバックアップする
	 */
	public void renameFile(String fromFilePath) {
		File toFile = new File(fromFilePath);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String toFilePath = fromFilePath + "." + sdf.format(new Date());
		toFile.renameTo(new File(toFilePath));
	}

}