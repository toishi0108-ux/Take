package jp.co.ptn.recruit.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Hstcompanyperson {
	
	private Integer historyId;

    private Integer personNo;

    private String personNm;

    private String personEmail;

    private String personTel;

    private Integer referralCompanyCd;

    private Integer deleteFlg;

    private LocalDateTime createDate;

    private String createUser;

    private LocalDateTime updateDate;

    private String updateUser;

    private LocalDateTime historyCreatedAt;

}
