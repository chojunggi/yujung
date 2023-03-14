package com.fullstack.secure.club.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClubMember extends BaseEntity {

    @Id
    private String email;
    private String password;
    private String name;
    //OAuth2 를 적용할 필드.. 소셜로그인 여부 필드
    private boolean fromSocial;

    //테이블 생성시 참조 테이블인 권한 테이블을 생성하는데
    //이때 사용할 타입이 ENUM 입니다.
    //해서 이 타입을 사용하는 선언으로 @ElementCollection 을 사용합니다.
    //기본적으로 fetch 타입은 Lazy 로 합니다.
    @ElementCollection(fetch = FetchType.LAZY)
    //위에서처럼 Collection 으로 생성시엔 반드시 Builder.default() @ 을 사용해야 합니다.
    @Builder.Default
    //사용자 권한 값을 담는 Set 객체 생성
    private Set<ClubmemberRole> roleset = new HashSet<>();

    //멤버에 권한부여 메서드 정의
    public void addMemberRole(ClubmemberRole clubmemberRole){
        roleset.add(clubmemberRole);
    }


}
