package com.spa.springCommuProject.domain.post.freepost;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FreePostServiceTest {

    @Autowired
    FreePostRepository freePostRepository;

    @Autowired
    FreePostService freePostService;

    @Test
    public void 회원가입() throws Exception{
        //given
        FreePost freePost = new FreePost();

        //when
        Long saveId = freePostService.join(freePost);

        //then
        Assertions.assertThat(saveId).isEqualTo(freePost.getId());
    }
}