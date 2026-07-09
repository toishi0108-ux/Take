package jp.co.ptn.recruit.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ptn.recruit.dto.CompanyRowDto;
import jp.co.ptn.recruit.service.CompanyService;
import lombok.RequiredArgsConstructor;

/**
 * 紹介会社の一覧画面および検索画面のHTTPリクエストをハンドリングするコントローラークラスです。
 * 画面からの入力値を受け取り、サービス層の呼び出しおよび遷移先画面へのデータ引き渡しを制御します。
 */
@Controller
@RequiredArgsConstructor
public class CompanyController {

	/** 紹介会社業務ロジック用サービス */
	private final CompanyService companyService;

	/**
	 * 紹介会社の一覧画面（検索結果画面）を表示します。
	 * プルダウンから渡される並び替えキーを条件にデータを取得し、HTMLテンプレートへ渡します。
	 * 
	 * @param sort  画面のプルダウンで選択されたソートキー（未指定の場合はデフォルト値 "create_desc" が適用される）
	 * @param model ビューへデータを渡すためのSpring Framework Modelオブジェクト
	 * @return 遷移先となるThymeleaf HTMLテンプレートのパス ("list/company_list")
	 */
	@GetMapping("/list")
	public String showList(
			@RequestParam(name = "sort", required = false, defaultValue = "create_desc") String sort,
			Model model) {

		// サービスを呼び出して指定ソート順の一覧データを取得
		List<CompanyRowDto> list = companyService.getCompanyList(sort);

		// 取得した一覧データをModelに登録
		model.addAttribute("companyList", list);

		// 画面リロード後もプルダウンの選択状態を保持させるために現在のキーを登録
		model.addAttribute("currentSort", sort);
		
		return "list/company_list";
	}
}