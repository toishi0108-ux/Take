package jp.co.ptn.recruit.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jp.co.ptn.recruit.entity.Users;
import jp.co.ptn.recruit.repository.UsersRepository;

@Service
public class LoginServiceImpl implements LoginService {
	private final UsersRepository usersRepository;

	public LoginServiceImpl(UsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;

	}

	@Override
	public Users login(String email, String personPassword) {
		Users user = usersRepository.findByEmail(email);

		if (user == null) {
			return null;
		}

		// ロック中か確認
		if (user.getAccountLockFlg() == 1) {
			LocalDateTime now = LocalDateTime.now();

			// ロック中
			if (user.getAccountLockTime() != null
					&& now.isBefore(user.getAccountLockTime())) {
				return null;
			}
			// ロック解除
			usersRepository.unlockAccount(user.getUserId());

			user.setAccountLockFlg(0);
			user.setLoginFailCount(0);
			user.setAccountLockTime(null);
		}

		// パスワード照合
		if (personPassword != null && personPassword.equals(user.getPersonPassword())) {
			usersRepository.resetFailureCount(user.getUserId());
			return user;
		}

		// ログイン失敗
		Integer failureCount = user.getLoginFailCount() + 1;
		usersRepository.updateFailureCount(user.getUserId(), failureCount);
		// 5回以上失敗
		if (failureCount >= 5) {
			// 現在時刻+5分
			LocalDateTime accountLockTime = LocalDateTime.now().plusMinutes(5);

			usersRepository.lockAccount(user.getUserId(), accountLockTime);
		} else {
			usersRepository.updateFailureCount(user.getUserId(), failureCount);
		}
		return null;
	}
}