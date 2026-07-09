package jp.co.ptn.recruit.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Users {
	
	private Integer userId;
	
	private String userName;
	
	private String email;
	
	private String personPassword;
	
	private Integer loginFailCount;
	
	private Integer accountLockFlg;
	
	private LocalDateTime accountLockTime;
}