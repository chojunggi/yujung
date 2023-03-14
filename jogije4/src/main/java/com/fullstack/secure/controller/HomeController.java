package com.fullstack.secure.controller;

import com.fullstack.secure.entity.Member;
import com.fullstack.secure.role.SessionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
이 컨트롤러는 세션의 여부를 검증해서 로그인한 사용자의 경우 사용자 정보를
그렇지 않은 경우엔 그냥 홈내용을 보여준다.
이 때 판단의 기준은 session 객체인데, session.getAttribute() 를 이용하는 방법과
@SessionAttributes 를 이용하는 방법 두가지 모두 사용가능하다.
필요시 두가지 모두 정의해볼게요.
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConstants.NORM_MEM, required = false)
                       Member loginMember, Model model, HttpServletRequest request) {
        if(loginMember == null) {
            return "main/index";
        }
        //세션이 유지되면 다시 홈으로 보낸다.
        model.addAttribute("member",loginMember);

        //어노테이션 없는 일반 세션 코드 적용..
//        HttpSession session = request.getSession(false);
//        if(session!= null) {
//            return "home";
//        }
//        Member loginMember2 = (Member) session.getAttribute(SessionConstants.NORM_MEM);
//        //세션 정보가 없으면 다시 홈으로..
//        if(loginMember2!= null) {
//            return "home";
//        }
//
//        model.addAttribute("member",loginMember2);
//        return "loginHome";

        return "login/loginHome";
    }
}
