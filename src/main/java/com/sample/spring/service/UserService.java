package com.sample.spring.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
	 * csvを出力する
	 */
	public void csvOutPut(String fileRootPath) {
		List<User> userList = userRepository.findAll();
		HashMap<String, String> bumonMap = new HashMap<String, String>();
		// 部門を抽出する
		for(User user:userList){
			bumonMap.put(user.getBumon(), user.getBumon());
		}
		String bumon = null;
		String filePath = null;
		BufferedWriter bw = null;
		// 部門ごとにCSVを出力する
		for(Entry<String, String> entry :bumonMap.entrySet()){
			bumon = entry.getValue();
			filePath = fileRootPath + bumon + nowDate() + ".csv";
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "Windows-31J"));
				for(User user:userList){
					if(user.getBumon().equals(bumon)){
						bw.write(user.getUserId() + "," + user.getBumon() + "," + user.getUserName() + "," + user.getSex());
						bw.write("\r\n");
					}
				}
				bw.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * file をバックアップする
	 */
	public void renameFile(String fromFilePath) {
		File toFile = new File(fromFilePath);

		String toFilePath = fromFilePath + "." + nowDate();
		toFile.renameTo(new File(toFilePath));
	}

	/*
	 * 現在日付を返却出力する
	 */
	private String nowDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 return sdf.format(new Date());
	}

}