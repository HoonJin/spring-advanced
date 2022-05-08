package com.example.spring.test.service;

import com.example.spring.test.StudyRepository;
import com.example.spring.test.domain.Member;
import com.example.spring.test.domain.Study;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
    private StudyRepository studyRepository;

    @Test
    void createStudyTest() {
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("studyRepository = " + studyRepository);

        Member newMember = new Member();
        newMember.setId(1L);
        newMember.setEmail("a@a.com");
        when(memberService.findById(1L)).thenReturn(newMember);

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Study study = new Study(10);

        studyService.createStudy(1L, study);

        assertEquals("a@a.com", study.getOwner().getEmail());
    }
}