package com.fullstack.secure.service;

import com.fullstack.secure.club.entity.ClubMember;
import com.fullstack.secure.club.entity.ClubmemberRole;
import com.fullstack.secure.club.repository.ClubmemberRepository;
import com.fullstack.secure.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuthUserDetailService extends DefaultOAuth2UserService {

    private final ClubmemberRepository clubmemberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("++++++++++++++++Open Auth 서비스 수행됨++++++++++++++++");
        System.out.println("출력문..요청유저 정보" + userRequest);

        System.out.println("토큰 : " + userRequest.getAccessToken());
        System.out.println("레지스트리정보" + userRequest.getClientRegistration());
        System.out.println("파라미터 정보 : " + userRequest.getAdditionalParameters().size());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((k, v) -> {
            System.out.println("Key : " + k + ", Value : " + v);
        });
        /*소셜로 로그인한 사람을 DB Insert 시킵니다.
        GOOGLE, EMail,,,,,,

        1.Entity 생성해야 합니다.(ClubMember)
          1_1. 암호 생성 --> Encoding
          1_2. 이름 필요..생성
        2.Repository 이용해서 save(Entity)
        3.다중 SNS Secure 를 이용한다면, SNS 정보를 기준으로 처리 작업도 필요합니다.
         */
        //SNS 유형 정보를 얻어냅니다.
        String whichSns = userRequest.getClientRegistration().getClientName();

        String email = null;

        //구글에서 인증한 정보라면,,
        if(whichSns.equals("Google")){
            //email 가져와서 세팅합니다.
            email = oAuth2User.getAttribute("email");
        }

       ClubMember clubMember = saveClubMember(email);

        //Entity 를 DTO 로 변환하는데, 일반 DTO 가 아닌 OAuth2User 타입의 DTO 로 변환
        //해야 합니다. 바로 전까지는 Spring 의 Secure 에서 사용하는 DTO 만 정의 했는데
        //(User 상속 받은), 지금은 위 DTO 에 OAuth2User 인터페이스를 추가 상속 시켜서
        //OAuth2User 타입으로 변경 하였고, DTO 객체를 생성하기 위해 생성자를 이용해야 합니다.
        ClubAuthMemberDTO authMemberDTO
                = new ClubAuthMemberDTO(clubMember.getEmail()
                , clubMember.getPassword()
                , clubMember.isFromSocial()
                , clubMember.getRoleset().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet()),
                oAuth2User.getAttributes()
        );//생성자 종료..

        authMemberDTO.setName(clubMember.getName());

        return authMemberDTO;
//        return super.loadUser(userRequest);
    }

    //여기에는 SNS 로 로그인한 사용자를 DB 에서 조회후 존재 여부를 확인 후 없는 사용자면
    //일단 Insert 시키는 로직을 메서드로 구현합니다.
    //또한 Insert 된 사용자 정보는 Controller 로 넘어가야 하기때문에 Entity 를 리턴
    //하도록 합니다.

    private ClubMember saveClubMember(String email){

        Optional<ClubMember> result = clubmemberRepository.findByEmail(email, true);

        //만약 존재하면 해당 Entity Return
        if(result.isPresent()){
            return result.get();
        }

        //존재하지 않으면 Insert 작업시작...단 암호는 인코등해서..
        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name("섭섭")
                .fromSocial(true)
                .password(passwordEncoder.encode("1234"))
                .build();

        //Role 정보도 셋업합니다.
        clubMember.addMemberRole(ClubmemberRole.ADMIN);

        clubmemberRepository.save(clubMember);

        return clubMember;
    }
}
