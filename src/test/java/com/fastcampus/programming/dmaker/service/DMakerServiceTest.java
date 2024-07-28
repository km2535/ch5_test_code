package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.code.StatusCode;
import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.fastcampus.programming.dmaker.code.StatusCode.*;
import static com.fastcampus.programming.dmaker.type.DeveloperLevel.*;
import static com.fastcampus.programming.dmaker.type.DeveloperSkillType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest // 통합 테스트
class DMakerServiceTest {
    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    @Test
    public void testSomething(){
        dMakerService.createDeveloper(CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
                .developerSkillType(FRONT_END)
                .experienceYears(12)
                .memberId("memberId")
                .name("name")
                .age(12)
            .build());

        List<DeveloperDto> allEmployedDevelopers = dMakerService.getAllEmployedDevelopers();
        System.out.println(allEmployedDevelopers);

        // given(developerRepository.findByMemberId(anyString()))
        //     .willReturn(Optional.of(Developer.builder()
        //             .developerLevel(SENIOR)
        //             .developerSkillType(FRONT_END)
        //             .experienceYears(12)
        //             .statusCode(EMPLOYED)
        //             .name("name")
        //             .age(12).build()));
        //
        // DeveloperDetailDto developerDetailDto = dMakerService.getDeveloperDetail("memberId");

        // assertEquals(SENIOR, developerDetailDto.getDeveloperLevel());
    }
}