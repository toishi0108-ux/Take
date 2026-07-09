package jp.co.ptn.recruit.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.co.ptn.recruit.entity.Mstcompany;
import lombok.RequiredArgsConstructor;

/**
 * 紹介会社情報のデータベース操作を行うリポジトリクラスです。
 */
@Repository
@RequiredArgsConstructor
public class Mst_CompanyRepository {

    /** JDBC操作を行うためのテンプレート */
    private final JdbcTemplate jdbcTemplate;

    /** 紹介会社情報をエンティティへ変換するRowMapper */
    private final RowMapper<Mstcompany> mapper =
            new RowMapper<Mstcompany>() {

                @Override
                public Mstcompany mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Mstcompany e = new Mstcompany();

                    e.setReferralCompanyCd(rs.getInt("referral_company_cd"));
                    e.setReferralCompanyNm(rs.getString("referral_company_nm"));
                    e.setReferralCompanyKananm(rs.getString("referral_company_kananm"));
                    e.setReferralCompanyTel(rs.getString("referral_company_tel"));
                    e.setAreaTokyo(rs.getBoolean("area_tokyo"));
                    e.setAreaChubu(rs.getBoolean("area_chubu"));
                    e.setAreaKansai(rs.getBoolean("area_kansai"));
                    e.setAreaKyusyu(rs.getBoolean("area_kyusyu"));
                    e.setReferralCompanyAddress(rs.getString("referral_company_address"));
                    e.setNoExperienceFlg(rs.getBoolean("no_experience_flg"));
                    e.setExperiencedFlg(rs.getBoolean("experienced_flg"));
                    e.setNotes(rs.getString("notes"));

                    return e;
                }
            };

    /**
     * 指定した紹介会社コードの紹介会社情報を取得します。
     *
     * @param companyCd 紹介会社コード
     * @return 紹介会社情報
     */
    public Mstcompany findById(Integer companyCd) {

        String sql = """
                SELECT *
                FROM mst_company
                WHERE referral_company_cd = ?
                  AND delete_flg = 0
                """;

        return jdbcTemplate.queryForObject(sql, mapper, companyCd);
    }

    /**
     * 紹介会社情報を更新します。
     *
     * @param e 更新する紹介会社情報
     * @param username 更新を実行したユーザー名
     * @return 更新件数
     */
    public int update(Mstcompany e, String username) {

        String sql = """
                UPDATE mst_company
                   SET referral_company_nm = ?,
                       referral_company_kananm = ?,
                       referral_company_tel = ?,
                       area_tokyo = ?,
                       area_chubu = ?,
                       area_kansai = ?,
                       area_kyusyu = ?,
                       referral_company_address = ?,
                       no_experience_flg = ?,
                       experienced_flg = ?,
                       notes = ?,
                       upd_user_user = ?,
                       update_date = NOW()
                 WHERE referral_company_cd = ?
                """;

        return jdbcTemplate.update(
                sql,
                e.getReferralCompanyNm(),
                e.getReferralCompanyKananm(),
                e.getReferralCompanyTel(),
                e.getAreaTokyo(),
                e.getAreaChubu(),
                e.getAreaKansai(),
                e.getAreaKyusyu(),
                e.getReferralCompanyAddress(),
                e.getNoExperienceFlg(),
                e.getExperiencedFlg(),
                e.getNotes(),
                username,
                e.getReferralCompanyCd());
    }
}