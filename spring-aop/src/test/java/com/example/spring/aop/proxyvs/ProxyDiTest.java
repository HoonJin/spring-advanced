package com.example.spring.aop.proxyvs;

import com.example.spring.aop.member.MemberService;
import com.example.spring.aop.member.MemberServiceImpl;
import com.example.spring.aop.proxyvs.code.ProxyDiAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {
//         "spring.aop.proxy-target-class=false", // jdk dynamic proxy는 사실상 사용 불가
         "spring.aop.proxy-target-class=true", // spring 기본 CGLIB
})
@Import(ProxyDiAspect.class)
public class ProxyDiTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;


    // CGLIB 의 단점
    // constructor 필수 -> 어찌보면 당연한 것? ㅋㅋ
    // 생성자 두번 호출 (proxy의 생성자) -> Objenesis 라이브러리로 해결하게 됨
    // final class, final method 사용 불가 (proxy class 생성 불가) -> 실제로 final 키워드를 잘 사용하지 않음
    @Test
    void go() {
        log.info("memberService CLASS={}", memberService.getClass());
        log.info("memberServiceImpl CLASS={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("abc");
    }
}
