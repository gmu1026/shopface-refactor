package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.*;
import com.dreamsecurity.shopface.enums.ScheduleColor;
import com.dreamsecurity.shopface.enums.ScheduleState;
import com.dreamsecurity.shopface.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessmanDashBoardApiControllerTest {
//    @Rule
//    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    WebApplicationContext context;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    ScheduleRepository scheduleRepository;
//
//    @Autowired
//    EmployRepository employRepository;
//
//    @Autowired
//    OccupationRepository occupationRepository;
//
//    @Autowired
//    BranchRepository branchRepository;
//
//    @Autowired
//    RecordRepository recordRepository;
//
//    @Autowired
//    BusinessmanDashBoardRepository businessmanDashBoardRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation))
//                .build();
//
//        // Member business
//        Member business = Member.builder()
//                .id("test")
//                .password("1234")
//                .name("김사업")
//                .phone("01012341234")
//                .state("A")
//                .type("B")
//                .build();
//        memberRepository.save(business);
//        // Branch CU?
//        Branch branch = Branch.builder()
//                .name("CU서울점")
//                .phone("01012341234")
//                .address("서울")
//                .detailAddress("강남")
//                .zipCode("11111")
//                .member(business)
//                .state("Y")
//                .build();
//        branchRepository.save(branch);
//        // Member employee
//        Member employee = Member.builder()
//                .id("emp01")
//                .password("1234")
//                .name("박알바")
//                .phone("01012341234")
//                .state("A")
//                .type("E")
//                .build();
//        memberRepository.save(employee);
//
//        // Occupation cleaning
//        Occupation occupation = Occupation.builder()
//                .name("청소")
//                .branch(branch)
//                .build();
//        occupationRepository.save(occupation);
//        // Employ employ (Cu에 employee가)
//        Employ employ = Employ.builder()
//                .member(employee)
//                .name("박알바")
//                .email("test@test.com")
//                .branch(branch)
//                .salary(9000)
//                .role(null)
//                .department(null)
//                .state("I")
//                .build();
//        employRepository.save(employ);
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        recordRepository.deleteAll();;
//        scheduleRepository.deleteAll();
//        employRepository.deleteAll();
//        occupationRepository.deleteAll();
//        branchRepository.deleteAll();
//        memberRepository.deleteAll();
//    }
//
//    @Test
//    public void testBusinessmanDashBoardWorkDone() throws Exception{
//        Member business = memberRepository.findAll().get(0);
//        Member employee = memberRepository.findAll().get(1);
//        Branch branch = branchRepository.findAll().get(0);
//        Occupation occupation = occupationRepository.findAll().get(0);
//
//        Schedule schedule = Schedule.builder()
//                .member(employee)
//                .branch(branch)
//                .occupation(occupation)
//                .workStartTime(LocalDateTime.of(2020, 8, 18, 10, 0, 0))
//                .workEndTime(LocalDateTime.of(2020, 8, 18, 12, 0,0 ))
//                .color(ScheduleColor.LIGHTGREEN.getColorCode())
//                .state(ScheduleState.COMPLETE.getState())
//                .build();
//        scheduleRepository.save(schedule);
//
//        Record record = Record.builder()
//                .businessmanId(business.getId())
//                .businessmanName(business.getName())
//                .businessmanPhone(business.getPhone())
//                .branchNo(branch.getNo())
//                .branchName(branch.getName())
//                .branchPhone(branch.getPhone())
//                .occupationName(occupation.getName())
//                .memberId(employee.getId())
//                .memberName(employee.getName())
//                .memberPhone(employee.getPhone())
//                .workStartTime(schedule.getWorkStartTime())
//                .workEndTime(schedule.getWorkEndTime())
//                .workingTime(LocalDateTime.of(2020,8,18,9,58,20))
//                .quittingTime(LocalDateTime.of(2020,8,18,12,2,1))
//                .build();
//        recordRepository.save(record);
//
//        //when
//        mockMvc.perform(get("/businessman/" + business.getId() + "/branch/" + branch.getNo() + "/C"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].quittingTime", is("2020-08-18T12:02:01")))
//                .andDo(document("BusinessmanDashBoard_Scheduled"));
//        //then
//    }
//
//    @Test
//    public void testBusinessmanDashBoardWorking() throws Exception{
//        Member business = memberRepository.findAll().get(0);
//        Member member = memberRepository.findAll().get(1);
//        Branch branch = branchRepository.findAll().get(0);
//        Occupation occupation = occupationRepository.findAll().get(0);
//
//        Schedule schedule = Schedule.builder()
//                .member(member)
//                .branch(branch)
//                .occupation(occupation)
//                .color(ScheduleColor.BLUE.getColorCode())
//                .workStartTime(LocalDateTime.of(2020,8,19,11,0,0))
//                .workEndTime(LocalDateTime.of(2020,8,19,13,0,0))
//                .state(ScheduleState.GO_WORKING.getState())
//                .build();
//        scheduleRepository.save(schedule);
//
//        Record record = Record.builder()
//                .businessmanId(business.getId())
//                .businessmanName(business.getName())
//                .businessmanPhone(business.getPhone())
//                .branchNo(branch.getNo())
//                .branchName(branch.getName())
//                .branchPhone(branch.getPhone())
//                .memberId(member.getId())
//                .memberName(member.getName())
//                .memberPhone(member.getPhone())
//                .occupationName(occupation.getName())
//                .workStartTime(schedule.getWorkStartTime())
//                .workEndTime(schedule.getWorkEndTime())
//                .workingTime(LocalDateTime.of(2020,8,19,11,1,10))
//                .quittingTime(null)
//                .build();
//        recordRepository.save(record);
//
//        //when
//        mockMvc.perform(get("/businessman/" + business.getId() + "/branch/" + branch.getNo() + "/W"))
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.data[0].workingTime", is("2020-08-19T11:01:10")))
//                .andDo(document("BusinessmanDashBoard_Working"));
//        //then
//    }
//
//    @Test
//    public void testBusinessmanDashBoardScheduled() throws Exception{
//        Member business = memberRepository.findAll().get(0);
//        Member member = memberRepository.findAll().get(1);
//        Branch branch = branchRepository.findAll().get(0);
//        Occupation occupation = occupationRepository.findAll().get(0);
//
//        List<LocalDateTime> times = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            LocalDateTime startTime = LocalDateTime.of(2020, 8, 20, i, 0,0);
//            times.add(startTime);
//
//            Schedule schedule = Schedule.builder()
//                    .member(member)
//                    .branch(branch)
//                    .occupation(occupation)
//                    .color(ScheduleColor.BLUE.getColorCode())
//                    .workStartTime(startTime)
//                    .workEndTime(LocalDateTime.of(2020,8,20,i+1,0,0))
//                    .state(ScheduleState.REGISTER.getState())
//                    .build();
//            scheduleRepository.save(schedule);
//        }
//
//        //when
//        mockMvc.perform(get("/businessman/" + business.getId() + "/branch/" + branch.getNo() + "/R"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.data[0].workStartTime", is("2020-08-20T00:00:00")))
//                .andExpect(jsonPath("$.data[1].workStartTime", is("2020-08-20T01:00:00")))
//                .andExpect(jsonPath("$.data[2].workStartTime", is("2020-08-20T02:00:00")))
//                .andExpect(jsonPath("$.data[3].workStartTime", is("2020-08-20T03:00:00")))
//                .andExpect(jsonPath("$.data[4].workStartTime", is("2020-08-20T04:00:00")))
//                .andDo(document("BusinessmanDashBoard_Scheduled"));
//        //then
//    }
}