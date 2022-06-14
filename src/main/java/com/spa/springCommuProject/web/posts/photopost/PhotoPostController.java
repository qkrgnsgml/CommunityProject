package com.spa.springCommuProject.web.posts.photopost;

import com.spa.springCommuProject.domain.post.entity.Image;
import com.spa.springCommuProject.domain.post.entity.PhotoPost;
import com.spa.springCommuProject.domain.post.entity.Post;
import com.spa.springCommuProject.domain.post.service.FileService;
import com.spa.springCommuProject.domain.post.service.PostService;
import com.spa.springCommuProject.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PhotoPostController {

    private final FileService fileService;
    private final PostService postService;

    @GetMapping("/photoposts")
    public String photoPostList(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               Model model) {
        List<PhotoPost> photoPosts = postService.findAvailablePhotoPosts();
        model.addAttribute("link", loginUser==null ? "login" : "photopost");
        model.addAttribute("posts", photoPosts);
        return "posts/photopost/photoPostList";
    }

    @GetMapping("/photoposts/{pageNum}")
    public String photoPostListPage(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                                   @PathVariable int pageNum, Model model) {
        int size = postService.findAvailablePhotoPosts().size()/10 + 1;
        List<PhotoPost> photoPosts = postService.findAvailablePagingPhotoPosts(pageNum);
        model.addAttribute("link", loginUser==null ? "login" : "photopost");
        model.addAttribute("size", size);
        model.addAttribute("posts", photoPosts);
        return "posts/photopost/photoPostList";
    }

    @GetMapping("/photopost")
    public String createPhotoPostForm(PhotoPostDTO photoPostDTO) {
        log.info("createPhotoPostForm");
        return "posts/photopost/photoPostForm";
    }

    @PostMapping("/photopost")
    public String createPhotoPost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                                  PhotoPostDTO photoPostDTO) throws IOException {
        log.info("createPhotoPost");
        List<Image> storeImageFiles = fileService.storeImages(photoPostDTO.getImageFiles());

        //데이터베이스에 저장
        PhotoPost post = new PhotoPost(loginUser, photoPostDTO.getTitle(),
                            photoPostDTO.getContent());
        postService.savePost(post);

        for (Image storeImageFile : storeImageFiles) {
            Image image = new Image(post, storeImageFile.getUploadFileName(), storeImageFile.getStoreFileName());
            fileService.saveImage(image);
        }

        Long id = post.getId();

        return "redirect:/photopost/" + id;
    }

    @GetMapping("/photopost/{postId}")
    public String photoPostView(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               @PathVariable Long postId, Model model) {
        log.info("photopostView");

        postService.viewIncrease(postId); //추가
        Post post = postService.findOnePost(postId);

        PhotoPostDTO photoPostDTO = new PhotoPostDTO(post.getTitle(), post.getContent(), post.getCreatedDate(), post.getUser());
        List<Image> images = fileService.findImagesByPostId(postId);

        model.addAttribute("loginUserId", loginUser==null ? null : loginUser.getId());
        model.addAttribute("loginUserLoginId", loginUser==null ? null : loginUser.getLoginId());
        model.addAttribute("photoPostDTO", photoPostDTO);
        model.addAttribute("images", images);
        model.addAttribute("postId", post.getId());
        return "posts/photopost/photoPostView";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileService.getFullImagePath(filename));
    }

    @GetMapping("/photopost/{postId}/edit")
    public String editPhotoPostForm(@PathVariable Long postId, Model model) {
        log.info("photoPosteditForm");

        Post post = postService.findOnePost(postId);
        PhotoPostDTO photoPostDTO = new PhotoPostDTO(post.getTitle(), post.getContent());

        model.addAttribute("postId",postId);
        model.addAttribute("photoPostDTO", photoPostDTO);
        return "posts/photopost/photoPostUpdateForm";
    }

    @PostMapping("/photopost/{postId}/edit")
    public String editPhotoPost(@PathVariable Long postId,
                               @Valid PhotoPostDTO photoPostDTO, BindingResult bindingResult) {
        log.info("photoPostedit");

        if (bindingResult.hasErrors()) {
            bindingResult.reject("updateFail", "잘못된 정보를 입력했습니다.");
            log.info("bindingError");
            return "/posts/photopost/photoPostUpdateForm";
        } //추가

        postService.updatePost(postId, photoPostDTO.getTitle(), photoPostDTO.getContent());

        return "redirect:/photopost/" + postId;
    }

    @GetMapping("/photopost/{postId}/delete")
    public String deletePhotoPostForm(@PathVariable Long postId, Model model) {
        log.info("PhotoPostDeleteForm");

        model.addAttribute("postId",postId);

        return "posts/photopost/photoPostdeleteForm";
    }

    @PostMapping("/photopost/{postId}/delete")
    public String deletePhotoPost(@PathVariable Long postId) {
        log.info("PhotoPostDelete");

        postService.deletePost(postId);

        return "redirect:/photoposts/0";
    }




}
