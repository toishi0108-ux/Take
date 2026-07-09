package jp.co.ptn.recruit.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ptn.recruit.entity.Mstcompany;
import jp.co.ptn.recruit.entity.Mstcompanyperson;
import jp.co.ptn.recruit.form.Mst_CompanyForm;
import jp.co.ptn.recruit.repository.Mst_CompanyRepository;
import jp.co.ptn.recruit.repository.Mst_Company_PersonRepository;
import lombok.RequiredArgsConstructor;

/**
 * 紹介会社情報および担当者情報の更新処理を行うサービスクラスです。
 */
@Service
@RequiredArgsConstructor
public class UpdateService {

    /** 紹介会社情報リポジトリ */
    private final Mst_CompanyRepository mstCompanyRepository;

    /** 紹介会社担当者情報リポジトリ */
    private final Mst_Company_PersonRepository mstCompanyPersonRepository;

    /**
     * 紹介会社情報および担当者情報を更新します。
     *
     * @param form 画面から入力された紹介会社情報
     * @param loginUser 更新を実行したログインユーザー
     */
    @Transactional
    public void updateCompany(Mst_CompanyForm form, String loginUser) {

        // 既存の紹介会社情報を取得
        Mstcompany company =
                mstCompanyRepository.findById(form.getReferralCompanyCd());

        // 作成日時・作成者が未設定の場合は設定
        if (company.getCreateDate() == null) {
            company.setCreateDate(LocalDateTime.now());
            company.setCreateUser(loginUser);
        }

        // 画面で入力された内容をエンティティへ設定
        company.setReferralCompanyCd(form.getReferralCompanyCd());
        company.setReferralCompanyNm(form.getReferralCompanyNm());
        company.setReferralCompanyKananm(form.getReferralCompanyKananm());
        company.setReferralCompanyTel(form.getReferralCompanyTel());
        company.setAreaTokyo(form.getAreaTokyo());
        company.setAreaChubu(form.getAreaChubu());
        company.setAreaKansai(form.getAreaKansai());
        company.setAreaKyusyu(form.getAreaKyusyu());
        company.setReferralCompanyAddress(form.getReferralCompanyAddress());
        company.setNoExperienceFlg(form.getNoExperienceFlg());
        company.setExperiencedFlg(form.getExperiencedFlg());
        company.setNotes(form.getNotes());

        // 更新日時・更新者などの共通項目を設定
        company.setUpdateDate(LocalDateTime.now());
        company.setUpdUserUser(loginUser);
        company.setDeleteFlg(0);

        // 紹介会社情報を更新
        mstCompanyRepository.update(company, loginUser);

        // 担当者情報を設定
        Mstcompanyperson person = new Mstcompanyperson();
        person.setPersonNo(form.getPersonNo());
        person.setPersonNm(form.getPersonNm());
        person.setPersonEmail(form.getPersonEmail());
        person.setPersonTel(form.getPersonTel());
        person.setReferralCompanycd(form.getReferralCompanyCd());

        // 担当者情報を更新
        mstCompanyPersonRepository.updatePerson(person, loginUser);
    }
}