package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.department.DepartmentAddRequestDto;
import com.dreamsecurity.shopface.dto.department.DepartmentEditRequestDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.DepartmentRepository;
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
import org.springframework.test.web.servlet.ResultActions;
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
public class DepartmentApiControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WebApplicationContext context;

    @Autowired
    DepartmentRepository departmentRepository;

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
        departmentRepository.deleteAll();
        branchRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 부서등록_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);

        String content = objectMapper.writeValueAsString(
                new DepartmentAddRequestDto("부서", branch.getNo()));
        //when
        mockMvc.perform(post("/department")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Department-Add"));
        //then
        List<Department> results = departmentRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo("부서");
    }

    @Test
    public void 부서목록조회_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        for (int i = 0; i < 3; i++) {
            Department department = Department.builder().name("부서" + i).branch(branch).build();
            departmentRepository.save(department);
        }
        //when
        ResultActions result = mockMvc.perform(get("/branch/" + branch.getNo() + "/department"));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name", is("부서0")))
                .andExpect(jsonPath("$.data[1].name", is("부서1")))
                .andExpect(jsonPath("$.data[2].name", is("부서2")))
                .andDo(document("Department-List"));
    }

    @Test
    public void 부서수정_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Department department = Department.builder()
                .name("부서")
                .branch(branch)
                .build();
        departmentRepository.save(department);

        String content = objectMapper.writeValueAsString(new DepartmentEditRequestDto("부서수정"));
        //when
        mockMvc.perform(put("/department/" + department.getNo())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("Department-Edit"));
        //then
        List<Department> results = departmentRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo("부서수정");
    }

    @Test
    public void 부서삭제_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Department department = Department.builder()
                .name("부서")
                .branch(branch)
                .build();
        departmentRepository.save(department);
        //when
        mockMvc.perform(delete("/department/" + department.getNo()))
                .andExpect(status().isOk())
                .andDo(document("Department-Remove"));
        //then
        List<Department> results = departmentRepository.findAll();
        assertThat(results.size()).isEqualTo(0);
    }
}