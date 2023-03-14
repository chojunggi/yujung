package com.fullstack.secure.service;

import com.fullstack.secure.club.entity.ClubMember;
import com.fullstack.secure.club.repository.ClubmemberRepository;
import com.fullstack.secure.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubMemberService implements UserDetailsService {

    //DB 에서 사용자 정보를 가져와야 하기때문에 Repository 를 사용합니다.
    private final ClubmemberRepository clubmemberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //서비스에 등록을 하면, 인증을시도한 사용자의 정보를 컨트롤러에서
        //이 메서드를 호출 하면서 값을 넣어주게 됩니다.
        //그럼 다음 작업으로는 username 값으로 DB 에서 사용자를 조회하고
        //등록된 사용자라면, User 를 상속받은 UserDTO 에 사용자 정보를 Setting
        //하고, 권한을 등록하면 됩니다.
        //이렇게 생성된 DTO 객체는 다시 컨트롤러에서 사용 가능하고요.

        System.out.println("로그인 요청된 유저 정보 : " + username);

        //DB에서 사용자 정보를 추출할게요.
        Optional<ClubMember> reslut = clubmemberRepository.findByEmail(username, false);
        if(!reslut.isPresent()){
            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없음");
        }

        //사용자가 존재 하는 경우엔, User 타입의 DTO 객체에 데이터를 Setting 하고
        //서비스로 되돌립니다.
        ClubMember clubMember = reslut.get();

        System.out.println("찾아온 사용자 정보 : " + clubMember);

        ClubAuthMemberDTO authMemberDTO
                = new ClubAuthMemberDTO(clubMember.getEmail()
                  , clubMember.getPassword()
                  , clubMember.isFromSocial()
                  , clubMember.getRoleset().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet())
                );//생성자 종료..

        authMemberDTO.setName(clubMember.getName());
        authMemberDTO.setFromSocial(clubMember.isFromSocial());

        return authMemberDTO;
    }
}
