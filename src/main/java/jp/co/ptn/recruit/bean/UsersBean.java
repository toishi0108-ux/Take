package jp.co.ptn.recruit.bean;

import lombok.Data;

@Data
public class UsersBean {
	private Integer userName;

	private Integer userId;

	// メールアドレス
	private String email;

	// パスワード
	private String personPassword;
}