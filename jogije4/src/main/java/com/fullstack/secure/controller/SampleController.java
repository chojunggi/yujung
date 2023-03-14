package com.fullstack.secure.controller;

import com.fullstack.secure.dto.ClubAuthMemberDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/sample/exAll")
    public void exAll() {

    }
    @GetMapping("/sample/exMember")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        System.out.println("+++++++++++인증된 사용자 정보+++++++++++ : " + clubAuthMemberDTO);
    }
    //Secure 가 적용된 SpringBoot 에서 UserServiceDetail 을 이용한 경우
    //DTO 의 정보를 View Controller 단에서 확인하는 가장 간편한 방법은 아래
    //@AuthenticationPrincipal 이라는 객체를 파람 선언하면 됩니다.
    @GetMapping("/sample/exAdmin")
    public void exAdmin(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        System.out.println("+++++++++++인증된 사용자 정보+++++++++++ : " + clubAuthMemberDTO);

    }

    //AuthenticationManager(인증작업의 처리 관리자) -->
    //AuthenticationProvider(인증의 종류 즉 인증 방식을 구현한 객체를 선택)
    //선택된 인증방식을 통해서 인증될 인증 정보객체인 UserDetailService 객체를 호출해서
    //인증 작업을 시도하게 됩니다.

}
