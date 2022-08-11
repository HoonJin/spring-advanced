package com.example.spring.login.web.member;

import com.example.spring.login.domain.member.Member;
import com.example.spring.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/add")
    public String addForm(@ModelAttribute Member member) {
        return "/members/addMemberForm";
    }

    @PostMapping("/members/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/members/addMemberForm";
        }

        memberRepository.save(member);
        return "redirect:/";
    }
}
