package jp.co.ptn.recruit.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Mstcompany {
	
	private Integer referralCompanyCd; 

    private String referralCompanyNm;

    private String referralCompanyKananm;

    private String referralCompanyTel;

    private Boolean areaTokyo;

    private Boolean areaChubu;

    private Boolean areaKansai;

    private Boolean areaKyusyu;

    private String referralCompanyAddress;

    private Boolean noExperienceFlg;

    private Boolean experiencedFlg;

    private String notes;

    private LocalDateTime createDate;
    
    private String createUser;

    private LocalDateTime updateDate;
    
    private String updUserUser;

    private Integer deleteFlg;

}
