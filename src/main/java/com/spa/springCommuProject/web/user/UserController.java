package com.spa.springCommuProject.web.user;


import com.spa.springCommuProject.domain.user.Role;
import com.spa.springCommuProject.domain.user.User;
import com.spa.springCommuProject.domain.user.UserService;
import com.spa.springCommuProject.domain.user.bigThreePower;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String joinForm(UserDTO userDTO){
        log.info("joinForm");
        return "/user/joinForm";
    }

    @PostMapping("/user")
    public String join(@Valid UserDTO userDTO){
        User user = new User();
        user.setNickName(userDTO.getNickName());
        user.setLoginId(userDTO.getLoginId());
        user.setPassword(userDTO.getPassword());
        user.setBigThreePower(new bigThreePower(0,0,0));
        user.setRole(Role.USER);
        log.info("join user={}",user);
        userService.join(user);
        return "redirect:/";
    }
}
