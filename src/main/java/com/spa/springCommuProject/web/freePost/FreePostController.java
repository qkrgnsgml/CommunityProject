package com.spa.springCommuProject.web.freePost;

import com.spa.springCommuProject.domain.post.Post;
import com.spa.springCommuProject.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FreePostController {

    private final PostService postService;

    @GetMapping("/freeposts")
    public String freePostList(Model model){
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "posts/FreePostList";
    }

}
