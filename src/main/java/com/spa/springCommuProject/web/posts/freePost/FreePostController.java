package com.spa.springCommuProject.web.posts.freePost;

import com.spa.springCommuProject.domain.post.entity.FreePost;
import com.spa.springCommuProject.domain.post.entity.Post;
import com.spa.springCommuProject.domain.post.service.PostService;
import com.spa.springCommuProject.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FreePostController {

    private final PostService postService;

    @GetMapping("/freeposts")
    public String freePostList(@SessionAttribute(name = "loginUser", required = false) User loginUser,
            Model model) {
        List<FreePost> freePosts = postService.findAvailableFreePosts();
        model.addAttribute("link", loginUser==null ? "login" : "freepost");
        model.addAttribute("posts", freePosts);
        return "posts/freepost/freePostList";
    }

    @GetMapping("/freeposts/{pageNum}")
    public String freePostListPage(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               @PathVariable int pageNum, Model model) {
        int size = postService.findAvailableFreePosts().size()/10 + 1;
        List<FreePost> freePosts = postService.findAvailablePagingFreePosts(pageNum);
        model.addAttribute("link", loginUser==null ? "login" : "freepost");
        model.addAttribute("size", size);
        model.addAttribute("posts", freePosts);
        return "posts/freepost/freePostList";
    }

    @GetMapping("/freepost")
    public String createFreePostForm(FreePostDTO freePostDTO) {
        log.info("createFreePostForm");
        return "posts/freepost/freePostForm";
    }

    @PostMapping("/freepost")
    public String createFreePost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                         @Valid FreePostDTO freePostDTO, BindingResult bindingResult) {
        log.info("createFreePost");

        if (bindingResult.hasErrors()) {
            bindingResult.reject("PostCreateFail", "잘못된 정보를 입력했습니다.");
            log.info("bindingError");
            return "/posts/freepost/freePostForm";
        } //추가

        FreePost freePost = new FreePost(loginUser, freePostDTO.getTitle(), freePostDTO.getContent());
        postService.savePost(freePost);

        Long id = freePost.getId();
        return "redirect:/freepost/" + id;
    }

    @GetMapping("/freepost/{postId}")
    public String freePostView(@SessionAttribute(name = "loginUser", required = false) User loginUser,
            @PathVariable Long postId, Model model) {
        log.info("FreepostView");

        postService.viewIncrease(postId); //추가
        Post post = postService.findOnePost(postId);
        FreePostDTO freePostDTO = new FreePostDTO(post.getTitle(), post.getContent(), post.getCreatedDate(),post.getUser());

        model.addAttribute("loginUserId", loginUser==null ? null : loginUser.getId());
        model.addAttribute("freePostDTO", freePostDTO);
        model.addAttribute("loginUserLoginId", loginUser==null ? null : loginUser.getLoginId());
        model.addAttribute("postId", post.getId());
        return "posts/freepost/freePostView";
    }

    @GetMapping("/freepost/{postId}/edit")
    public String editFreePostForm(@PathVariable Long postId, Model model) {
        log.info("freePosteditForm");

        Post post = postService.findOnePost(postId);
        FreePostDTO freePostDTO = new FreePostDTO(post.getTitle(), post.getContent());

        model.addAttribute("postId",postId);
        model.addAttribute("freePostDTO", freePostDTO);
        return "posts/freepost/freePostUpdateForm";
    }

    @PostMapping("/freepost/{postId}/edit")
    public String editFreePost(@PathVariable Long postId,
                       @Valid FreePostDTO freePostDTO, BindingResult bindingResult) {
        log.info("freePostedit");

        if (bindingResult.hasErrors()) {
            bindingResult.reject("updateFail", "잘못된 정보를 입력했습니다.");
            log.info("bindingError");
            return "/posts/freepost/freePostUpdateForm";
        } //추가

        postService.updatePost(postId, freePostDTO.getTitle(), freePostDTO.getContent());

        return "redirect:/freepost/" + postId;
    }

    @GetMapping("/freepost/{postId}/delete")
    public String deleteFreePostForm(@PathVariable Long postId, Model model) {
        log.info("freePostDeleteForm");

        model.addAttribute("postId",postId);

        return "posts/freepost/freePostdeleteForm";
    }

    @PostMapping("/freepost/{postId}/delete")
    public String deleteFreePost(@PathVariable Long postId) {
        log.info("freePostDelete");

        postService.deletePost(postId);

        return "redirect:/freeposts/0";
    }


}
