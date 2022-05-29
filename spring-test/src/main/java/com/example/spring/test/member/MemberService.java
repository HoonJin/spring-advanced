package com.example.spring.test.member;

import com.example.spring.test.domain.Member;

public interface MemberService {

    Member findById(Long memberId);
}
