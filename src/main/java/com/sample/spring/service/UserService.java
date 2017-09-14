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

	public List<User> selectAllUsers() {
		return userRepository.findAll(new Sort(Sort.Direction.ASC, "userId"));
	}

	public void insert(List<User> userList) {
		userRepository.save(userList);
	}

	public void delete(int userId) {
		userRepository.delete(userId);
	}

	/*
	 * csvを読み込み、リストで返却する
	 * 注意：区切り文字は「,」のファイルであること
	 */
	public List<String[]> csvToStringList(String filePath) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			// filePath配下のcsvを読み込む
			File file = new File(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "SJIS");
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line;
			while ((line = br.readLine()) != null) {
				String[] tmpList = line.split(",");
				list.add(tmpList);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * dbのユーザを部門ごとのcsvに出力する
	 */
	public void exportUserCsvEachBumon(String fileRootPath) {
		List<User> userList = userRepository.findAll();

		// 部門を抽出する
		HashMap<String, String> bumonMap = createBumonMap(userList);

		String bumon = null;
		String exportFilePath = null;
		BufferedWriter bw = null;
		// 部門ごとにCSVを出力する
		for(Entry<String, String> entry :bumonMap.entrySet()){
			bumon = entry.getValue();
			exportFilePath = fileRootPath + bumon + nowDateStr() + ".csv";
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(exportFilePath)), "Windows-31J"));
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
	 * Userの部門一覧を返却する
	 */
	private HashMap<String, String> createBumonMap(List<User> userList){
		HashMap<String, String> bumonMap = new HashMap<String, String>();
		// 部門を抽出する
		for(User user:userList){
			bumonMap.put(user.getBumon(), user.getBumon());
		}
		return bumonMap;
	}

	/*
	 * file をバックアップする
	 */
	public void renameFile(String fromFilePath) {
		File toFile = new File(fromFilePath);

		String toFilePath = fromFilePath + "." + nowDateStr();
		toFile.renameTo(new File(toFilePath));
	}

	/*
	 * 現在日付を返却出力する
	 */
	private String nowDateStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 return sdf.format(new Date());
	}

}