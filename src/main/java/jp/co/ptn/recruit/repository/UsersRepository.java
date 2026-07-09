package jp.co.ptn.recruit.repository;

import java.time.LocalDateTime;

import jp.co.ptn.recruit.entity.Users;

public interface UsersRepository {
	/**
	 * 
	 * メールアドレスでユーザーを取得
	 * 
	 * @param email メールアドレス
	 * @return 該当ユーザー
	 */
	Users findByEmail(String email);

	/**
	 * ログイン失敗回数を更新する
	 * 
	 * @parm userId ユーザーID
	 * @parm count 失敗回数
	 */
	void updateFailureCount(Integer userId, Integer loginFailCount);

	/**
	 * ログイン失敗回数をリセットする
	 */
	void resetFailureCount(Integer userId);

	/**
	 * アカウントをロックする
	 * @param userId ユーザーID
	 * @param accountLockTime ロック解除時刻
	 */
	void lockAccount(Integer userId, LocalDateTime accountLockTime);

	/**
	 * アカウントロックを解除する
	 * @param userId ユーザーID
	 */
	void unlockAccount(Integer userId);
}