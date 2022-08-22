package com.example.spring.login.web;

import com.example.spring.login.domain.member.Member;
import com.example.spring.login.domain.member.MemberRepository;
import com.example.spring.login.web.resolver.Login;
import com.example.spring.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (model == null) {
            return "home";
        } else {
            log.info("login memberId {}", memberId);
            Member member = memberRepository.findById(memberId);
            if (member == null) {
                return "home";
            } else {
                model.addAttribute("member", member);
            }
        }
        return "login/loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        Object session = sessionManager.getSession(request);
        if (session == null) {
            return "home";
        } else {
            Member member = (Member) session;

            log.info("login memberId {}", member.getId());
            model.addAttribute("member", member);
        }
        return "login/loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        } else {
            if (session.getAttribute(SessionConst.LOGIN_MEMBER) instanceof Member member) {
                log.info("login memberId {}", member.getId());
                model.addAttribute("member", member);
                return "login/loginHome";
            } else
                return "home";
        }
    }

//    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
        if (member == null) {
            return "home";
        } else {
            log.info("login memberId {}", member.getId());
            model.addAttribute("member", member);
            return "login/loginHome";
        }
    }

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member member, Model model) {
        if (member == null) {
            return "home";
        } else {
            log.info("login memberId {}", member.getId());
            model.addAttribute("member", member);
            return "login/loginHome";
        }
    }
}