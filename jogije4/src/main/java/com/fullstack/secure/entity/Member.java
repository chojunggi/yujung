package com.fullstack.secure.entity;

import lombok.Builder;
import lombok.Data;

/*
회원 Entity,DTO 정의
 */
@Builder
@Data
public class Member {

    private Long id;
    private String loginId;
    private String name;
    private String password;
}
