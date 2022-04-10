package com.spa.springCommuProject;

import com.spa.springCommuProject.domain.user.User;
import com.spa.springCommuProject.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setNickName("park");
        user.setLoginId("spa");
        user.setPassword("123");
        userService.join(user);
    }

}
