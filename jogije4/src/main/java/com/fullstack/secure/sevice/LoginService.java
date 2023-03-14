package com.fullstack.secure.sevice;

import com.fullstack.secure.entity.Member;
import com.fullstack.secure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
Service 객체가 구현할 내용 : MemberRepository 에서 회원 조회 후, 파라미터로 넘어오는 password
와 비교해서 같으면 Member 인스턴스를 넘기기로, 아닌경우 null 을 리턴하도록 정의 합니다.
 */
@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m->m.getPassword().equals(password)).orElse(null);
    }
}
