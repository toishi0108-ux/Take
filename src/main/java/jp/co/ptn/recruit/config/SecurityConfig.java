package jp.co.ptn.recruit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())

				// 認可設定
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/login",
								"/error",
								"/css/**",
								"/js/**")
						.permitAll()
						.anyRequest().permitAll())

				// Spring Securityデフォルトログイン画面を無効化
				.formLogin(form -> form.disable())

				// ログアウト設定
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"));

		return http.build();
	}
}