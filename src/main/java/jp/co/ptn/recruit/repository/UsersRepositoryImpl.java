package jp.co.ptn.recruit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.ptn.recruit.entity.Users;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
	private final JdbcTemplate jdbcTemplate;

	public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * メールアドレスでユーザーを取得
	 */
	@Override
	public Users findByEmail(String email) {
		String sql = "SELECT " +
				"user_id, " +
				"user_name, " +
				"email, " +
				"person_password, " +
				"login_fail_count, " +
				"account_lock_flg, " +
				"account_lock_time " +
				"FROM users " +
				"WHERE email = ?";
		List<Users> users = jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<>(Users.class),
				email);

		return users.isEmpty() ? null : users.get(0);
	}

	/**
	 * ログイン失敗回数更新
	 */
	@Override
	public void updateFailureCount(Integer userId, Integer loginFailCount) {
		String sql = "UPDATE users " +
				"SET login_fail_count = ? " +
				"WHERE user_id = ?";

		int count = jdbcTemplate.update(sql, loginFailCount, userId);
		System.out.println("updateFailureCount");
		System.out.println("userId = " + userId);
		System.out.println("loginFailCount = " + loginFailCount);
		System.out.println("更新件数 = " + count);
	}

	/**
	 * ログイン失敗回数リセット
	 */
	@Override
	public void resetFailureCount(Integer userId) {
		String sql = "UPDATE users " +
				"SET login_fail_count = 0 " +
				"WHERE user_id = ?";

		jdbcTemplate.update(sql, userId);
	}

	/**
	 * アカウントロック
	 */
	@Override
	public void lockAccount(Integer userId, LocalDateTime accountLockTime) {

		String sql = "UPDATE users " +
				"SET account_lock_flg = 1, " +
				"account_lock_time = ? " +
				"WHERE user_id = ?";

		int count = jdbcTemplate.update(sql, accountLockTime, userId);

		System.out.println("lockAccount");
		System.out.println("userId = " + userId);
		System.out.println("更新件数 = " + count);
	}

	/**
	 * アカウントロック解除
	 */
	@Override
	public void unlockAccount(Integer userId) {

		String sql = "UPDATE users " +
				"SET account_lock_flg = 0, " +
				"login_fail_count = 0, " +
				"account_lock_time = NULL " +
				"WHERE user_id = ?";

		jdbcTemplate.update(sql, userId);
	}
}