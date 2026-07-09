package jp.co.ptn.recruit.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.ptn.recruit.dto.CompanyRowDto;
import lombok.RequiredArgsConstructor;

/**
 * 紹介会社マスタおよび担当者マスタに関するデータベース操作を行うリポジトリクラスです。
 * Spring Bootの管理するJdbcTemplateを使用してデータの取得を行います。
 */
@Repository
@RequiredArgsConstructor
public class CompanyRepository {

	private final JdbcTemplate jdbcTemplate;

	/**
	 * 削除フラグが立っていない有効な紹介会社の一覧を指定されたソート順で取得します。
	 * 会社データの重複を防ぐため、サブクエリを用いて担当者名を最新の1件のみ取得します。
	 * * @param sortKey 画面のプルダウンから渡される並び替え条件の識別キー（null可）
	 * @return ソートおよびフォーマット済みの紹介会社一覧
	 */
	public List<CompanyRowDto> findAllActiveCompanies(String sortKey) {

		// 【修正】LEFT JOINとGROUP BYを廃止し、サブクエリで担当者名を取得することで重複を完全に防ぐ
		String sql = "SELECT "
				+ "  c.referral_company_cd AS 'No', "
				+ "  c.referral_company_nm AS '会社名', "
				+ "  c.referral_company_kananm AS '会社名（カナ）', "
				+ "  (SELECT MAX(p.person_nm) FROM mst_company_person p WHERE p.referral_company_cd = c.referral_company_cd AND p.delete_flg = 0) AS '担当者名', "
				+ "  c.referral_company_tel AS '紹介会社電話番号', "
				+ "  c.area_tokyo AS '東京', "
				+ "  c.area_chubu AS '中部', "
				+ "  c.area_kansai AS '関西', "
				+ "  c.area_kyusyu AS '九州', "
				+ "  c.referral_company_address AS '紹介会社住所', "
				+ "  c.no_experience_flg AS '未経験採用', "
				+ "  c.experienced_flg AS '経験者採用', "
				+ "  c.delete_flg AS '削除', "
				+ "  c.notes AS '備考' "
				+ "FROM mst_company c "
				+ "WHERE c.delete_flg = 0 ";

		// 【修正】動的ORDER BY句の組み立て（初期表示・デフォルトをNOの昇順にする）
		String orderByClause = "ORDER BY c.referral_company_cd ASC";

		if (sortKey != null) {
			switch (sortKey) {
				case "create_old": // ② 作成日時（古い順）
					orderByClause = "ORDER BY c.create_date ASC, c.create_date DESC";
					break;
				case "name_asc":   // ③ 紹介会社名（昇順）
					orderByClause = "ORDER BY c.referral_company_nm ASC, c.create_date DESC";
					break;
				case "name_desc":  // ④ 紹介会社名（降順）
					orderByClause = "ORDER BY c.referral_company_nm DESC, c.create_date DESC";
					break;
				default:           // ① デフォルト：作成日時（新しい順）
					orderByClause = "ORDER BY c.create_date DESC";
					break;
			}
		}

		sql += orderByClause;

		// JdbcTemplateによるデータマッピング処理
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			CompanyRowDto dto = new CompanyRowDto();
			dto.setCompanyCd(rs.getInt("No"));
			dto.setCompanyNm(rs.getString("会社名"));
			dto.setCompanyKanaNm(rs.getString("会社名（カナ）"));
			dto.setPersonNm(rs.getString("担当者名"));

			String tel = rs.getString("紹介会社電話番号");

			// 電話番号のハイフンフォーマット処理（10桁/11桁対応）
			if (tel != null && !tel.contains("-")) {
				if (tel.length() == 11) {
					tel = tel.substring(0, 3) + "-" + tel.substring(3, 7) + "-" + tel.substring(7);
				} else if (tel.length() == 10) {
					if (tel.startsWith("03") || tel.startsWith("06")) {
						tel = tel.substring(0, 2) + "-" + tel.substring(2, 6) + "-" + tel.substring(6);
					} else {
						tel = tel.substring(0, 3) + "-" + tel.substring(3, 6) + "-" + tel.substring(6);
					}
				}
			}

			dto.setCompanyTel(tel);
			dto.setAreaTokyo((Boolean) rs.getObject("東京"));
			dto.setAreaChubu((Boolean) rs.getObject("中部"));
			dto.setAreaKansai((Boolean) rs.getObject("関西"));
			dto.setAreaKyusyu((Boolean) rs.getObject("九州"));
			dto.setCompanyAddress(rs.getString("紹介会社住所"));
			dto.setNoExperienceFlg((Boolean) rs.getObject("未経験採用"));
			dto.setExperiencedFlg((Boolean) rs.getObject("経験者採用"));
			dto.setDeleteFlg((Boolean) rs.getObject("削除"));
			dto.setNotes(rs.getString("備考"));

			return dto;
		});
	}
}