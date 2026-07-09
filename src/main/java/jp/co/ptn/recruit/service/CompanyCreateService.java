package jp.co.ptn.recruit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ptn.recruit.entity.Mstcompany; // ⭕ 小文字のcのentityをインポート
import jp.co.ptn.recruit.entity.Mstcompanyperson;
import jp.co.ptn.recruit.form.CompanyForm;
import jp.co.ptn.recruit.repository.CompanyCreateRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyCreateService {

	private final CompanyCreateRepository repository;

	@Transactional
	public void registerCompany(CompanyForm form) {

		// --- 1. Mstcompany（会社情報）の組み立て ---
		Mstcompany company = new Mstcompany(); // ⭕ クラス名を Mstcompany に変更
		company.setReferralCompanyNm(form.getCompanyNm());
		company.setReferralCompanyKananm(form.getCompanyKananm());
		company.setReferralCompanyTel(form.getCompanyTel());
		company.setReferralCompanyAddress(form.getCompanyAddress());

		// 全国チェックの判定ロジック（Booleanでセット）
		boolean isAll = form.getAreaAll();
		company.setAreaTokyo(isAll || form.getAreaTokyo());
		company.setAreaChubu(isAll || form.getAreaChubu());
		company.setAreaKansai(isAll || form.getAreaKansai());
		company.setAreaKyusyu(isAll || form.getAreaKyusyu());

		company.setNoExperienceFlg(form.getNoExperienceFlg());
		company.setExperiencedFlg(form.getExperiencedFlg());
		company.setNotes(form.getNotes());
		company.setDeleteFlg(0); // 0: 有効
		
		String loginUser = "system"; // ログイン情報が取れなかった時の身代わり
		var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
			loginUser = auth.getName(); // ここに Users の userName が入ります
		}

		// 組み立て中のオブジェクトにセットする
		company.setCreateUser(loginUser);
		company.setUpdUserUser(loginUser);

		// 会社情報を保存し、発行されたコードを取得
		Integer generatedCompanyCd = repository.saveCompany(company);

		// --- 2. MstCompanyPerson（担当者情報）の組み立て ---
		Mstcompanyperson person = new Mstcompanyperson();
		person.setPersonNm(form.getPersonNm());
		person.setPersonEmail(form.getPersonEmail());
		person.setPersonTel(form.getPersonTel());
		person.setReferralCompanycd(generatedCompanyCd); // コードの紐付け
		person.setDeleteFlg(0); // 0: 有効

		// 担当者情報を保存
		repository.savePerson(person);
	}
}