package com.example.spring.login.web;

import com.example.spring.login.domain.member.Member;
import com.example.spring.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")
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
}