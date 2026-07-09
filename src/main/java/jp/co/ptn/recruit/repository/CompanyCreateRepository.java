package jp.co.ptn.recruit.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jp.co.ptn.recruit.entity.Mstcompany;
import jp.co.ptn.recruit.entity.Mstcompanyperson;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyCreateRepository {

	private final JdbcTemplate jdbcTemplate;

	// ① mst_companyへの保存（自動採番されたコードを返す）
	public Integer saveCompany(Mstcompany company) {
		String sql = "INSERT INTO mst_company ("
				+ "referral_company_nm, referral_company_kananm, referral_company_tel, referral_company_address, "
				+ "area_tokyo, area_chubu, area_kansai, area_kyusyu, "
				+ "no_experience_flg, experienced_flg, notes, delete_flg, "
				+ "create_user, upd_user_user"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, company.getReferralCompanyNm());
			ps.setString(2, company.getReferralCompanyKananm());
			ps.setString(3, company.getReferralCompanyTel());
			ps.setString(4, company.getReferralCompanyAddress());
			ps.setBoolean(5, company.getAreaTokyo());
			ps.setBoolean(6, company.getAreaChubu());
			ps.setBoolean(7, company.getAreaKansai());
			ps.setBoolean(8, company.getAreaKyusyu());
			ps.setBoolean(9, company.getNoExperienceFlg());
			ps.setBoolean(10, company.getExperiencedFlg());
			ps.setString(11, company.getNotes());
			ps.setInt(12, company.getDeleteFlg());
			ps.setString(13, company.getCreateUser());
			ps.setString(14, company.getUpdUserUser());
			return ps;
		}, keyHolder);

		// 発番された主キー（referral_company_cd）を返す
		return keyHolder.getKey().intValue();
	}

	// ② mst_company_personへの保存
	public void savePerson(Mstcompanyperson person) {
		String sql = "INSERT INTO mst_company_person ("
				+ "person_nm, person_email, person_tel, referral_company_cd, delete_flg, "
				+ "create_user, upd_user_user" 
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?)"; 

		// ⭐ 末尾に getCreateUser() と getUpdateUser() を追加する
		jdbcTemplate.update(sql,
				person.getPersonNm(),
				person.getPersonEmail(),
				person.getPersonTel(),
				person.getReferralCompanycd(),
				person.getDeleteFlg(),
				person.getCreateUser(), 
				person.getUpdUserUser() 
		);
	}
}