package com.fullstack.secure.club;

import com.fullstack.secure.club.entity.ClubMember;
import com.fullstack.secure.club.repository.ClubmemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;



@SpringBootTest
public class ClubMemberTests {

    @Autowired
    private ClubmemberRepository clubmemberRepository;
    //암호는 반드시 Encoding 되어야 합니다. 스프링 Security 사용시엔..
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
//    public void insertMembers() {
//        //1-80 USER;
//        //81-90 MEMBER;
//        //나머지 ADMIN 부여
//
//        IntStream.rangeClosed(1,100).forEach(i -> {
//            ClubMember clubMember = ClubMember.builder()
//                    .email("user" + i + "@abc.com")
//                    .name("고길동" + i)
//                    .password(passwordEncoder.encode("1234"))
//                    .fromSocial(false)
//                    .build();
//            //Role 적용..
//            clubMember.addMemberRole(ClubmemberRole.USER);
//
//            if(i > 80){
//                clubMember.addMemberRole(ClubmemberRole.MANAGER);
//            }
//            if(i > 90){
//                clubMember.addMemberRole(ClubmemberRole.ADMIN);
//            }
//            clubmemberRepository.save(clubMember);
//        });
//    }

    //Email ID 와 SNS 로그인 여부를 통한 사용자 정보 얻기 테스트
    @Test
    public void testRead(){
        Optional<ClubMember> result = clubmemberRepository.findByEmail("user100@abc.com",false);
        System.out.println(result.get());

        /*
        스프링큐어를 사용 할때 기억해야 할 것...

        1.개발자가 원하는 방식으로 로그인을 처리 하려면 반드시 구현 해야하는
        인터페이스가 있는데, 이름이 UserDetailInterface 입니다.

        2.이 인터페이스에는 사용자의 정보를 이름(username) 으로 받아서 정보객체를
        리턴하는 UserDetail 이라는 객체를 리턴하도록 되어 있습니다.

        3.UserDetail 내에는 사용자의 인증정보 및 인가(Permit) 정보등을 확인 할 수
        있는 메서드가 다수 포함되어 있습니다.
        4.2번에서 사용된 username 은 특정 객체에 속한 username 이 아니라
        PK 로 사용되는 값을 의미 함.. 이는 보통 오전에 예제에서 사용한 User 객체의
        메서드를 보면 알수 있습니다.

        5.4의 의미를 되돌리면, 스프링에는 USER 라는 객체가 있고, 그 객체엔
        사용자의 식별정보(username) 이 있고, 내부에 암호등을 담고 있는 필드가 있습니다.

        6.다시 순서를 정의 하면, 일단 User의 이름으로 User 가 존재하는지를 찾고

        7.존재 한다면, 암호를 검증하고(틀리면 인증오류 등의 메세지 출력), 인증이 되면
        이후 Permit 을 주는 순서로 이루어져있습니다.
         */
    }


}
