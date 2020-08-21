package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.branch.BranchAddRequestDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BranchApiControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    MemberRepository memberRepository;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        Member member = Member.builder()
                .id("test")
                .password("1234")
                .name("테스트")
                .phone("01012341234")
                .state("A")
                .type("B")
                .build();

        memberRepository.save(member);
    }

    @After
    public void tearDown() throws Exception {
        branchRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 지점_등록_테스트() throws Exception {
        //given
        Member businessman = memberRepository.findAll().get(0);

        Branch branch = Branch.builder()
                .name("테스트")
                .phone("01012341234")
                .address("서울")
                .detailAddress("강남")
                .zipCode("11111")
                .build();

        BranchAddRequestDto requestDto = new BranchAddRequestDto(branch);
        requestDto.setMemberId("test");

        String content = objectMapper.writeValueAsString(requestDto);

        //when
        ResultActions result = mockMvc.perform(post("/branch")
                    .content(content).contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        List<Branch> results = branchRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo(branch.getName());
        assertThat(results.get(0).getMember().getId()).isEqualTo(businessman.getId());

        result
                .andExpect(status().isOk())
                .andDo(document("Branch-add",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("지점 명"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("detailAddress").type(JsonFieldType.STRING).description("상세 주소"),
                                fieldWithPath("zipCode").type(JsonFieldType.STRING).description("우편 번호"),
                                fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("ID")
                        )
                ));
    }

    @Test
    public void 지점_목록_테스트() throws Exception {
        //given
        Member businessman = memberRepository.findAll().get(0);
        for (int i = 0; i < 3; i++) {
            Branch branch = Branch.builder()
                    .name("테스트" + i)
                    .phone("0101234123" + i)
                    .address("서울")
                    .detailAddress("강남")
                    .zipCode("11111")
                    .member(businessman)
                    .build();
            branchRepository.save(branch);
        }
        //when
        ResultActions result = mockMvc.perform(get("/member/{id}/branch", businessman.getId()));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name", is("테스트0")))
                .andExpect(jsonPath("$.data[1].name", is("테스트1")))
                .andExpect(jsonPath("$.data[2].name", is("테스트2")))
                .andDo(document("Branch-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("사업자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                subsectionWithPath("data.[]").type(JsonFieldType.ARRAY).description("데이터")
                        )
                ));
    }

    @Test
    public void 지점_조회_테스트() throws Exception {
        //given
        Member businessman = memberRepository.findAll().get(0);

        Branch branch = Branch.builder()
                .name("테스트")
                .phone("01012341234")
                .address("서울")
                .detailAddress("강남")
                .zipCode("11111")
                .member(businessman)
                .state("W")
                .build();

        branchRepository.save(branch);
        //when
        ResultActions result = mockMvc.perform(get("/branch/{no}", branch.getNo()));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("테스트")))
                .andDo(document("Branch-detail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("no").description("지점 번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                subsectionWithPath("data").type(JsonFieldType.OBJECT).description("데이터")
                        )
                ));
    }

    @Test
    public void 지점_수정_테스트() throws Exception {
        //given
        Member businessman = memberRepository.findAll().get(0);

        Branch branch = Branch.builder()
                .name("테스트")
                .phone("01012341234")
                .address("서울")
                .detailAddress("강남")
                .zipCode("11111")
                .member(businessman)
                .state("W")
                .build();

        Branch target = Branch.builder()
                .name("테스트1")
                .address("서울")
                .detailAddress("판교")
                .zipCode("12345")
                .build();

        branchRepository.save(branch);
        //when
        ResultActions result = mockMvc.perform(put("/branch/{no}", branch.getNo())
                    .param("name", "테스트1")
                    .param("address", "서울")
                    .param("detailAddress", "판교")
                    .param("zipCode", "12345")
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
        //then
        List<Branch> results = branchRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo(target.getName());
        assertThat(results.get(0).getAddress()).isEqualTo(target.getAddress());
        assertThat(results.get(0).getDetailAddress()).isEqualTo(target.getDetailAddress());

        result
                .andExpect(status().isOk())
                .andDo(document("Branch-edit",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("no").description("지점 번호")
                        ),
                        requestParameters(
                                parameterWithName("name").description("지점 명"),
                                parameterWithName("address").description("주소"),
                                parameterWithName("detailAddress").description("상세 주소"),
                                parameterWithName("zipCode").description("우편 번호"),
                                parameterWithName("businessLicenseImage").description("사업자 등록증 이미지").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("ID")
                        )
                ));
    }

    @Test
    public void 지점_삭제_테스트() throws Exception {
        //given
        Member businessman = memberRepository.findAll().get(0);

        Branch branch = Branch.builder()
                .name("테스트")
                .phone("01012341234")
                .address("서울")
                .detailAddress("강남")
                .zipCode("11111")
                .member(businessman)
                .state("W")
                .build();

        branchRepository.save(branch);
        //when
        ResultActions result = mockMvc.perform(delete("/branch/{no}", branch.getNo()));
        //then
        result
                .andExpect(status().isOk())
                .andDo(document("Branch-remove", 
                        getDocumentRequest(), 
                        getDocumentResponse(), 
                        pathParameters(
                                parameterWithName("no").description("지점 번호")
                        ), 
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터")
                        )
                ));
    }
}