package jp.co.ptn.recruit.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Mstcompanyperson {
	
	private Integer personNo;
	
	private String personNm;
	
	private String personEmail;
	
	private String personTel;
	
	private Integer referralCompanycd;
	
	private LocalDateTime createDate;
	
	private String createUser;
	
	private LocalDateTime updateDate;
	
	private String updUserUser;
	
	private Integer deleteFlg;

}
