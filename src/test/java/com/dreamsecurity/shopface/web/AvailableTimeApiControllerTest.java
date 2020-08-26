package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeAddRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeEditRequestDto;
import com.dreamsecurity.shopface.repository.AvailableTimeRepository;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.EmployRepository;
import com.dreamsecurity.shopface.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static com.dreamsecurity.shopface.ApiDocumentUtils.getDocumentRequest;
import static com.dreamsecurity.shopface.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvailableTimeApiControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    EmployRepository employRepository;

    @Autowired
    AvailableTimeRepository availableTimeRepository;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        // Member business
        Member business = Member.builder()
                .id("test")
                .password("1234")
                .name("김사업")
                .phone("01012341234")
                .state("A")
                .type("B")
                .build();
        memberRepository.save(business);
        // Branch CU?
        Branch branch1 = Branch.builder()
                .name("CU서울점")
                .phone("01012341234")
                .address("서울")
                .detailAddress("강남")
                .zipCode("11111")
                .member(business)
                .state("Y")
                .build();
        branchRepository.save(branch1);
        // Branch CU?
        Branch branch2 = Branch.builder()
                .name("CU아산점")
                .phone("01056785678")
                .address("충남")
                .detailAddress("아산")
                .zipCode("22222")
                .member(business)
                .state("Y")
                .build();
        branchRepository.save(branch2);
        // Member employee
        Member employee1 = Member.builder()
                .id("emp01")
                .password("1234")
                .name("박알바")
                .phone("01045614561")
                .state("A")
                .type("E")
                .build();
        memberRepository.save(employee1);
        Member employee2 = Member.builder()
                .id("emp02")
                .password("5678")
                .name("최인턴")
                .phone("01096329632")
                .state("A")
                .type("E")
                .build();
        memberRepository.save(employee2);

        // Employ employ (Cu에 employee가)
        Employ employ1 = Employ.builder()
                .member(employee1)
                .name("박알바")
                .email("test@test.com")
                .branch(branch1)
                .salary(9000)
                .role(null)
                .department(null)
                .state("I")
                .build();
        employRepository.save(employ1);
        // Employ employ (Cu에 employee가)
        Employ employ1_1 = Employ.builder()
                .member(employee1)
                .name("박알바")
                .email("test@test.com")
                .branch(branch2)
                .salary(9000)
                .role(null)
                .department(null)
                .state("I")
                .build();
        employRepository.save(employ1);
        Employ employ2 = Employ.builder()
                .member(employee2)
                .name("최인턴")
                .email("email@test.com")
                .branch(branch2)
                .salary(8530)
                .role(null)
                .department(null)
                .state("I")
                .build();
        employRepository.save(employ2);

//        AvailableTime saving = AvailableTime.builder()
//                .member(employee1)
//                .branch(branch1)
//                .startTime(LocalDateTime.of(2020,9, 12, 9,0,0))
//                .endTime(LocalDateTime.of(2020, 9, 12, 12,0, 0))
//                .build();
//        availableTimeRepository.save(saving);

//        AvailableTime saving2 = AvailableTime.builder()
//                .member(employee1)
//                .branch(branch1)
//                .startTime(LocalDateTime.of(2020,9, 12, 8,0,0))
//                .endTime(LocalDateTime.of(2020, 9, 12, 12,0, 0))
//                .build();
//        availableTimeRepository.save(saving2);
    }

    @After
    public void tearDown() throws Exception {
        availableTimeRepository.deleteAll();
        employRepository.deleteAll();
        branchRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 가용시간_등록_테스트() throws Exception {
        Member member = memberRepository.findAll().get(1);
        Branch branch = branchRepository.findAll().get(0);



        AvailableTime availableTime = AvailableTime.builder()
                .member(member)
                .branch(branch)
                .startTime(LocalDateTime.of(2020,9, 12, 5,0,0))
                .endTime(LocalDateTime.of(2020, 9, 12, 10,0, 0))
                .build();

        AvailableTimeAddRequestDto requestDto = new AvailableTimeAddRequestDto(availableTime);
        requestDto.setMemberId(member.getId());
        requestDto.setBranchNo(branch.getNo());

        String content = objectMapper.writeValueAsString(requestDto);
        //when
        ResultActions result = mockMvc.perform(post("/availabletime")
                .content(content).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        //then
        List<AvailableTime> results = availableTimeRepository.findAll();
        assertThat(results.get(0).getMember().getId()).isEqualTo(availableTime.getMember().getId());
        assertThat(results.get(0).getBranch().getNo()).isEqualTo(availableTime.getBranch().getNo());
        assertThat(results.get(0).getStartTime()).isEqualTo(LocalDateTime.of(2020,9,12,5,0,0));
        assertThat(results.get(0).getEndTime()).isEqualTo(LocalDateTime.of(2020, 9, 12, 12,0, 0));
    }

    @Test
    public void 사업장별_가용시간_목록조회_테스트() throws Exception {
        //given
        Member emp01 = memberRepository.findAll().get(1);
        Branch branch1 = branchRepository.findAll().get(0);
        Branch branch2 = branchRepository.findAll().get(1);

        for (int i = 0; i < 9; i += 2) {
            AvailableTime availableTime1 = AvailableTime.builder()
                    .member(emp01)
                    .branch(branch1)
                    .startTime(LocalDateTime.of(2020,9, 10, i, 0, 0))
                    .endTime(LocalDateTime.of(2020, 9, 10,i + 1, 0, 0))
                    .build();
            AvailableTime availableTime2 = AvailableTime.builder()
                    .member(emp01)
                    .branch(branch2)
                    .startTime(LocalDateTime.of(2020,9, 11, i, 0, 0))
                    .endTime(LocalDateTime.of(2020, 9, 11,i + 1, 0, 0))
                    .build();

            availableTimeRepository.save(availableTime1);
            availableTimeRepository.save(availableTime2);
        }

//        //when branch-1번
//        mockMvc.perform(get("/branch/" + branch1.getNo() + "/availabletime"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].startTime", is("2020-09-10T00:00:00")))
//                .andExpect(jsonPath("$.data[0].endTime", is("2020-09-10T01:00:00")))
//                .andExpect(jsonPath("$.data[1].startTime", is("2020-09-10T02:00:00")))
//                .andExpect(jsonPath("$.data[1].endTime", is("2020-09-10T03:00:00")))
//                .andExpect(jsonPath("$.data[2].startTime", is("2020-09-10T04:00:00")))
//                .andExpect(jsonPath("$.data[2].endTime", is("2020-09-10T05:00:00")))
//                .andExpect(jsonPath("$.data[3].startTime", is("2020-09-10T06:00:00")))
//                .andExpect(jsonPath("$.data[3].endTime", is("2020-09-10T07:00:00")))
//                .andExpect(jsonPath("$.data[4].startTime", is("2020-09-10T08:00:00")))
//                .andExpect(jsonPath("$.data[4].endTime", is("2020-09-10T09:00:00")))
//                .andDo(document("AvailableTime_List_By_BranchNo"));

//        //when branch-2번
//        mockMvc.perform(get("/branch/" + branch2.getNo() + "/availabletime"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].startTime", is("2020-09-11T00:00:00")))
//                .andExpect(jsonPath("$.data[0].endTime", is("2020-09-11T01:00:00")))
//                .andExpect(jsonPath("$.data[1].startTime", is("2020-09-11T02:00:00")))
//                .andExpect(jsonPath("$.data[1].endTime", is("2020-09-11T03:00:00")))
//                .andExpect(jsonPath("$.data[2].startTime", is("2020-09-11T04:00:00")))
//                .andExpect(jsonPath("$.data[2].endTime", is("2020-09-11T05:00:00")))
//                .andExpect(jsonPath("$.data[3].startTime", is("2020-09-11T06:00:00")))
//                .andExpect(jsonPath("$.data[3].endTime", is("2020-09-11T07:00:00")))
//                .andExpect(jsonPath("$.data[4].startTime", is("2020-09-11T08:00:00")))
//                .andExpect(jsonPath("$.data[4].endTime", is("2020-09-11T09:00:00")))
//                .andDo(document("AvailableTime_List_By_BranchNo"));
    }

    @Test
    public void 근무자의_자기_가용시간_목록조회_테스트() throws Exception {
        //given
        Member emp01 = memberRepository.findAll().get(1);
        Member emp02 = memberRepository.findAll().get(2);
        Branch branch1 = branchRepository.findAll().get(0);
        Branch branch2 = branchRepository.findAll().get(1);

        for (int i = 0; i < 9; i += 2) {
            AvailableTime availableTime0 = AvailableTime.builder()
                    .member(emp02)
                    .branch(branch2)
                    .startTime(LocalDateTime.of(2020,9, 9, i, 0, 0))
                    .endTime(LocalDateTime.of(2020, 9, 9,i + 1, 0, 0))
                    .build();
            AvailableTime availableTime1 = AvailableTime.builder()
                    .member(emp01)
                    .branch(branch1)
                    .startTime(LocalDateTime.of(2020,9, 10, i, 0, 0))
                    .endTime(LocalDateTime.of(2020, 9, 10,i + 1, 0, 0))
                    .build();
            AvailableTime availableTime2 = AvailableTime.builder()
                    .member(emp01)
                    .branch(branch2)
                    .startTime(LocalDateTime.of(2020,9, 11, i, 0, 0))
                    .endTime(LocalDateTime.of(2020, 9, 11,i + 1, 0, 0))
                    .build();
            availableTimeRepository.save(availableTime0);
            availableTimeRepository.save(availableTime1);
            availableTimeRepository.save(availableTime2);
        }

                //when
        mockMvc.perform(get("/member/" + emp01.getId() + "/availabletime"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].startTime", is("2020-09-10T00:00:00")))
                .andExpect(jsonPath("$.data[0].endTime", is("2020-09-10T01:00:00")))
                .andExpect(jsonPath("$.data[1].startTime", is("2020-09-10T02:00:00")))
                .andExpect(jsonPath("$.data[1].endTime", is("2020-09-10T03:00:00")))
                .andExpect(jsonPath("$.data[2].startTime", is("2020-09-10T04:00:00")))
                .andExpect(jsonPath("$.data[2].endTime", is("2020-09-10T05:00:00")))
                .andExpect(jsonPath("$.data[3].startTime", is("2020-09-10T06:00:00")))
                .andExpect(jsonPath("$.data[3].endTime", is("2020-09-10T07:00:00")))
                .andExpect(jsonPath("$.data[4].startTime", is("2020-09-10T08:00:00")))
                .andExpect(jsonPath("$.data[4].endTime", is("2020-09-10T09:00:00")))
                .andExpect(jsonPath("$.data[5].startTime", is("2020-09-11T00:00:00")))
                .andExpect(jsonPath("$.data[5].endTime", is("2020-09-11T01:00:00")))
                .andExpect(jsonPath("$.data[6].startTime", is("2020-09-11T02:00:00")))
                .andExpect(jsonPath("$.data[6].endTime", is("2020-09-11T03:00:00")))
                .andExpect(jsonPath("$.data[7].startTime", is("2020-09-11T04:00:00")))
                .andExpect(jsonPath("$.data[7].endTime", is("2020-09-11T05:00:00")))
                .andExpect(jsonPath("$.data[8].startTime", is("2020-09-11T06:00:00")))
                .andExpect(jsonPath("$.data[8].endTime", is("2020-09-11T07:00:00")))
                .andExpect(jsonPath("$.data[9].startTime", is("2020-09-11T08:00:00")))
                .andExpect(jsonPath("$.data[9].endTime", is("2020-09-11T09:00:00")))
                .andDo(document("AvailableTime_List_By_MemberId"));
    }

    @Test
    public void 가용시간_수정_테스트() throws Exception {
        Member member = memberRepository.findAll().get(1);
        Branch branch = branchRepository.findAll().get(0);

        AvailableTime availableTime = AvailableTime.builder()
                .member(member)
                .branch(branch)
                .startTime(LocalDateTime.of(2020,9, 12, 5,0,0))
                .endTime(LocalDateTime.of(2020, 9, 12, 10,0, 0))
                .build();
        availableTimeRepository.save(availableTime);

        AvailableTime target = AvailableTime.builder()
                .member(member)
                .startTime(LocalDateTime.of(2020,9, 10, 0,0,0))
                .endTime(LocalDateTime.of(2020, 9, 10, 6,0, 0))
                .build();

        String content = objectMapper.writeValueAsString(new AvailableTimeEditRequestDto(target));
        //when /availabletime/{no}
        ResultActions result = mockMvc.perform(put("/availabletime/{no}", availableTime.getNo())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        List<AvailableTime> results = availableTimeRepository.findAll();
        assertThat(results.get(0).getStartTime()).isEqualTo(target.getStartTime());
        assertThat(results.get(0).getEndTime()).isEqualTo(target.getEndTime());
    }

    @Test
    public void 가용시간_삭제_테스트() throws Exception {
        //given
        Member emp01 = memberRepository.findAll().get(1);
        Branch branch1 = branchRepository.findAll().get(0);

        AvailableTime availableTime1 = AvailableTime.builder()
                .member(emp01)
                .branch(branch1)
                .startTime(LocalDateTime.of(2020,9, 10, 0, 0, 0))
                .endTime(LocalDateTime.of(2020, 9, 10,2, 0, 0))
                .build();
        availableTimeRepository.save(availableTime1);

        AvailableTime availableTime = availableTimeRepository.findAll().get(0);
        //when
        ResultActions result = mockMvc.perform(delete("/availabletime/{no}", availableTime.getNo()));
        result
            .andExpect(status().isOk())
            .andDo(document("AvailableTime-remove",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                            parameterWithName("no").description("가용시간 번호")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터")
                    )
            ));


    }
}