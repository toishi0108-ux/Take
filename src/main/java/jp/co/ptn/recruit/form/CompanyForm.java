package jp.co.ptn.recruit.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CompanyForm {
	// ===== 紹介会社情報 =====
    /** 紹介会社名 */
	@Size(min=1,max=100,message="100文字以内で入力してください。")
    @NotBlank(message = "紹介会社名を入力してください。")
    private String CompanyNm;
    /** 紹介会社カナ */
	@Size(min=1,max=100,message="100文字以内で入力してください。")
	@NotBlank(message = "紹介会社カナ名を入力してください。")
    private String CompanyKananm;
    /** 紹介会社電話番号 */
	@Size(min=1,max=11,message="11文字以内で入力してください。")
    @NotBlank(message = "紹介会社電話番号を入力してください。")
    private String CompanyTel;
    /** 紹介会社住所 */
	@Size(min=1,max=200,message="200文字以内で入力してください。")
	@NotBlank(message = "紹介会社住所を入力してください。")
    private String CompanyAddress;
	
    /** 担当地域 */
    private Boolean areaTokyo;
    private Boolean areaChubu;
    private Boolean areaKansai;
    private Boolean areaKyusyu;
    // 「全国」にチェックを付ける場合
    private Boolean areaAll;
    
    // ===== 担当者情報 =====
    /** 担当者名 */
    @NotBlank(message = "担当者名を入力してください。")
    @Size(min=1,max=40,message="40文字以内で入力してください。")
    private String personNm;
    /** メールアドレス */
    @Email(message = "メールアドレスの形式が正しくありません。")
    @NotBlank(message = "メールアドレスを入力してください。")
    @Size(min=1,max=64,message="64文字以内で入力してください。")
    private String personEmail;
    /** 担当者電話番号 */
    @NotBlank(message = "担当者電話番号を入力してください。")
    @Size(min=1,max=11,message="11文字以内で入力してください。")
    private String personTel;
    
    private Integer personNo;
    
    // ===== 経験区分 =====
    private Boolean noExperienceFlg;
    private Boolean experiencedFlg;
    // ===== 備考 =====//
    private String notes;
}
