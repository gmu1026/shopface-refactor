package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.domain.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmRepositoryTest {
    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    MemberRepository memberRepository;

    @Before
    public void setUp() {
        Member entity = Member.builder()
                .id("test")
                .password("asd123")
                .name("테스트")
                .phone("01012345678")
                .state("A")
                .type("E")
                .build();

        memberRepository.save(entity);
    }

    @After
    public void cleanUp() {
        alarmRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 알림_저장_테스트() {
        //given
        Member member = Member.builder()
                .id("test")
                .password("1234")
                .name("테스트")
                .phone("01023451234")
                .state("A")
                .type("E")
                .build();

        String type = "등록";
        String contents = "알람이 등록되었습니다.";

        memberRepository.save(member);

        alarmRepository.save(Alarm.builder()
                .member(member)
                .type(type)
                .contents(contents)
                .checkState("C")
                .build());

        //when
        List<Alarm> alarmList = alarmRepository.findAll();

        //then
        Alarm alarm = alarmList.get(0);
        assertThat(alarm.getType()).isEqualTo(type);
        assertThat(alarm.getContents()).isEqualTo(contents);
        assertThat(alarm.getMember().getId()).isEqualTo(member.getId());
    }
}