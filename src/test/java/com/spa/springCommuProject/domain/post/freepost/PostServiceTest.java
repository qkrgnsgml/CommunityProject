package com.spa.springCommuProject.domain.post.freepost;

import com.spa.springCommuProject.domain.post.entity.Post;
import com.spa.springCommuProject.domain.post.repository.PostRepository;
import com.spa.springCommuProject.domain.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    @Rollback(value = false)
    public void 글등록() throws Exception{
        //given
        Post post = new Post();

        //when
        Long saveId = postService.savePost(post);

        //then
        assertThat(saveId).isEqualTo(post.getId());
    }
}