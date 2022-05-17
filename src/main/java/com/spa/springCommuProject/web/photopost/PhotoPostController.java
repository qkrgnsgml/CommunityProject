package com.spa.springCommuProject.web.photopost;

import com.spa.springCommuProject.domain.post.Post;
import com.spa.springCommuProject.domain.post.PostService;
import com.spa.springCommuProject.domain.user.User;
import com.spa.springCommuProject.web.freePost.FreePostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PhotoPostController {

    private final PostService postService;

    @GetMapping("/photoposts")
    public String photoPostList(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               Model model) {
        List<Post> posts = postService.findAvailablePosts();
        model.addAttribute("link", loginUser==null ? "login" : "photopost");
        model.addAttribute("posts", posts);
        return "posts/photoPostList";
    }

    @GetMapping("/photopost")
    public String createForm(FreePostDTO freePostDTO) {
        log.info("createFreePostForm");
        return "posts/filePostForm";
    }




}
