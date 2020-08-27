package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.dto.occupation.OccupationAddRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationEditRequestDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.MemberRepository;
import com.dreamsecurity.shopface.repository.OccupationRepository;
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
@SpringBootTest
public class OccupationApiControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WebApplicationContext context;

    @Autowired
    OccupationRepository occupationRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    MemberRepository memberRepository;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        Member businessman = Member.builder()
                .id("test")
                .password("1234")
                .name("사업자")
                .phone("01012341234")
                .state("A")
                .type("B")
                .build();

        Branch branch = Branch.builder()
                .name("테스트")
                .phone("01012341234")
                .address("서울")
                .detailAddress("강남")
                .zipCode("11111")
                .member(businessman)
                .state("W")
                .build();

        memberRepository.save(businessman);
        branchRepository.save(branch);
    }

    @After
    public void tearDown() throws Exception {
        occupationRepository.deleteAll();
        branchRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 업무등록_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);

        String content = objectMapper.writeValueAsString(
                new OccupationAddRequestDto("업무", branch.getNo()));
        //when
        ResultActions result = mockMvc.perform(post("/occupation")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
        result
            .andExpect(status().isOk())
            .andDo(document("Occupation-Add",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                            fieldWithPath("name").type(JsonFieldType.STRING).description("업무 명"),
                            fieldWithPath("branchNo").type(JsonFieldType.NUMBER).description("지점 번호")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            fieldWithPath("data").type(JsonFieldType.NUMBER).description("ID")
                    )
            ));
        //then
        List<Occupation> results = occupationRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo("업무");
    }

    @Test
    public void 업무목록조회_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        for (int i = 0; i < 3; i++) {
            Occupation occupation = Occupation.builder().name("업무" + i).branch(branch).build();
            occupationRepository.save(occupation);
        }
        //when
        ResultActions result = mockMvc.perform(get("/branch/{no}/occupation", branch.getNo()));
        //then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].name", is("업무0")))
            .andExpect(jsonPath("$.data[1].name", is("업무1")))
            .andExpect(jsonPath("$.data[2].name", is("업무2")))
            .andDo(document("Occupation-List",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                            parameterWithName("no").description("지점 번호")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            subsectionWithPath("data.[]").type(JsonFieldType.ARRAY).description("데이터")
                    )
            ));
    }

    @Test
    public void 업무수정_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Occupation occupation = Occupation.builder()
                .name("업무")
                .branch(branch)
                .build();
        occupationRepository.save(occupation);

        String content = objectMapper.writeValueAsString(new OccupationEditRequestDto("업무수정"));
        //when
        ResultActions result = mockMvc.perform(put("/occupation/{no}", occupation.getNo())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
        result
            .andExpect(status().isOk())
            .andDo(document("Occupation-Edit",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                            parameterWithName("no").description("업무 번호")
                    ),
                    requestFields(
                            fieldWithPath("name").type(JsonFieldType.STRING).description("업무 명")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            fieldWithPath("data").type(JsonFieldType.NUMBER).description("ID")
                    )
            ));
        //then
        List<Occupation> results = occupationRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo("업무수정");
    }

    @Test
    public void 업무삭제_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Occupation occupation = Occupation.builder()
                .name("업무")
                .branch(branch)
                .build();

        occupationRepository.save(occupation);
        //when
        ResultActions result = mockMvc.perform(delete("/occupation/{no}", occupation.getNo()));
        result
            .andExpect(status().isOk())
            .andDo(document("Occupation-Remove", getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                            parameterWithName("no").description("업무 번호")
                    ),
                    responseFields(
                            fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                            fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                            fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터")
                    )
            ));
        //then
        List<Occupation> results = occupationRepository.findAll();
        assertThat(results.size()).isEqualTo(0);
    }
}