package com.spa.springCommuProject;

import com.spa.springCommuProject.domain.post.entity.FreePost;
import com.spa.springCommuProject.domain.post.entity.PhotoPost;
import com.spa.springCommuProject.domain.post.entity.VideoPost;
import com.spa.springCommuProject.domain.post.service.PostService;
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
        User user = new User("root","root","1234");
        userService.join(user);
        PhotoPost photoPost = new PhotoPost(user, "PhotoTitle", "PhotoContent");
        FreePost post = new FreePost(user, "FreeTitle", "FreeContent");
        VideoPost videoPost = new VideoPost(user, "VideoTitle", "VideoContent");
        postService.savePost(post);
        postService.savePost(photoPost);
        postService.savePost(videoPost);
    }

}
