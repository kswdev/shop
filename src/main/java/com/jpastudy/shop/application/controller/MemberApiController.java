package com.jpastudy.shop.application.controller;

import com.jpastudy.shop.domain.member.entity.Member;
import com.jpastudy.shop.domain.member.repository.MemberRepository;
import com.jpastudy.shop.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        List<Member> members = memberService.findMembers();
        return members;
    }

    @GetMapping("/api/v2/members")
    public ApiResponse<List<MemberDTO>> membersV2() {
        List<Member> members = memberService.findMembers();
        List<MemberDTO> memberDTOs = members.stream()
                .map(m -> new MemberDTO(m.getUsername()))
                .toList();

        return new ApiResponse<>(memberDTOs);
    }

    @Data
    @AllArgsConstructor
    private static class MemberDTO {
        private String name;
    }
    @Data
    @AllArgsConstructor
    private static class ApiResponse<T> {
        private T data;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setUsername(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getUsername());
    }

    @Data
    private static class UpdateMemberRequest {
        private String name;
    }
    @Data
    @AllArgsConstructor
    private static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    private static class CreateMemberRequest {
        private String name;
    }

    @Data
    private static class CreateMemberResponse {
        private Long id;

        private  CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
