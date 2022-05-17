package com.spa.springCommuProject;

import com.spa.springCommuProject.domain.post.FreePost;
import com.spa.springCommuProject.domain.post.PostService;
import com.spa.springCommuProject.domain.user.User;
import com.spa.springCommuProject.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Transactional
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;
    private final PostService postService;
    //private final EntityManager em;

    @PostConstruct
    public void init() {
        User user = new User("park","asdfg","123");
        userService.join(user);
        FreePost post = new FreePost(user, "title", "content");
        postService.savePost(post);
    }

}
