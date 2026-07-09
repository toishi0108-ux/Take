package jp.co.ptn.recruit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ptn.recruit.dto.CompanyRowDto;
import jp.co.ptn.recruit.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

/**
 * 紹介会社に関するビジネスロジックを制御するサービス sympathy クラスです。
 * コントローラーからの要求を受け、リポジトリ層と連携して適切なデータ加工を行います。
 */
@Service
@RequiredArgsConstructor
public class CompanyService {

	/** 紹介会社データベース操作用リポジトリ */
	private final CompanyRepository companyRepository;

	/**
	 * 指定されたソートキーに基づいて、現在有効な紹介会社の一覧を取得します。
	 * 内部でリポジトリメソッドを呼び出し、必要なソート句を動的に適用します。
	 * 
	 * @param sortKey 画面から渡された並び替えの基準キー（"create_desc", "create_old", "name_asc", "name_desc"）
	 * @return ソートが適用された紹介会社行データのリスト
	 */
	public List<CompanyRowDto> getCompanyList(String sortKey) {
		return companyRepository.findAllActiveCompanies(sortKey);
	}
}