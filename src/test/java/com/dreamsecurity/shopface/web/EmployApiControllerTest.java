package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.*;
import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.repository.*;
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

import java.util.ArrayList;
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

        memberRepository.save(businessman);
        memberRepository.save(employee);

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

        Role role = Role.builder().name("대리").branch(branch).build();
        Department department = Department.builder().name("인사").branch(branch).build();

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
                .email("test@test.com")
                .build();

        EmployAddRequestDto requestDto = new EmployAddRequestDto(employ);
        requestDto.setBranchNo(branch.getNo());
        requestDto.setRoleNo(role.getNo());
        requestDto.setDepartmentNo(department.getNo());

        String content = objectMapper.writeValueAsString(requestDto);
        //when
        ResultActions result = mockMvc.perform(post("/employ")
                .content(content).contentType(MediaType.APPLICATION_JSON));
        //then
        List<Employ> results = employRepository.findAll();
        assertThat(results.get(0).getName()).isEqualTo(employ.getName());
        assertThat(results.get(0).getBranch().getName()).isEqualTo(branch.getName());
        assertThat(results.get(0).getRole().getName()).isEqualTo(role.getName());
        assertThat(results.get(0).getDepartment().getName()).isEqualTo(department.getName());

        result
                .andExpect(status().isOk())
                .andDo(document("Employ-add",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("근무자 이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("branchNo").type(JsonFieldType.NUMBER).description("지점 번호"),
                                fieldWithPath("roleNo").type(JsonFieldType.NUMBER).description("역할 번호").optional(),
                                fieldWithPath("departmentNo").type(JsonFieldType.NUMBER).description("부서 번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("ID")
                        )
                ));
    }

    @Test
    public void 고용_목록조회_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        for (int i = 0; i < 3; i++) {
            Employ employ = Employ.builder()
                    .name("홍길동" + i)
                    .email("test@test.com")
                    .branch(branch)
                    .role(role)
                    .department(department)
                    .state("I")
                    .build();

            employRepository.save(employ);
        }
        //when
        ResultActions result = mockMvc.perform(get("/branch/{no}/employ", branch.getNo()));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name", is("홍길동0")))
                .andExpect(jsonPath("$.data[0].roleName", is("대리")))
                .andExpect(jsonPath("$.data[0].departmentName", is("인사")))
                .andDo(document("Employ-list",
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
    public void 고용_조회_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        Employ employ = Employ.builder()
                .name("홍길동")
                .email("test@test.com")
                .branch(branch)
                .role(role)
                .department(department)
                .state("I")
                .build();

        employRepository.save(employ);
        //when
        ResultActions result = mockMvc.perform(get("/employ/{no}", employ.getNo()));
        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(employ.getName()))
                .andDo(document("Employ-detail",
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
    public void 고용_수정_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        Role editRole = Role.builder().name("차장").branch(branch).build();
        Department editDepartment = Department.builder().name("개발").branch(branch).build();

        Employ employ = Employ.builder()
                .name("홍길동")
                .email("test@test.com")
                .branch(branch)
                .role(role)
                .department(department)
                .state("I")
                .build();

        employRepository.save(employ);
        roleRepository.save(editRole);
        departmentRepository.save(editDepartment);

        EmployEditRequestDto requestDto = new EmployEditRequestDto(
                10000, editRole.getNo(), editDepartment.getNo());

        String content = objectMapper.writeValueAsString(requestDto);
        //when
        ResultActions result = mockMvc.perform(put("/employ/{no}", employ.getNo())
                    .content(content).contentType(MediaType.APPLICATION_JSON));
        //then
        List<Employ> results = employRepository.findAll();
        assertThat(results.get(0).getSalary()).isEqualTo(requestDto.getSalary());
        assertThat(results.get(0).getRole().getName()).isEqualTo(editRole.getName());
        assertThat(results.get(0).getDepartment().getName()).isEqualTo(editDepartment.getName());

        result
                .andExpect(status().isOk())
                .andDo(document("Employ-edit",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("no").description("고용 번호")
                        ),
                        requestFields(
                                fieldWithPath("salary").type(JsonFieldType.NUMBER).description("시급").optional(),
                                fieldWithPath("roleNo").type(JsonFieldType.NUMBER).description("역할 번호").optional(),
                                fieldWithPath("departmentNo").type(JsonFieldType.NUMBER).description("부서 번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("ID")
                        )
                ));
    }

    @Test
    public void 고용_삭제_테스트() throws Exception {
        //given
        Branch branch = branchRepository.findAll().get(0);
        Role role = roleRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);

        Employ employ = Employ.builder()
                .name("홍길동")
                .email("test@test.com")
                .branch(branch)
                .role(role)
                .department(department)
                .state("I")
                .build();

        employRepository.save(employ);
        //when
        ResultActions result = mockMvc.perform(delete("/employ/{no}", employ.getNo()));
        //then
        result
                .andExpect(status().isOk())
                .andDo(document("Employ-remove",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("no").description("고용 번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터")
                        )
                ));
    }
}