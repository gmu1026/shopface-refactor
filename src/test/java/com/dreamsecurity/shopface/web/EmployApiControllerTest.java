package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.*;
import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployListResponse;
import com.dreamsecurity.shopface.dto.employ.EmployResponseDto;
import com.dreamsecurity.shopface.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;
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

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployApiControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    EmployRepository employRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        Member businessman = Member.builder()
                .id("test_businessman")
                .password("1234")
                .name("사업자")
                .phone("01012341234")
                .state("A")
                .type("B")
                .build();

        Member employee = Member.builder()
                .id("test_employee")
                .password("1234")
                .name("근무자")
                .phone("01044454446")
                .state("A")
                .type("E")
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

        Role role = Role.builder().name("대리").branch(branch).build();
        Department department = Department.builder().name("인사").branch(branch).build();

        memberRepository.save(businessman);
        memberRepository.save(employee);
        branchRepository.save(branch);
        roleRepository.save(role);
        departmentRepository.save(department);
    }

    @After
    public void tearDown() throws Exception {
        employRepository.deleteAll();
        roleRepository.deleteAll();
        departmentRepository.deleteAll();
        branchRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 고용_등록_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);
        Employ employ = Employ.builder()
                .name("홍길동")
                .branch(branch)
                .role(role)
                .department(department)
                .build();

        String content = objectMapper.writeValueAsString(new EmployAddRequestDto(employ));
        //when
        mockMvc.perform(post("/employ").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Employ-add"));
        //then
        List<Employ> results = employRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo(employ.getName());
        assertThat(results.get(0).getBranch().getName()).isEqualTo(branch.getName());
        assertThat(results.get(0).getRole().getName()).isEqualTo(role.getName());
        assertThat(results.get(0).getDepartment().getName()).isEqualTo(department.getName());
    }

    @Test
    public void 고용_목록조회_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        List<Employ> samples = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Employ employ = Employ.builder()
                    .name("홍길동" + i)
                    .branch(branch)
                    .role(role)
                    .department(department)
                    .build();

            samples.add(employ);
        }

        employRepository.saveAll(samples);

        List<EmployListResponse> results = new ArrayList<>();
        for (Employ result : employRepository.findAll()) {
            results.add(new EmployListResponse(result));
        }

        String content = objectMapper.writeValueAsString(results);
        //when
        mockMvc.perform(get("/employ/branch/" + branch.getNo()))
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(document("Employ-list"));
        //then
    }

    @Test
    public void 고용_조회_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        Employ employ = Employ.builder()
                .name("홍길동")
                .branch(branch)
                .role(role)
                .department(department)
                .build();

        employRepository.save(employ);

        String content = objectMapper.writeValueAsString(new EmployResponseDto(employ));
        //when
        mockMvc.perform(get("/employ/" + employ.getNo()))
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(document("Employ-detail"));
        //then
    }

    @Test
    public void 고용_수정_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        Role editRole = Role.builder().name("차장").branch(branch).build();
        Department editDepartment = Department.builder().name("개발").branch(branch).build();

        Employ employ = Employ.builder()
                .name("홍길동")
                .branch(branch)
                .role(role)
                .department(department)
                .build();

        long no = employRepository.save(employ).getNo();
        roleRepository.save(editRole);
        departmentRepository.save(editDepartment);

        Employ target = Employ.builder()
                .salary(10000L)
                .role(editRole)
                .department(editDepartment)
                .build();

        String content = objectMapper.writeValueAsString(new EmployEditRequestDto(target));
        //when
        mockMvc.perform(put("/employ/" + no)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Employ-edit"));
        //then
        List<Employ> results = employRepository.findAll();
        assertThat(results.get(0).getSalary()).isEqualTo(target.getSalary());
        assertThat(results.get(0).getRole().getName()).isEqualTo(target.getRole().getName());
        assertThat(results.get(0).getDepartment().getName()).isEqualTo(target.getDepartment().getName());
    }

    @Test
    public void 고용_삭제_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        Employ employ = Employ.builder()
                .name("홍길동")
                .branch(branch)
                .role(role)
                .department(department)
                .build();

        employRepository.save(employ);
        //when
        mockMvc.perform(delete("/employ/" + employ.getNo()))
                .andExpect(status().isOk())
                .andDo(document("Employ-remove"));
        //then
    }
}