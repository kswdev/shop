package com.jpastudy.shop.domain.member.service;

import com.jpastudy.shop.domain.member.entity.Member;
import com.jpastudy.shop.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    void joinTest() {
        // Given
        Member member = new Member();
        member.setUsername("kim");

        // When
        Long saveId = memberService.join(member);

        // Then
        assertEquals(member, memberRepository.find(saveId));
    }

    @Test
    public void duplicateTest() {
        // Given
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");

        // When
        memberService.join(member1);

        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        // Then
        fail("에러 발생");
    }
}