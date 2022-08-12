package com.example.spring.login.web.login;

import com.example.spring.login.domain.login.LoginService;
import com.example.spring.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        log.info("form = {}", loginForm);
        Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("member {}", member);

        if (member == null) {
            bindingResult.reject("loginError", "틀렷어 너");
            return "login/loginForm";
        } else {
            // 로그인 성공 처리 -> cookie 를 활용
            Cookie cookie = new Cookie("memberId", String.valueOf(member.getId()));
            response.addCookie(cookie);
            return "redirect:/";
        }
    }

    @PostMapping("logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

}
