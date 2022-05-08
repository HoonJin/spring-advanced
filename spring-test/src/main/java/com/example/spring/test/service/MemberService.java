package com.example.spring.test.service;

import com.example.spring.test.domain.Member;

public interface MemberService {

    Member findById(Long memberId);
}
