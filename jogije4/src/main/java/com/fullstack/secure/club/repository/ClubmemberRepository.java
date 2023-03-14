package com.fullstack.secure.club.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.secure.club.entity.ClubMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubmemberRepository extends JpaRepository<ClubMember, String> {

    /*
    이메일로 회원 정보 얻어내기.(권한 그룹정보까지)
    현재 테이블은 생성시에 Fetch type 을 Lazy 로 설정했기에, 기본적으로 Outer Join은
    걸리지 않습니다. 이 때 사용하는 @ 이 EntityGraph 입니다. 여기에 값으로는 참조되는
    테이블의 Ref 를 주면, 조인 쿼리를 수행할 때 같이 조인되어집니다.

    속성으로는 LOAD : Fetch 에 적용한 속성명은 Eager 조인, 나머지는 기본
    FETCH : 속성으로 적용된 것만 EAGER 나머진 LAZY 로 조인
    이 있으니 참고하세요.
     */
    @EntityGraph(attributePaths = {"roleset"},
        type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial =:fromSocial and m.email =:email")
    Optional<ClubMember> findByEmail(@Param("email") String email, @Param("fromSocial") boolean social);
}