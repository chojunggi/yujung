package com.fullstack.secure.role;
//사용자의 인증이 이뤄진후 사용 권한을 설정한 상수 필드만 가지고 있는 인터페이스
//Spring 에서는 구현체가 없는 Interface, Abstract Class 는 인스턴스를 생성하지 않습니다.
//때문에 상수등을 선언만 하고 사용할 때는 인터페이스등을 이용하는 것도 한 방법입니다.

public interface SessionConstants {

    String NORM_MEM = "NOR_MEMBER";
            String ADM = "ADMIN";
}
