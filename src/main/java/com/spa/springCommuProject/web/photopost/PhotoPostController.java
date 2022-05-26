package com.spa.springCommuProject.web.photopost;

import com.spa.springCommuProject.domain.post.*;
import com.spa.springCommuProject.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "posts/photoPostList";
    }

    @GetMapping("/photopost")
    public String createPhotoPostForm(PhotoPostDTO photoPostDTO) {
        log.info("createPhotoPostForm");
        return "posts/filePostForm";
    }

    @PostMapping("/photopost")
    public String createPhotoPost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                                  PhotoPostDTO photoPostDTO) throws IOException {
        log.info("createPhotoPost");
        List<Image> storeImageFiles = fileService.storeFiles(photoPostDTO.getImageFiles());

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
        List<Image> images = fileService.findImagesbyPostId(postId);

        model.addAttribute("loginUserId", loginUser==null ? null : loginUser.getId());
        model.addAttribute("photoPostDTO", photoPostDTO);
        model.addAttribute("images", images);
        model.addAttribute("postId", post.getId());
        return "posts/photoPostView";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }




}
