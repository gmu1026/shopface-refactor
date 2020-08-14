package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.member.MemberAddRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberEditRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberListResponseDto;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    EmployRepository employRepository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @After
    public void tearDown() {
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
        mockMvc.perform(get("/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is("test0")))
                .andExpect(jsonPath("$.data[1].id", is("test1")))
                .andExpect(jsonPath("$.data[2].id", is("test2")))
                .andDo(document("Member-list"));
        //then
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

        String content = objectMapper.writeValueAsString(new MemberAddRequestDto(businessman));

    // when
    mockMvc
        .perform(
            post("/member")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(document("Member-add-businessman"));
        //then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(0).getId()).isEqualTo(businessman.getId());
        assertThat(memberList.get(0).getName()).isEqualTo(businessman.getName());
    }

    @Test
    public void 근무자등록_테스트() throws Exception {
        //given
        Employ employ = Employ.builder()
                .name("근무자")
                .email("test@test.com")
                .certCode("asdqwe")
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
        mockMvc.perform(
                post("/member")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        )
        .andExpect(status().isOk())
        .andDo(document("Member-add-employee"));

        //then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(0).getId()).isEqualTo(employee.getId());
        assertThat(memberList.get(0).getName()).isEqualTo(employee.getName());
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
        mockMvc.perform(get("/member/" + target.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(target.getId()))
                .andExpect(jsonPath("$.data.name").value(target.getName()))
                .andDo(document("Member-get"));
        //then
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
        mockMvc.perform(put("/member/" + member.getId())
                    .content(content).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document("Member-edit"));
        //then
        Member result = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        assertThat(result.getPassword()).isEqualTo(target.getPassword());
        assertThat(result.getAccountNum()).isEqualTo(target.getAccountNum());
        assertThat(result.getBankName()).isEqualTo(target.getBankName());
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
        mockMvc.perform(delete("/member/" + member.getId()))
                .andExpect(status().isOk())
                .andDo(document("Member-remove"));
        //then
    }
}