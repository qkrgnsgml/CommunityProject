package com.spa.springCommuProject.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception{
        //given
        User user = new User("park", "userId", "userPW");


        //when
        Long saveId = userService.join(user);

        //then
        Assertions.assertThat(user.getId()).isEqualTo(saveId);
    }

}