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
    public String freePostList(@SessionAttribute(name = "loginUser", required = false) User loginUser,
            Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("link", loginUser==null ? "login" : "freepost");
        model.addAttribute("posts", posts);
        return "posts/freePostList";
    }

    @GetMapping("/freepost")
    public String createForm(FreePostDTO freePostDTO) {
        log.info("createFreePostForm");
        return "posts/freePostForm";
    }

    @PostMapping("/freepost")
    public String create(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                         FreePostDTO freePostDTO) {
        log.info("createFreePost");

        FreePost freePost = new FreePost(loginUser, freePostDTO.getTitle(), freePostDTO.getContent());
        postService.savePost(freePost);

        Long id = freePost.getId();
        return "redirect:/freepost/" + id;
    }

    @GetMapping("/freepost/{postId}")
    public String postView(@SessionAttribute(name = "loginUser", required = false) User loginUser,
            @PathVariable Long postId, Model model) {
        log.info("postView");

        postService.viewIncrease(postId); //추가
        Post post = postService.findOnePost(postId);
        FreePostDTO freePostDTO = new FreePostDTO(post.getTitle(), post.getContent());

        model.addAttribute("loginUser", loginUser==null ? null : loginUser.getId());
        model.addAttribute("postUser",post.getUser().getId());
        model.addAttribute("freePostDTO", freePostDTO);
        model.addAttribute("postId", post.getId());
        return "posts/freePostView";
    }

    @GetMapping("/freepost/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {
        log.info("editForm");

        Post post = postService.findOnePost(postId);
        FreePostDTO freePostDTO = new FreePostDTO(post.getTitle(), post.getContent());

        model.addAttribute("freePostDTO", freePostDTO);
        return "posts/FreePostForm";
    }

    @PostMapping("/freepost/{postId}/edit")
    public String edit(@PathVariable Long postId, FreePostDTO freePostDTO) {
        log.info("edit");

        postService.updateFreePost(postId, freePostDTO.getTitle(), freePostDTO.getContent());

        return "redirect:/freepost/" + postId;
    }

    @GetMapping("/freepost/{postId}/delete")
    public String deleteForm(@PathVariable Long postId, Model model) {
        log.info("deleteForm");

        model.addAttribute("postId",postId);

        return "posts/deleteForm";
    }

    @PostMapping("/freepost/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        log.info("delete");

        postService.deleteFreePost(postId);

        return "redirect:/freeposts";
    }


}
