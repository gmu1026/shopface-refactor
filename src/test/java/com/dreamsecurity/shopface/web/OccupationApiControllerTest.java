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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OccupationApiControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

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
        mockMvc.perform(post("/occupation")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Occupation-Add"));
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
        mockMvc.perform(get("/branch/" + branch.getNo() + "/occupation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name", is("업무0")))
                .andExpect(jsonPath("$.data[1].name", is("업무1")))
                .andExpect(jsonPath("$.data[2].name", is("업무2")))
                .andDo(document("Occupation-List"));
        //then
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
        mockMvc.perform(put("/occupation/" + occupation.getNo())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Occupation-Edit"));
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
        mockMvc.perform(delete("/occupation/" + occupation.getNo()))
                .andExpect(status().isOk())
                .andDo(document("Occupation-Remove"));
        //then
        List<Occupation> results = occupationRepository.findAll();
        assertThat(results.size()).isEqualTo(0);
    }
}