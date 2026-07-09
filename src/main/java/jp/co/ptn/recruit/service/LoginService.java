package jp.co.ptn.recruit.service;

import jp.co.ptn.recruit.entity.Users;

public interface LoginService {
 /**
 * ログイン認証
 * 
 * @param email
 * @param personPassword
 * @return
 */
 Users login(String email, String personPassword);
}
