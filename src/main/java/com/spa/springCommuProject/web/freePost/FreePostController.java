package com.spa.springCommuProject.web.freePost;

import com.spa.springCommuProject.domain.post.FreePost;
import com.spa.springCommuProject.domain.post.Post;
import com.spa.springCommuProject.domain.post.PostService;
import com.spa.springCommuProject.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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

    @GetMapping("/freepost")
    public String createForm(FreePostDTO freePostDTO){
        log.info("createFreePostForm");
        return "posts/createFreePostForm";
    }

    @PostMapping("/freepost")
    public String create(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                         FreePostDTO freePostDTO){
        log.info("createFreePost");

        FreePost freePost = new FreePost(loginUser, freePostDTO.getTitle(), freePostDTO.getContent());
        postService.savePost(freePost);

        Long id = freePost.getId();
        return "redirect:/freepost/"+id;
    }

    @GetMapping("/freepost/{postId}")
    public String postView(@PathVariable Long postId, Model model){
        log.info("postView");

        Post post = postService.findOnePost(postId);
        FreePostDTO freePostDTO = new FreePostDTO();
        freePostDTO.setContent(post.getContent());
        freePostDTO.setTitle(post.getTitle());

        model.addAttribute("freePostDTO",freePostDTO);
        return "posts/freePostView";
    }

}
