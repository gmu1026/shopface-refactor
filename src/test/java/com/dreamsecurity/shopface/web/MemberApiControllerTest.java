package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.member.MemberAddRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberEditRequestDto;
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

import java.util.List;

import static com.dreamsecurity.shopface.ApiDocumentUtils.getDocumentRequest;
import static com.dreamsecurity.shopface.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WebApplicationContext context;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    EmployRepository employRepository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @After
    public void tearDown() {
        employRepository.deleteAll();
        branchRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 멤버목록조회_테스트() throws Exception {
        //given
        for (int i = 0; i < 3; i++) {
            Member member = Member.builder()
                    .id("test" + i)
                    .password("1234")
                    .name("테스트" + i)
                    .phone("0102345123" + i)
                    .state("A")
                    .type("E")
                    .build();
            memberRepository.save(member);
        }
        //when
        ResultActions result = mockMvc.perform(get("/member"));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is("test0")))
                .andExpect(jsonPath("$.data[1].id", is("test1")))
                .andExpect(jsonPath("$.data[2].id", is("test2")))
                .andDo(document("Member-list",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            subsectionWithPath("data.[]").type(JsonFieldType.ARRAY).description("데이터")
                    )
                )
        );
    }

    @Test
    public void 사업자등록_테스트() throws Exception {
        //given
        Member businessman = Member.builder()
                .id("test_businessman")
                .password("1234")
                .name("사업자")
                .phone("01012345678")
                .address("서울")
                .detailAddress("강남")
                .zipCode("12345")
                .email("test@test.com")
                .build();

        MemberAddRequestDto requestDto = new MemberAddRequestDto(businessman);

        String content = objectMapper.writeValueAsString(requestDto);

    // when
    ResultActions result = mockMvc.perform(post("/member")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
    result
        .andExpect(status().isOk())
        .andDo(document("Member-add-businessman",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호"),
                        fieldWithPath("certCode").type(JsonFieldType.STRING).ignored()
                ),
                responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                        fieldWithPath("data").type(JsonFieldType.STRING).description("ID")
                )));
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(0).getId()).isEqualTo(businessman.getId());
        assertThat(memberList.get(0).getName()).isEqualTo(businessman.getName());
        assertThat(memberList.get(0).getType()).isEqualTo("B");
    }

    @Test
    public void 근무자등록_테스트() throws Exception {
        //given
        Member member = Member.builder()
                .id("test_businessman")
                .password("1234")
                .phone("01000001111")
                .email("test@test.com")
                .address("서울")
                .detailAddress("잠실")
                .zipCode("12344")
                .name("사업자")
                .type("B")
                .build();

        memberRepository.save(member);

        Branch branch = Branch.builder()
                .name("테스트")
                .member(member)
                .address("서울")
                .detailAddress("강남")
                .zipCode("12121")
                .phone("01014785236")
                .build();

        branchRepository.save(branch);

        Employ employ = Employ.builder()
                .name("근무자")
                .email("test@test.com")
                .certCode("asdqwe")
                .branch(branch)
                .state("I")
                .build();

        employRepository.save(employ);

        Member employee = Member.builder()
                            .id("test_employee")
                            .password("1234")
                            .name("근무자")
                            .phone("01012345678")
                            .address("서울")
                            .detailAddress("강남")
                            .zipCode("12345")
                            .email("test@test.com")
                            .build();

        MemberAddRequestDto requestDto = new MemberAddRequestDto(employee);
        requestDto.setCertCode(employ.getCertCode());
        String content = objectMapper.writeValueAsString(requestDto);
        //when
        ResultActions result = mockMvc.perform(post("/member")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        result
            .andExpect(status().isOk())
            .andDo(document("Member-add-employee",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                            fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
                            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                            fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호"),
                            fieldWithPath("certCode").type(JsonFieldType.STRING).description("초대코드")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            fieldWithPath("data").type(JsonFieldType.STRING).description("ID")
                    )
            ));

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(1).getId()).isEqualTo(employee.getId());
        assertThat(memberList.get(1).getName()).isEqualTo(employee.getName());
    }


    @Test
    public void 멤버조회_테스트() throws Exception {
        //given
        Member target = Member.builder()
                .id("test")
                .password("1234")
                .name("테스트")
                .phone("01012341234")
                .state("A")
                .type("B")
                .build();

        memberRepository.save(target);
        //when
        ResultActions result = mockMvc.perform(get("/member/{id}", target.getId()));
        //then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(target.getId()))
            .andExpect(jsonPath("$.data.name").value(target.getName()))
            .andDo(document("Member-detail",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                            parameterWithName("id").description("아이디")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            subsectionWithPath("data").type(JsonFieldType.OBJECT).description("데이터")
                    )
            ));
    }


    @Test
    public void 멤버수정_테스트() throws Exception {
        //given
        Member member = Member.builder()
                .id("test")
                .password("1234")
                .name("테스트")
                .phone("01012345678")
                .state("A")
                .type("E")
                .build();

        Member target = Member.builder()
                .password("123456")
                .address("드림시큐리티")
                .detailAddress("5층")
                .zipCode("12345")
                .email("tset@test.com")
                .bankName("신한")
                .accountNum("12345111111")
                .build();

        memberRepository.save(member);

        String content = objectMapper.writeValueAsString(new MemberEditRequestDto(target));
        //when
        ResultActions result = mockMvc.perform(put("/member/{id}", member.getId())
                    .content(content).contentType(MediaType.APPLICATION_JSON_VALUE));
        result
                .andExpect(status().isOk())
                .andDo(document("Member-edit",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        requestFields(
                                fieldWithPath("password").type(JsonFieldType.STRING).ignored(),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("detailAddress").type(JsonFieldType.STRING).description("상세 주소"),
                                fieldWithPath("zipCode").type(JsonFieldType.STRING).description("우편 번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("bankName").type(JsonFieldType.STRING).description("은행 명").optional(),
                                fieldWithPath("accountNum").type(JsonFieldType.STRING).description("계좌 번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("ID")
                        )));
        //then
        Member memberResult = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        assertThat(memberResult.getAccountNum()).isEqualTo(target.getAccountNum());
        assertThat(memberResult.getBankName()).isEqualTo(target.getBankName());
    }

    @Test
    public void 멤버삭제_테스트() throws Exception {
        //given
        Member member = Member.builder()
                .id("test")
                .password("1234")
                .name("테스트")
                .phone("01012345678")
                .state("A")
                .type("E")
                .build();

        memberRepository.save(member);
        //when
        ResultActions result = mockMvc.perform(delete("/member/{id}", member.getId()));
        //then
        result
                .andExpect(status().isOk())
                .andDo(document("Member-remove",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터")
                        )
                ));
    }
}