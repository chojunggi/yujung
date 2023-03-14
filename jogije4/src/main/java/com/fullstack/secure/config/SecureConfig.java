package com.fullstack.secure.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
스프링에서 시큐어 작업을 할때 필요한 Bean 등을 여기서 정의 후에
스프링커네이너가 로드시 해당 객체를 Bean 으로 생성해서 메모리에
올리고, 관리하게끔 저의 합니다. 이후 Service 나 Repository 등에서
해당 Bean 등을 사용하는 방식이 일반적입니다.
 */
@Log4j2
@Configuration
public class SecureConfig {

    //InMemoryUser 객체를 빈으로 생성하고,
    //기본적으로 스프링에서는 각 계정마다 사용되는 암호를 반드시 Encrypt 시키도록
    //강제화 되어 있습니다.
    //때문에 PasswordEncoder 라는 인터페이스 객체또한 빈으로 올려서 사용하도록 할 예정입니다.
    //@Bean 어노테이션을 적용후 사용될 타입을 리턴하는 메서드로 정의 하시면 기본 빈 설정은
    //끝납니다.

    //패스워드 인코더 빈 설정.
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager 타입의 InMemoryUserDetailsManager 의 객체를 Bean 으로 생성해서
    //기본 인증에 필요한 값들을 설정후 메모리에 올리는 작업을 합니다.
    //여기서 기억하셔야할 것은 실제 사용자의 인증 정보를 가지고 있는 객체는 UserDetail 이라는
    //객체이고 모두, UserDetailService 타입이라는 걸 기억하세요.
    @Bean
    public InMemoryUserDetailsManager UserDetailsManager() {

        UserDetails user = User.builder()
                .username("yuseob")//스프링에서는 username 이라는 속성은 unique 한 ID 로 취함.
                .password(passwordEncoder().encode("1234"))
                .roles("USER")//User 권한을 부여함.인가설정.(자원에 Access 할수 있는 권한)
                .build();
        log.info("생성된 User : " + user);
        return  new InMemoryUserDetailsManager(user);
    }

    /*
    Authorization 설정을 변경하는 작업을 해봅니다.
    Config 에서 하는방법을 적용해서, 인증시 실행되는 FilterChain 을
    수정하여 그 내용을 적용해 봅니다.
    여기서 주의깊게 보셔야 할건, 인가 작업을 수행하는 객체인데, HttpSecurity 객체입니다.
    이 객체를 통해 인가작업, 로그인 후 리디렉션, 로그아웃, 세션 및 쿠키 삭제 작업등을 할 수
    있습니다.
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //인가작업을 수행할 메서드를 호출 해서 원하는 인가 작업으로 오버라이드 시킵니다.

        http.authorizeHttpRequests((auth)->{
            //권한이 없는 유저도 접근 가능하도록 설정함
            auth.antMatchers("/sample/exAll").permitAll();
            auth.antMatchers("/sample/exAdmin").hasRole("ADMIN");
        });

        http.csrf().disable();//교차스크립팅 보안 금지 사용시 활성화..

        http.logout()
                .deleteCookies("JSESSIONID");

        http.formLogin();//기본 로그인폼으로 되돌리기.

        //OAuth2 인증적용하기
        http.oauth2Login();

        return http.build();
    }
}
