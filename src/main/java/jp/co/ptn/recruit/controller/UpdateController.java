package jp.co.ptn.recruit.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.ptn.recruit.bean.UsersBean;
import jp.co.ptn.recruit.entity.Mstcompany;
import jp.co.ptn.recruit.entity.Mstcompanyperson;
import jp.co.ptn.recruit.form.Mst_CompanyForm;
import jp.co.ptn.recruit.repository.Mst_CompanyRepository;
import jp.co.ptn.recruit.repository.Mst_Company_PersonRepository;
import jp.co.ptn.recruit.service.UpdateService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UpdateController {

	private final UpdateService mstCompanyService;
	private final Mst_CompanyRepository mstCompanyRepository;
	private final Mst_Company_PersonRepository mstCompanyPersonRepository;

	/**
	 * 紹介会社変更内容入力画面を出力します。
	 *
	 * @param companyCd 紹介会社コード
	 * @param mstCompanyForm 紹介会社情報を保持するフォーム
	 * @param model 画面へデータを渡すModel
	 * @return 紹介会社変更内容入力画面
	 */
	@RequestMapping(path = "/company/update/input", method = RequestMethod.GET)
	public String inputUpdate(@RequestParam("companyCd") Integer companyCd,
			@ModelAttribute("mstCompanyForm") Mst_CompanyForm mstCompanyForm,
			Model model) {
		Mstcompany company = mstCompanyRepository.findById(companyCd);

		if (company != null) {
			mstCompanyForm.setReferralCompanyCd(company.getReferralCompanyCd());
			mstCompanyForm.setReferralCompanyNm(company.getReferralCompanyNm());
			mstCompanyForm.setReferralCompanyKananm(company.getReferralCompanyKananm());
			mstCompanyForm.setReferralCompanyTel(company.getReferralCompanyTel());
			mstCompanyForm.setAreaTokyo(company.getAreaTokyo());
			mstCompanyForm.setAreaChubu(company.getAreaChubu());
			mstCompanyForm.setAreaKansai(company.getAreaKansai());
			mstCompanyForm.setAreaKyusyu(company.getAreaKyusyu());
			mstCompanyForm.setReferralCompanyAddress(company.getReferralCompanyAddress());
			mstCompanyForm.setNoExperienceFlg(company.getNoExperienceFlg());
			mstCompanyForm.setExperiencedFlg(company.getExperiencedFlg());
			mstCompanyForm.setNotes(company.getNotes());
		}
		List<Mstcompanyperson> personList = mstCompanyPersonRepository.findByCompanyCd(companyCd);

		model.addAttribute("personList", personList);
		if (!personList.isEmpty()) {
			Mstcompanyperson p = personList.get(0);

			mstCompanyForm.setPersonNm(p.getPersonNm());
			mstCompanyForm.setPersonEmail(p.getPersonEmail());
			mstCompanyForm.setPersonTel(p.getPersonTel());
			mstCompanyForm.setPersonNo(p.getPersonNo());
		}
		return "update/company_update_input";
	}

	/**
	 * 紹介会社変更確認画面を出力します。
	 *
	 * @param mstCompanyForm 入力された紹介会社情報
	 * @param result 入力チェック結果
	 * @param model 画面へデータを渡すModel
	 * @return 入力エラー時は変更入力画面、正常時は変更確認画面
	 */
	@RequestMapping(path = "/company/update/check", method = RequestMethod.POST)
	public String checkUpdate(
			@Validated @ModelAttribute("mstCompanyForm") Mst_CompanyForm mstCompanyForm,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("mstCompanyForm", mstCompanyForm);
			return "update/company_update_input";
		}
		model.addAttribute("mstCompanyForm", mstCompanyForm);
		return "update/company_update_check";
	}

	/**
	 * 紹介会社情報を更新し、変更完了画面を出力します。
	 *
	 * @param mstCompanyForm 更新する紹介会社情報
	 * @param session ログインユーザー情報を保持するセッション
	 * @return 紹介会社変更完了画面
	 */
	@RequestMapping(path = "/company/update/complete", method = RequestMethod.POST)
	public String completeUpdate(Mst_CompanyForm mstCompanyForm, HttpSession session) {
		UsersBean loginUser = (UsersBean) session.getAttribute("user");
		String username = (loginUser != null) ? String.valueOf(loginUser.getUserName()) : "system";

		mstCompanyService.updateCompany(mstCompanyForm, username);

		return "redirect:/company/update/complete";
	}

	@RequestMapping(path = "/company/update/complete", method = RequestMethod.GET)
	public String completeView() {
		return "update/company_update_complete";
	}
}