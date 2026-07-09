package jp.co.ptn.recruit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // 追加
import org.springframework.validation.annotation.Validated; // 追加
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ptn.recruit.form.CompanyForm;
import jp.co.ptn.recruit.service.CompanyCreateService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CompanyCreateController {

	private final CompanyCreateService companyService;

	// ① 新規登録画面（入力画面）を表示する
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("companyForm", new CompanyForm());
		return "regist/company_create_input";
	}

	// ② 【修正】入力画面から確認（チェック）画面へ遷移させる（バリデーション追加）
	@PostMapping("/register/check")
	public String showConfirmPage(
			@ModelAttribute @Validated CompanyForm companyForm, 
			BindingResult result,                               
			Model model) {

		if (result.hasErrors()) {
			// エラー内容と入力データが保持されたまま入力画面へ戻ります
			return "regist/company_create_input";
		}

		// 入力されたデータをそのまま確認画面の th:object="${companyForm}" に引き渡す
		model.addAttribute("companyForm", companyForm);
		return "regist/company_create_check";
	}

	// ③ 確認画面からのアクション（登録実行 または 入力へ戻る）
	@PostMapping("/register/execute")
	public String executeRegister(
			@ModelAttribute CompanyForm companyForm,
			@RequestParam(value = "back", required = false) String back,
			Model model) {

		// もし確認画面で「戻る」ボタン（name="back"）が押されていた場合
		if (back != null) {
			// 入力されていたデータを保持したまま、入力画面（input）へ戻す
			model.addAttribute("companyForm", companyForm);
			return "regist/company_create_input";
		}
		
		// 「登録する」が押された場合は、サービス経由でDBへ保存
		companyService.registerCompany(companyForm);

		return "redirect:/regist/complete/view";
	}

	@GetMapping("/regist/complete/view")
	public String showCompleteView() {
		return "regist/company_create_complete";
	}
}