package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.branch.BranchAddRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchEditRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;
import com.dreamsecurity.shopface.dto.branch.BranchResponseDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                .member(businessman)
                .state("W")
                .build();

        String content = objectMapper.writeValueAsString(new BranchAddRequestDto(branch));

        //when
        mockMvc.perform(post("/branch")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document("Branch-Add"));
        //then
        List<Branch> results = branchRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo(branch.getName());
        assertThat(results.get(0).getMember().getId()).isEqualTo(businessman.getId());
    }

    @Test
    public void 지점_목록_테스트() throws Exception {
        //given
        Member businessman = memberRepository.findAll().get(0);
        List<Branch> samples = new ArrayList<>();
        List<BranchListResponseDto> resultLists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Branch branch = Branch.builder()
                    .name("테스트" + i)
                    .phone("0101234123" + i)
                    .address("서울")
                    .detailAddress("강남")
                    .zipCode("11111")
                    .member(businessman)
                    .state("W")
                    .build();

            samples.add(branch);
        }

        branchRepository.saveAll(samples);
        for (Branch result : branchRepository.findAll()) {
            resultLists.add(new BranchListResponseDto(result));
        }
        String content = objectMapper.writeValueAsString(resultLists);
        //when
        mockMvc.perform(get("/branch/member/" + businessman.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(document("Branch-list"));
        //then
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

        Long no = branchRepository.save(branch).getNo();
        Branch result = branchRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다."));

        String content = objectMapper.writeValueAsString(new BranchResponseDto(result));
        //when
        mockMvc.perform(get("/branch/" + branch.getNo()))
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(document("Branch-detail"));
        //then
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

        String content = objectMapper.writeValueAsString(new BranchEditRequestDto(target));
        Long no = branchRepository.save(branch).getNo();
        //when
        mockMvc.perform(put("/branch/" + no)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Branch-edit"));
        //then
        List<Branch> results = branchRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo(target.getName());
        assertThat(results.get(0).getAddress()).isEqualTo(target.getAddress());
        assertThat(results.get(0).getDetailAddress()).isEqualTo(target.getDetailAddress());
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
        mockMvc.perform(delete("/branch/" + branch.getNo()))
                .andExpect(status().isOk())
                .andDo(document("Branch-remove"));
        //then
    }
}