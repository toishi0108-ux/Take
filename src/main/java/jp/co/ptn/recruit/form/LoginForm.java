package jp.co.ptn.recruit.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginForm {
	// メールアドレス
	@NotBlank(message = "{email.required}")
	@Email(message = "{email.format}")
	@Size(max = 64, message = "{email.size}")
	private String email;

	// パスワード
	@NotBlank(message = "{password.required}")
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\\\",.<>/?]{8,16}$", message = "{password.format}")
	private String personPassword;

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email セットする email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return personPassword
	 */
	public String getPersonPassword() {
		return personPassword;
	}

	/**
	 * @param personPassword セットする personPassword
	 */
	public void setPersonPassword(String personPassword) {
		this.personPassword = personPassword;
	}

}