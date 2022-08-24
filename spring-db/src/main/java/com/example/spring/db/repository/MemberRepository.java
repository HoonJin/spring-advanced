package com.example.spring.db.repository;

import com.example.spring.db.domain.Member;

public interface MemberRepository {

    Member save(Member member);

    Member findById(String memberId);

    int update(String memberId, int money);

    int delete(String memberId);

}
