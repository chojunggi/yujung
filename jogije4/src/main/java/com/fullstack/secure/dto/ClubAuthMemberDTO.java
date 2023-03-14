package com.fullstack.secure.dto;

import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/*
UserDetailService 인터페이스에서 사용될 멤버들의 정보를 담고 있는 DTO 정의 합니다.
상속받을 User 클래스에는 대표적인 속성이 몇개 정의 되어있는데, ID 정보, Password 정보,
Role 권한 정보등입니다.

해서 스프링 Secure 를 구성할때는 위 클래스를 상속 받아서, 부모가 가진 기본 정보값은 부모에게
Super 로 넘기고 나머지 부분(권한 작업등)만 정의 하도록 합니다.
 */
@Log4j2
@Data
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {

    private String email;
    private boolean fromSocial;
    private String name;
    private Map<String, Object> attr;

    public ClubAuthMemberDTO(String username,
                             String password,
                             boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;

    }
    public ClubAuthMemberDTO(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {

        this(username, password, fromSocial, authorities);

        this.attr = attr;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
