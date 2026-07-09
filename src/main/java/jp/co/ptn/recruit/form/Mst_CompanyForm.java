package jp.co.ptn.recruit.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Mst_CompanyForm {

    private Integer referralCompanyCd;
    
    @Size(min=1,max=100,message="100文字以内で入力してください。")
    @NotBlank(message = "紹介会社名を入力してください。")
    private String referralCompanyNm;
    
    @Size(min=1,max=100,message="100文字以内で入力してください。")
	@NotBlank(message = "紹介会社カナ名を入力してください。")
    private String referralCompanyKananm;
    
    @Size(min=1,max=11,message="11文字以内で入力してください。")
    @NotBlank(message = "紹介会社電話番号を入力してください。")
    private String referralCompanyTel;
 
    @Size(min=1,max=200,message="200文字以内で入力してください。")
	@NotBlank(message = "紹介会社住所を入力してください。")
    private String referralCompanyAddress;
    
    private Boolean areaTokyo = false;
    private Boolean areaChubu = false;
    private Boolean areaKansai = false;
    private Boolean areaKyusyu = false;
    
    @NotBlank(message = "担当者名を入力してください。")
    @Size(min=1,max=40,message="40文字以内で入力してください。")
    private String personNm;
    
    @Email(message = "メールアドレスの形式が正しくありません。")
    @NotBlank(message = "メールアドレスを入力してください。")
    @Size(min=1,max=64,message="64文字以内で入力してください。")
    private String personEmail;
    
    @NotBlank(message = "担当者電話番号を入力してください。")
    @Size(min=1,max=11,message="11文字以内で入力してください。")
    private String personTel;
    
    private Integer personNo;
    
    private Boolean noExperienceFlg = false;
    private Boolean experiencedFlg = false;

    @Size(max = 300)
    private String notes;
}
