package jp.co.ptn.recruit.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.ptn.recruit.entity.Users;
import jp.co.ptn.recruit.form.LoginForm;
import jp.co.ptn.recruit.repository.UsersRepository;
import jp.co.ptn.recruit.service.LoginService;

@Controller
public class LoginController {
	
    private final LoginService loginService;
    private final UsersRepository usersRepository;

    public LoginController(LoginService loginService, UsersRepository usersRepository) {
        super();
        this.loginService = loginService;
        this.usersRepository = usersRepository;
    }

    /**
     * ログイン画面表示
     * 
     * @param model
     * @return
     */

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    /**
     * ログイン処理
     * 
     * @param form
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form,
            BindingResult result, HttpSession session, Model model) {
        // 入力チェック
        if (result.hasErrors()) {
            return "login";
        }

        Users user = loginService.login(
                form.getEmail(),
                form.getPersonPassword());

        if (user == null) {

            Users dbUser = usersRepository.findByEmail(form.getEmail());

            // ユーザーが存在し、ロック中なら
            if (dbUser != null
                    && Integer.valueOf(1).equals(dbUser.getAccountLockFlg())
                    && dbUser.getAccountLockTime() != null
                    && LocalDateTime.now().isBefore(dbUser.getAccountLockTime())) {

                model.addAttribute("error",
                        "アカウントがロックされています。しばらくしてから再度お試しください。");
            } else {
                model.addAttribute("error",
                        "メールアドレスまたはパスワードが違います");
            }

            return "login";
        }
        session.setAttribute("loginUser", user);
        return "redirect:/list";
    }

    /**
     * ログアウト
     * 
     * @param session
     * @return
     */

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}