package com.example.spring.test.service;

import com.example.spring.test.StudyRepository;
import com.example.spring.test.domain.Member;
import com.example.spring.test.domain.Study;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
    private StudyRepository studyRepository;

    @Test
    void createStudyTest() {
        // GIVEN
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("studyRepository = " + studyRepository);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member newMember = new Member();
        newMember.setId(1L);
        newMember.setEmail("a@a.com");
        // when(memberService.findById(1L)).thenReturn(newMember);
        given(memberService.findById(1L)).willReturn(newMember);

        Study study = new Study(10);

        // WHEN
        studyService.createStudy(1L, study);

        // THEN
        // verify 는 몇번 호출되었는가 검증함.
        //verify(studyRepository, times(1)).save(study);
        then(studyRepository).should(times(1)).save(study);
        then(studyRepository).shouldHaveNoMoreInteractions();

        assertEquals("a@a.com", study.getOwner().getEmail());
    }
}