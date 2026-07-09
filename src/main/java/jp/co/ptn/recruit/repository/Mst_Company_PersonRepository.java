package jp.co.ptn.recruit.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.co.ptn.recruit.entity.Mstcompanyperson;
import lombok.RequiredArgsConstructor;

/**
 * 紹介会社担当者情報のデータベース操作を行うリポジトリクラスです。
 */
@Repository
@RequiredArgsConstructor
public class Mst_Company_PersonRepository {

    /** JDBC操作を行うためのテンプレート */
    private final JdbcTemplate jdbcTemplate;

    /** 紹介会社担当者情報をエンティティへ変換するRowMapper */
    private final RowMapper<Mstcompanyperson> mapper =
            new RowMapper<>() {

                @Override
                public Mstcompanyperson mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Mstcompanyperson e = new Mstcompanyperson();

                    e.setPersonNo(rs.getInt("person_no"));
                    e.setPersonNm(rs.getString("person_nm"));
                    e.setPersonEmail(rs.getString("person_email"));
                    e.setPersonTel(rs.getString("person_tel"));
                    e.setReferralCompanycd(rs.getInt("referral_company_cd"));

                    return e;
                }
            };

    /**
     * 指定した紹介会社コードに紐づく担当者情報を取得します。
     *
     * @param companyCd 紹介会社コード
     * @return 担当者情報一覧
     */
    public List<Mstcompanyperson> findByCompanyCd(Integer companyCd) {

        String sql = """
                SELECT *
                FROM mst_company_person
                WHERE referral_company_cd = ?
                  AND delete_flg = 0
                """;

        return jdbcTemplate.query(sql, mapper, companyCd);
    }

    /**
     * 紹介会社担当者情報を更新します。
     *
     * @param e 更新する担当者情報
     * @param username 更新を実行したユーザー名
     * @return 更新件数
     */
    public int updatePerson(Mstcompanyperson e, String username) {

        String sql = """
                UPDATE mst_company_person
                   SET person_nm = ?,
                       person_email = ?,
                       person_tel = ?,
                       upd_user_user = ?,
                       update_date = NOW()
                 WHERE referral_company_cd = ?
                   AND delete_flg = 0
                """;

        return jdbcTemplate.update(
                sql,
                e.getPersonNm(),
                e.getPersonEmail(),
                e.getPersonTel(),
                username,
                e.getReferralCompanycd());
    }
}