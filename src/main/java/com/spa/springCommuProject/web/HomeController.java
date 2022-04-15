package com.spa.springCommuProject.web;

import com.spa.springCommuProject.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                       Model model){

        //세션에 회원 데이터가 없으면 home
        if (loginUser == null) {
            return "home";
        }

        //세션이 유지되면 모델에 값 넣고 로그인으로 이동
        model.addAttribute("user", loginUser);

        return "loginHome";
    }
}
