package jp.co.ptn.recruit.dto;

import lombok.Data;

@Data
public class CompanyRowDto {
	private Integer companyCd;
	private String companyNm;
	private String companyKanaNm;
	private String personNm;
	private String companyTel;
	private Boolean areaTokyo;
	private Boolean areaChubu;
	private Boolean areaKansai;
	private Boolean areaKyusyu;
	private String companyAddress;
	private Boolean noExperienceFlg;
	private Boolean experiencedFlg;
	private Boolean deleteFlg;
	private String notes;
}