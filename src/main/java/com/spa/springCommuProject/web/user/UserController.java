package com.spa.springCommuProject.web.user;


import com.spa.springCommuProject.domain.user.Role;
import com.spa.springCommuProject.domain.user.User;
import com.spa.springCommuProject.domain.user.UserService;
import com.spa.springCommuProject.domain.user.bigThreePower;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String joinForm(UserJoinDTO userJoinDTO){
        log.info("joinForm");
        return "/user/joinForm";
    }

    @PostMapping("/user")
    public String join(@Valid UserJoinDTO userJoinDTO){
        User user = new User(userJoinDTO.getNickName(),userJoinDTO.getLoginId(),
                userJoinDTO.getPassword());
        log.info("join nickName={}",user.getNickName());
        log.info("join username={}",user.getLoginId());
        log.info("join userpassword={}",user.getPassword());
        userService.join(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(UserLoginDTO userLoginDTO){
        log.info("loginForm");
        return "/user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDTO userLoginDTO, BindingResult
            bindingResult, HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            bindingResult.reject("loginFail", "잘못된 정보가 들어왔습니다.");
            return "/user/loginForm";
        } //폼에 잘못된 정보 들어왔을때

        User loginUser = userService.login(userLoginDTO.getLoginId(), userLoginDTO.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/loginForm";
        }
        log.info("login username={}",loginUser.getLoginId());
        log.info("login userpassword={}",loginUser.getPassword());

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginUser", loginUser);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
