function ConfirmAndSubmit() {
	ret = confirm("上記を登録します。宜しいですか？");
	if (ret == true) {
		location.href = "http://localhost:8080/user/insert";
	}
}

function OutPutCsvApart() {
	ret = confirm("部門別にCSVを出力します。宜しいですか？");
	if (ret == true) {
		location.href = "http://localhost:8080/user/outPutCsv";
	}
}

function DeleteUser(userId){
	ret = confirm(userId + "を削除します。宜しいですか？");
	if (ret == true) {
		location.href = "http://localhost:8080/user/DeleteUser?userId="+userId;
	}
}
