

# Spring Boot와 JUnit을 사용한 서비스 레이어 테스트
서비스 레이어는 애플리케이션의 비즈니스 로직을 처리하는 중요한 부분입니다. 
따라서 서비스 레이어의 단위 테스트는 애플리케이션의 안정성을 보장하는 데 필수적입니다. 
JUnit과 Spring Boot를 사용하여 서비스 레이어를 테스트하는 방법을 살펴보겠습니다. 

## 테스트 설정

### 의존성 추가
build.gradle 파일에 JUnit과 Mockito 의존성을 추가합니다.
```java
    //이미 spring-boot-starter-test에 포함되어 있음
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
```
### 테스트 클래스 작성
이제 테스트 클래스를 작성하겠습니다.
이 클래스는 @ExtendWith(MockitoExtension.class)와 @SpringBootTest 어노테이션을 사용하여 설정합니다.

```java
import org.junit.jupiter.api.Test;
//.. import 생략

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    @Test
    void testCreateDeveloperAndRetrieveAllEmployedDevelopers() {
        // 테스트 데이터 생성
        CreateDeveloper.Request request = CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .memberId("memberId")
            .name("name")
            .age(12)
            .build();

        // 서비스 메서드 호출
        dMakerService.createDeveloper(request);

        // 서비스 메서드 호출 및 결과 검증
        List<DeveloperDto> allEmployedDevelopers = dMakerService.getAllEmployedDevelopers();
        assertEquals(1, allEmployedDevelopers.size());
        assertEquals("name", allEmployedDevelopers.get(0).getName());
    }

    @Test
    void testGetDeveloperDetail() {
        // Mock 설정
        given(developerRepository.findByMemberId(anyString()))
            .willReturn(Optional.of(Developer.builder()
                .developerLevel(SENIOR)
                .developerSkillType(FRONT_END)
                .experienceYears(12)
                .statusCode(EMPLOYED)
                .name("name")
                .age(12).build()));

        // 서비스 메서드 호출 및 결과 검증
        DeveloperDetailDto developerDetailDto = dMakerService.getDeveloperDetail("memberId");
        assertEquals(SENIOR, developerDetailDto.getDeveloperLevel());
        assertEquals("name", developerDetailDto.getName());
    }
}
```
## 테스트 코드
- @ExtendWith(MockitoExtension.class)
  - 이 어노테이션은 Mockito를 사용하여 테스트를 실행하는 데 필요합니다. 
  - MockitoExtension은 Mockito의 기능을 JUnit 5와 통합합니다.

- @SpringBootTest
  - 이 어노테이션은 통합 테스트를 위해 Spring Boot 애플리케이션 컨텍스트를 로드합니다. 
  - 애플리케이션의 모든 빈이 로드되고 실제 환경과 유사한 조건에서 테스트를 수행할 수 있습니다.

- @Mock과 @InjectMocks
  - @Mock: Mock 객체를 생성합니다. 여기서는 DeveloperRepository와 RetiredDeveloperRepository를 Mock으로 설정하였습니다. 
  - @InjectMocks: Mock 객체를 주입받을 클래스의 인스턴스를 생성합니다. 여기서는 DMakerService를 주입받습니다.

## Mock 객체란?

- Mock 객체는 단위 테스트에서 외부 종속성을 격리하고 제어하기 위해 사용되는 객체입니다. 
- 이를 통해 실제 객체의 복잡한 동작을 흉내내고 테스트 시나리오를 쉽게 설정할 수 있습니다. 
- Mock 객체를 사용하면 테스트하려는 코드의 로직만 집중적으로 검증할 수 있으며, 테스트 실행 시의 의존성이나 환경의 영향을 최소화할 수 있습니다.

### 정의 
 - Mock 객체는 테스트 중에 실제 객체를 대체하는 가짜 객체입니다. 
 - Mock 객체는 실제 객체처럼 행동하도록 프로그래밍되지만, 실제 객체와 달리 테스트 코드에서 쉽게 제어할 수 있습니다.

### 목적
외부 종속성 격리: 데이터베이스, 웹 서비스, 파일 시스템 등과 같은 외부 종속성을 격리하여 테스트할 수 있습니다.
테스트 제어: 특정 상황을 시뮬레이션하거나 예외 상황을 테스트할 수 있습니다.
빠른 테스트: 실제 객체를 사용하는 것보다 Mock 객체를 사용하면 테스트 실행 속도가 빨라집니다.

--> 여기서 사용된 Mock 객체로 생성된 Repository는 실제 데이터베이스와 통신하지 않아 실제 비즈니스 로직에서 벗어나 테스트 할 수 있습니다.
