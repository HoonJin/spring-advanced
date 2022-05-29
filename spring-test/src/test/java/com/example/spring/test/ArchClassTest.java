package com.example.spring.test;

import com.example.spring.test.domain.Study;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packagesOf = SpringTestApplication.class)
public class ArchClassTest {

    @ArchTest
    ArchRule controllerClassRule = classes().that()
            .haveSimpleNameEndingWith("Service")
            .should().accessClassesThat().haveSimpleNameEndingWith("Repository");
//            .orShould().accessClassesThat().haveSimpleNameEndingWith("Repository");

    @ArchTest
    ArchRule repositoryClassRule = classes().that()
            .haveSimpleNameEndingWith("Repository")
            .should().accessClassesThat().haveSimpleNameNotContaining("Service")
            .orShould().accessClassesThat().haveSimpleNameNotContaining("Controller");

    @ArchTest
    ArchRule studyClassesRule = classes().that()
            .haveSimpleNameStartingWith("Study")
            .and().areNotEnums()
            .should().notBe("Study")
            .orShould().resideInAnyPackage("..study..");


}
