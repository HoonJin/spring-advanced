package com.example.spring.test.study;

import com.example.spring.test.domain.Member;
import com.example.spring.test.domain.Study;
import com.example.spring.test.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@Testcontainers
@Slf4j
@ContextConfiguration(initializers = StudyServiceTest.ContainerPropertyInitializer.class)
class StudyServiceTest {

    @Mock
    private MemberService memberService;

    @Mock
//    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    Environment environment;

    // https://www.testcontainers.org/test_framework_integration/junit_5/
//    @Container
//    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres")
//            .withDatabaseName("test");

    @Container
    static GenericContainer genericContainer = new GenericContainer("postgres")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "test");

    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer slf4jLogConsumer = new Slf4jLogConsumer(log);
        genericContainer.followOutput(slf4jLogConsumer);
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("-----------");
        System.out.println("environment.getProperty(\"container.port\") = " + environment.getProperty("container.port"));
//        studyRepository.deleteAll();
    }

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

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "container.port=" + genericContainer.getMappedPort(5432)
            ).applyTo(applicationContext);
        }
    }
}