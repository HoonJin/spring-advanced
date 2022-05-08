package com.example.spring.test.service;

import com.example.spring.test.StudyRepository;
import com.example.spring.test.domain.Member;
import com.example.spring.test.domain.Study;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudyService {

    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public Study createStudy(Long memberId, Study study) {
        Member member = memberService.findById(memberId);
        if (member == null)
            throw new IllegalArgumentException("member id is wrong");

        study.setOwner(member);
        return studyRepository.save(study);
    }
}
