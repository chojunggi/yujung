package com.fullstack.secure;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@SpringBootTest
public class PasswordEncoderTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncryptTests() {
        String password = "1234";

        /*
        BCryptPasswordEncoder 의 특징

        1. 복호화 안됨.
        2. 일정한 길이의 암호화된 문자열 리턴.
        3. 복호화는 안되지만 Raw Password 를 Encrypt 된 값과 같은지를
        비교하는 matches???() 메서드 제공함.
         */
        String encryptPw = passwordEncoder.encode(password);
        System.out.println(password + " ---> " + encryptPw);

        System.out.println(passwordEncoder.matches(password, encryptPw));
    }


}
