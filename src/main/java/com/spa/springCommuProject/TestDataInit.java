package com.spa.springCommuProject;

import com.spa.springCommuProject.domain.post.entity.FreePost;
import com.spa.springCommuProject.domain.post.entity.VideoPost;
import com.spa.springCommuProject.domain.post.service.PostService;
import com.spa.springCommuProject.domain.user.BigThreePower;
import com.spa.springCommuProject.domain.user.Role;
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
        user.setRole(Role.MASTER);
        userService.join(user);
        //PhotoPost photoPost = new PhotoPost(user, "PhotoTitle", "PhotoContent");
        FreePost post = new FreePost(user, "FreeTitle", "FreeContent");
        VideoPost videoPost = new VideoPost(user, "VideoTitle", "VideoContent");
        postService.savePost(post);
        //postService.savePost(photoPost);
        postService.savePost(videoPost);
        User user1 = new User("1234", "1234", "1234");
        User user2 = new User("1235", "1235", "1234");
        User user3 = new User("1236", "1236", "1234");
        User user4 = new User("1237", "1237", "1234");
        User user5 = new User("1238", "1238", "1234");
        User user6 = new User("1239", "1239", "1234");
        User user7 = new User("1241", "1241", "1234");
        User user8 = new User("1242", "1242", "1234");
        User user9 = new User("1243", "1243", "1234");
        User user10 = new User("1244", "1244", "1234");
        userService.join(user1);
        userService.join(user2);
        userService.join(user3);
        userService.join(user4);
        userService.join(user5);
        userService.join(user6);
        userService.join(user7);
        userService.join(user8);
        userService.join(user9);
        userService.join(user10);
        userService.updateBigThree(user1.getId(),new BigThreePower(10,20,30));
        userService.updateBigThree(user2.getId(),new BigThreePower(60,200,100));
        userService.updateBigThree(user3.getId(),new BigThreePower(60,70,60));
        userService.updateBigThree(user4.getId(),new BigThreePower(70,200,40));
        userService.updateBigThree(user5.getId(),new BigThreePower(150,230,340));
        userService.updateBigThree(user6.getId(),new BigThreePower(30,70,80));
        userService.updateBigThree(user7.getId(),new BigThreePower(70,60,40));
        userService.updateBigThree(user8.getId(),new BigThreePower(90,80,50));
        userService.updateBigThree(user9.getId(),new BigThreePower(100,200,300));
        userService.updateBigThree(user10.getId(),new BigThreePower(10,20,90));
    }

}
