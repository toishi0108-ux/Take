package jp.co.ptn.recruit.entity;

import lombok.Data;

@Data
public class Company {
    private Long id; 
    private String companyNm;
    private String companyKanaNm;
    private String companyTel;
    private String companyAddress;
    private boolean areaTokyo;
    private boolean areaChubu;
    private boolean areaKansai;
    private boolean areaKyusyu;
    private String personNm;
    private String email;
    private boolean noExperienceFlg;
    private boolean experiencedFlg;
    private String notes;
    private boolean deleteFlg = false;
}